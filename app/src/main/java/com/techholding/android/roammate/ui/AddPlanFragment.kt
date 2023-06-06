package com.techholding.android.roammate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.techholding.android.roammate.R
import com.techholding.android.roammate.databinding.FragmentAddPlanBinding
import com.techholding.android.roammate.ui.singleplace.BottomSheetDialog
import com.techholding.android.roammate.utils.LocationManager

class AddPlanFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentAddPlanBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPlanBinding.inflate(inflater, container, false)

        setMapLayout()
        return binding.root
    }

    private fun setMapLayout() {
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val database = Firebase.database.reference.child("places")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    googleMap.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                childSnapshot.child("latitude").value.toString().toDouble(),
                                childSnapshot.child("longitude").value.toString().toDouble()
                            )
                        ).title(childSnapshot.child("name").value.toString())
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        if (LocationManager.getUserLocation() != null) {
            val location = LatLng(
                LocationManager.getUserLocation()!!.latitude,
                LocationManager.getUserLocation()!!.longitude
            )
            googleMap.addMarker(
                MarkerOptions().position(location)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .alpha(0.7f)
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7f))
            googleMap.setOnMarkerClickListener {
                if ((it.position.latitude == LocationManager.getUserLocation()!!.latitude) &&
                    (it.position.longitude == LocationManager.getUserLocation()!!.longitude)
                ) {
                    return@setOnMarkerClickListener false
                }
                val bottomSheetDialog = BottomSheetDialog(
                    it.title.toString(),
                    requireArguments().getString("placeId").toString(),
                    it.position.latitude,
                    it.position.longitude
                )
                bottomSheetDialog.show(childFragmentManager, "Modal Bottom Sheet")
                false
            }
        }
    }
}