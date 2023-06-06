package com.techholding.android.roammate.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techholding.android.roammate.databinding.ItemHotelsBinding
import com.techholding.android.roammate.model.Hotels
import com.techholding.android.roammate.model.Place

class HotelsAdapter:RecyclerView.Adapter<HotelsViewHolder>() {
    private val items: MutableList<Hotels> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HotelsViewHolder(
            ItemHotelsBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    fun updateHotelList(updatedHotelsList: List<Hotels>) {
        items.clear()
        items.addAll(updatedHotelsList)
        notifyDataSetChanged()
    }
}

class HotelsViewHolder(private val binding: ItemHotelsBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(hotels: Hotels) {
        binding.hotelName.text = hotels.name
        binding.address.text = hotels.address
//
    }
}

interface HotelsItemListener {

    fun onItemClicked(item: Hotels)
}