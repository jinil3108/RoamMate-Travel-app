package com.techholding.android.roammate.data

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techholding.android.roammate.databinding.ItemTopPlacesBinding
import com.techholding.android.roammate.model.Place

class TopPlacesAdapter(private val listener: TopPlacesItemListener): RecyclerView.Adapter<TopPlaceViewHolder>()  {
    private val items: MutableList<Place> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TopPlaceViewHolder(
            ItemTopPlacesBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TopPlaceViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateTopPlacesList(updatedNewsList: List<Place>) {
        items.clear()
        items.addAll(updatedNewsList)
        notifyDataSetChanged()
    }
}
class TopPlaceViewHolder(private val binding: ItemTopPlacesBinding) : RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(places: Place, listener: TopPlacesItemListener) {
        binding.root.setOnClickListener {
            listener.onItemClicked(places)
        }
        binding.titlePlace.text = places.name
        Glide.with(itemView.context).load(places.logo).into(binding.imagePlace)
    }
}
interface TopPlacesItemListener {
    fun onItemClicked(item: Place)
}