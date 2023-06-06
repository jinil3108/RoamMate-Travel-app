package com.techholding.android.roammate.data

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.techholding.android.roammate.databinding.ItemPlaceBinding
import com.techholding.android.roammate.model.Place


class PlacesAdapter(private val listener: PlacesItemListener) :
    RecyclerView.Adapter<PlaceViewHolder>() {
    private val items: MutableList<Place> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceViewHolder(
            ItemPlaceBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePlacesList(updatedNewsList: List<Place>) {
        items.clear()
        items.addAll(updatedNewsList)
        notifyDataSetChanged()
    }
}

class PlaceViewHolder(private val binding: ItemPlaceBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val firebaseAnalytics = Firebase.analytics

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(places: Place, listener: PlacesItemListener) {
        binding.root.setOnClickListener {
            listener.onItemClicked(places)
        }
        binding.titlePlace.text = places.name
        binding.locationPlace.text = places.city
        Glide.with(itemView.context).load(places.logo).into(binding.imagePlace)
        binding.likedPlace.setOnClickListener {
            logAnalyticsEvent(places.placesId)
        }
    }

    private fun logAnalyticsEvent(id: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id)
        }
    }
}

interface PlacesItemListener {
    fun onItemClicked(item: Place)
}