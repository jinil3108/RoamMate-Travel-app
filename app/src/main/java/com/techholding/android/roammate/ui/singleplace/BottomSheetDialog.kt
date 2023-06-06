package com.techholding.android.roammate.ui.singleplace

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.techholding.android.roammate.R
import com.techholding.android.roammate.data.HotelsAdapter
import com.techholding.android.roammate.databinding.BottomSheetDialogBinding
import com.techholding.android.roammate.model.Hotels
import com.techholding.android.roammate.utils.DatabaseHelper
import com.techholding.android.roammate.utils.Utils
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import java.util.*


class BottomSheetDialog(private var title: String, private var placeId: String,private var latitude: Double, private var longitude: Double) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private var hotelsList: MutableList<Hotels> = mutableListOf()
    private lateinit var adapter: HotelsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        binding.titleName.text = title
        checkIfAdded()
        initListeners()
        fetchHotels()
        return binding.root
    }

    private val client = OkHttpClient()

    fun getRapidApiAsync(
        url: String?,
        rapidApiKey: String?,
        rapidApiHost: String?,
        callback: Callback?
    ) {
        val request: Request = Request.Builder()
            .url(url!!)
            .get()
            .addHeader("x-rapidapi-key", rapidApiKey!!)
            .addHeader("x-rapidapi-host", rapidApiHost!!)
            .build()
        val call: Call = client.newCall(request)
        if (callback != null) {
            call.enqueue(callback)
        }
    }

    fun findPlacesNearby(
        lat: Double,
        lng: Double,
        type: String?,
        radius: Int,
        language: String?,
        callback: Callback?
    ) {
        getRapidApiAsync(
            java.lang.String.format(
                Locale.US,
                "https://%s/FindPlacesNearby?location=%.6f,%.6f&type=%s&radius=%d&language=%s",
                Utils.RAPIDAPI_TRUEWAY_PLACES_HOST,
                lat,
                lng,
                type,
                radius,
                language
            ),
            Utils.RAPIDAPI_KEY,
            Utils.RAPIDAPI_TRUEWAY_PLACES_HOST,
            callback
        )
    }



    private fun checkIfAdded(){
        DatabaseHelper.checkPlaces(placeId, title, onSuccess = {
            if(it)
            {
                binding.addToYourRoute.text = resources.getString(R.string.delete_from_your_route)
            }
            else
            {
                binding.addToYourRoute.text = resources.getString(R.string.add_to_your_route)
            }
        }, onError = {
            it.printStackTrace()
            Log.e(TAG, "Exception: $it")
        })
    }

    private fun initListeners() {
        binding.addToYourRoute.setOnClickListener {
            if(binding.addToYourRoute.text == resources.getString(R.string.add_to_your_route))
            {
                DatabaseHelper.setTripData(placeId, title, true,
                    onSuccess = {
                        Toast.makeText(context,"Place added successfully", Toast.LENGTH_LONG).show()
                    },
                    onError = {
                        Toast.makeText(context,"Failed", Toast.LENGTH_LONG).show()
                    }
                )
                binding.addToYourRoute.text = resources.getString(R.string.delete_from_your_route)

            }
            else
            {
                DatabaseHelper.setTripData(placeId, title, false,
                    onSuccess = {
                        Toast.makeText(context,"Place Removed successfully", Toast.LENGTH_LONG).show()
                    },
                    onError = {
                        Toast.makeText(context,"Failed", Toast.LENGTH_LONG).show()
                    }
                )
                binding.addToYourRoute.text = resources.getString(R.string.add_to_your_route)
            }
        }
    }

    private fun fetchHotels(){
        binding.hotelsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = HotelsAdapter()
        binding.hotelsRecycler.adapter = adapter

        findPlacesNearby(latitude, longitude, "restaurant", 15000, "en",
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Something went wrong
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {

                        val HotelsList : MutableList<Hotels> = mutableListOf()
                        val jsonData: String = response.body!!.string()
                        val jobject = JSONObject(jsonData)
                        val jarray = jobject.getJSONArray("results")

                        for (i in 0 until jarray.length()) {
                            val objects = jarray.getJSONObject(i)
                            if(objects.has("address")&&objects.has("name")&&objects.has("distance"))
                            {
                                HotelsList.add(Hotels(objects.getString("name"), objects.getString("address"), objects.getString("distance")+"m"))
                            }
                        }
                        Log.d(TAG, HotelsList.toString())

                        val handler = Handler(Looper.getMainLooper())
                        handler.post {
                            adapter.updateHotelList(HotelsList)
                        }

                    } else {
                        // Request not successful
                    }
                }
            })
    }

    companion object{
        private const val TAG = "BottomSheetDialog"
    }
}