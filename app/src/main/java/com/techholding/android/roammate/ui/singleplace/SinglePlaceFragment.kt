package com.techholding.android.roammate.ui.singleplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout
import com.techholding.android.roammate.R
import com.techholding.android.roammate.data.PlaceDetailTabsAdapter
import com.techholding.android.roammate.databinding.FragmentSinglePlaceBinding

class SinglePlaceFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSinglePlaceBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var googleMap: GoogleMap
    private val args: SinglePlaceFragmentArgs by navArgs()
    private val place by lazy {
        args.place
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSinglePlaceBinding.inflate(inflater, container, false)

        setMapLayout()
        setToolBar()
        setTabLayout()
        return binding.root
    }

    private fun setMapLayout(){
        val mapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setToolBar() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = place.name
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val location = LatLng(place.latitude.toDouble(),place.longitude.toDouble())
        googleMap.addMarker(MarkerOptions().position(location).title(place.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,10f))
    }


    private fun setTabLayout(){
        tabLayout = binding.tabLayout
        viewPager = binding.pageViewer

        val bundleArgs = Bundle()
        bundleArgs.putString("desc",place.description)
        bundleArgs.putString("reviews",place.placesId)
        tabLayout.addTab(tabLayout.newTab().setText("Description"))
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"))
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = context?.let {
            PlaceDetailTabsAdapter(
                childFragmentManager,
                tabLayout.tabCount,
                place.description,
                place.placesId
            )
        }
        viewPager.adapter=adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

}