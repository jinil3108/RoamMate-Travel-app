package com.techholding.android.roammate.data

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.techholding.android.roammate.databinding.ItemTripRecyclerViewBinding
import com.techholding.android.roammate.model.Trip
import com.techholding.android.roammate.utils.DatabaseHelper

class TripAdapter(private val listener: TripItemListener) : RecyclerView.Adapter<TripViewHolder>() {
    private val items: MutableList<Trip> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TripViewHolder(
            ItemTripRecyclerViewBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun deleteItem(pos: Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateTripList(updatedTripList: List<Trip>) {
        items.clear()
        items.addAll(updatedTripList)
        notifyDataSetChanged()
    }
    fun getItemAt(position: Int) : Trip {
        return items[position]
    }
}
class TripViewHolder(private val binding: ItemTripRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(trip: Trip, listener: TripItemListener) {
        binding.root.setOnClickListener {
            listener.onItemClicked(trip)
        }
        binding.titleTrip.text = trip.title
        DatabaseHelper.addPlacesInList(binding.titleTrip.text.toString(),
        onSuccess = { trips->
            binding.tripPlaces.text=trips
        },
            onError = {err->
                err.toException().printStackTrace()
                Log.e(TAG, "TaskException: $err")
            }
            )
    }
    companion object{
        private const val TAG = "TripAdapter"
    }
}

interface TripItemListener {
    fun onItemClicked(item: Trip)
}