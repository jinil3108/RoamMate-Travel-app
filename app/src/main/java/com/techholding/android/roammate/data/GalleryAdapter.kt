package com.techholding.android.roammate.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techholding.android.roammate.databinding.ItemGalleryViewBinding
import com.techholding.android.roammate.model.Image
class GalleryAdapter(private val imageList: MutableList<Image>, val context: Context): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(private val binding: ItemGalleryViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            Glide.with(context).load(image.imageURL).into(binding.recyclerImage)
            binding.recyclerCaption.text = image.caption
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {

        val binding =
            ItemGalleryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)

    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(imageList[position])
    }
    override fun getItemCount(): Int {
        return imageList.size
    }

}