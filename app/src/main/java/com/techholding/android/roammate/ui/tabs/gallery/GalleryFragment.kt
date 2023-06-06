package com.techholding.android.roammate.ui.tabs.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.*
import com.techholding.android.roammate.data.GalleryAdapter
import com.techholding.android.roammate.databinding.FragmentGalleryBinding
import com.techholding.android.roammate.model.Image
import com.techholding.android.roammate.ui.singleplace.SinglePlaceFragmentDirections


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private var imageList: MutableList<Image> = mutableListOf()
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("images")
    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        initListeners()
        fetchImage()
        return binding.root
    }

    private fun initListeners(){
        binding.AddPhoto.setOnClickListener {
            findNavController().navigate(
                SinglePlaceFragmentDirections.singlePlaceToUploadActivity(arguments?.getString("placeId").toString())
            )
        }

    }

    private fun fetchImage(){
        imageList.clear()
        binding.galleryRecycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = context?.let { GalleryAdapter(imageList, it) }!!
        binding.galleryRecycler.adapter = adapter

        databaseReference.addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for(childSnapShot in snapshot.children){
                    val image = childSnapShot.getValue(Image::class.java)
                    if (image != null && image.id == arguments?.getString("placeId")) {
                        imageList.add(image)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    companion object {
        @JvmStatic
        fun getInstance(placesId: String): GalleryFragment {
            val fragment = GalleryFragment()
            val bundle = Bundle()
            bundle.putString("placeId", placesId)
            fragment.arguments = bundle
            return fragment
        }
    }
}