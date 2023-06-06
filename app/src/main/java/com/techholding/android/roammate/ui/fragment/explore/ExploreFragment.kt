package com.techholding.android.roammate.ui.fragment.explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.techholding.android.roammate.data.PlacesAdapter
import com.techholding.android.roammate.data.PlacesItemListener
import com.techholding.android.roammate.data.TopPlacesAdapter
import com.techholding.android.roammate.data.TopPlacesItemListener
import com.techholding.android.roammate.databinding.FragmentExploreBinding
import com.techholding.android.roammate.model.Place
import com.techholding.android.roammate.utils.FileUtils
import com.techholding.android.roammate.utils.LocationManager
import com.techholding.android.roammate.utils.PermissionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.ByteBuffer


class ExploreFragment : Fragment(), PlacesItemListener, TopPlacesItemListener {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private var tflite: Interpreter? = null

    private lateinit var placesAdapter: PlacesAdapter
    private lateinit var topPlacesAdapter: TopPlacesAdapter

    private val exploreViewModel: ExploreViewModel by viewModels()
    private val candidates: MutableMap<String, Place> = HashMap()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        var allPermissionGranted = true
        result.entries.forEach {
            if (!it.value) {
                allPermissionGranted = false
            }
        }
        if (allPermissionGranted) {
            getLiveLocation()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)


        return binding.root
    }

    private fun initSearchBar() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return if (newText!!.isEmpty()) {
                    exploreViewModel.searchPlaces()
                    binding.topPlacesRecyclerView.visibility = View.VISIBLE
                    binding.topPlacesTitle.visibility = View.VISIBLE
                    true
                } else {
                    exploreViewModel.searchPlaces(newText)
                    binding.topPlacesRecyclerView.visibility = View.GONE
                    binding.topPlacesTitle.visibility = View.GONE
                    true
                }
            }
        })
    }

    data class Result(
        /** Predicted id.  */
        val id: Int,
        /** Recommended item.  */
        val item: Place,
        /** A sortable score for how good the result is relative to others. Higher should be better.  */
        val confidence: Float
    ) {
        override fun toString(): String {
            return String.format("[%d] confidence: %.3f, item: %s", id, confidence, item)
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            load()
        }
    }

    override fun onStop() {
        lifecycleScope.launch {
            unLoad()
        }
        super.onStop()
    }

    suspend fun load() {
        downloadRemoteModel()
        loadLocalModel()
    }

    private suspend fun loadLocalModel() {
        return withContext(Dispatchers.IO) {
            try {
                val buffer: ByteBuffer = FileUtils.loadModelFile(
                    requireContext().assets, "recommendation_cnn_i10o100.tflite"
                )
                initializeInterpreter(buffer)
                Log.v(TAG, "TFLite model loaded.")
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }
    }

    private fun downloadRemoteModel() {
        downloadModel("recommendations")
    }

    /** Free up resources as the client is no longer needed.  */
    fun unLoad() {
        tflite?.close()
        candidates.clear()
    }

    private fun downloadModel(modelName: String) {
        val conditions = CustomModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel(modelName, DownloadType.LOCAL_MODEL, conditions)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d(TAG, "Failed to get model file.")
                } else {
                    Log.d(TAG, "Downloaded remote model: $modelName")
                    GlobalScope.launch { initializeInterpreter(it.result) }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Model download failed for recommendations, please check your connection.")
            }
    }

    private suspend fun initializeInterpreter(model: Any) {
        return withContext(Dispatchers.IO) {
            tflite?.apply {
                close()
            }
            when (model) {
                is ByteBuffer -> {
                    tflite = Interpreter(model)
                }
                is CustomModel -> {
                    model.file?.let {
                        tflite = Interpreter(it)
                    }
                }
                else -> {
                    Toast.makeText(context, "Unexpected model type downloaded.", Toast.LENGTH_SHORT).show()
                }
            }
            Log.v(TAG, "TFLite model loaded.")
        }
    }

    private suspend fun preprocess(selectedMovies: List<Place>): IntArray {
        return withContext(Dispatchers.Default) {
            val inputContext = IntArray(10)
            for (i in 0 until 10) {
                if (i < selectedMovies.size) {
                    val (id) = selectedMovies[i]
                    inputContext[i] = id.toInt()
                } else {
                    // Padding input.
                    inputContext[i] = 0
                }
            }
            inputContext
        }
    }

    /** Postprocess to gets results from tflite inference.  */
    private suspend fun postprocess(
        outputIds: IntArray, confidences: FloatArray, selectedMovies: List<Place>
    ): List<Result> {
        return withContext(Dispatchers.Default) {
            val results = ArrayList<Result>()

            // Add recommendation results. Filter null or contained items.
            for (i in outputIds.indices) {
                if (results.size >= 10) {
                    Log.v(TAG, String.format("Selected top K: %d. Ignore the rest.", 10))
                    break
                }
                val id = outputIds[i]
                val item = candidates[id.toString()]
                if (item == null) {
                    Log.v(TAG, String.format("Inference output[%d]. Id: %s is null", i, id))
                    continue
                }
                if (selectedMovies.contains(item)) {
                    Log.v(TAG, String.format("Inference output[%d]. Id: %s is contained", i, id))
                    continue
                }
                val result = Result(
                    id, item,
                    confidences[i]
                )
                results.add(result)
                Log.v(TAG, String.format("Inference output[%d]. Result: %s", i, result))
            }
            results
        }
    }

    /** Given a list of selected items, and returns the recommendation results.  */
    private suspend fun recommend(selectedMovies: List<Place>): List<Result> {
        return withContext(Dispatchers.Default) {
            val inputs = arrayOf<Any>(preprocess(selectedMovies))

            // Run inference.
            val outputIds = IntArray(16)
            val confidences = FloatArray(16)
            val outputs: MutableMap<Int, Any> = HashMap()
            outputs[1] = outputIds
            outputs[0] = confidences

            tflite?.let {
                Log.d("recommendations",selectedMovies.size.toString())
                Log.d("recommendationsOutput",outputIds.size.toString())

                it.runForMultipleInputsOutputs(inputs, outputs)
                postprocess(outputIds, confidences, selectedMovies)
            } ?: run {
                Log.e(TAG, "No tflite interpreter loaded")
                emptyList()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LocationManager.setProviderClient(requireActivity())
        if (PermissionManager.isLocationPermissionGranted(requireActivity())) {
            getLiveLocation()
        } else {
            PermissionManager.requestLocationPermission(permissionLauncher)
        }
        initRecyclerView()
        initChatBtn()
        initSearchBar()
        exploreViewModel.generalPlaces.observe(viewLifecycleOwner) { list ->
            list?.let {
                placesAdapter.updatePlacesList(it)
            }
        }

        exploreViewModel.topPlaces.observe(viewLifecycleOwner) { list ->
            list?.let {
                topPlacesAdapter.updateTopPlacesList(it)
                for(item in it) {
                    candidates[item.placesId] = item
                }

//                GlobalScope.launch {
//                    val lister = recommend(it)
//                    Log.d(TAG, lister.toString())
//                }
            }
        }

    }

    private fun initRecyclerView() {
        binding.generalRecyclerView.layoutManager = LinearLayoutManager(context)
        placesAdapter = PlacesAdapter(this)
        binding.generalRecyclerView.adapter = placesAdapter

        binding.topPlacesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topPlacesAdapter = TopPlacesAdapter(this)
        binding.topPlacesRecyclerView.adapter = topPlacesAdapter
    }

    private fun getLiveLocation() {
        LocationManager.fetchLiveLocation(requireActivity(), onSuccess = {
            binding.locality.text = it
            exploreViewModel.searchPlaces()
        }, onError = {
            Log.e(TAG, "Exception: $it")
            it.printStackTrace()
        })
    }

    override fun onItemClicked(item: Place) {
        findNavController().navigate(
            ExploreFragmentDirections.exploreToSinglePlace(item)
        )
    }
    private fun initChatBtn() {
        binding.chatBtn.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.exploreToChat())
        }
    }

    companion object {
        private const val TAG = "ExploreFragment"
    }

}