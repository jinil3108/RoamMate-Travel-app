package com.techholding.android.roammate.data

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techholding.android.roammate.databinding.ItemReviewsBinding
import com.techholding.android.roammate.databinding.ItemTripRecyclerViewBinding
import com.techholding.android.roammate.model.Rating
import com.techholding.android.roammate.model.Trip
import com.techholding.android.roammate.ui.fragment.SettingsFragment
import com.techholding.android.roammate.utils.DatabaseHelper

class ReviewsAdapter(private val reviewsList: MutableList<Rating>): RecyclerView.Adapter<ReviewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ReviewsViewHolder(
            ItemReviewsBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val currentItem = reviewsList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }
}

class ReviewsViewHolder(private val binding: ItemReviewsBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(reviews: Rating){
        getUserName(reviews.userId.toString())
        binding.viewFeedback.text = reviews.feedback
        binding.ratingsViewer.text = reviews.review.toString()
        Log.d("ReviewsAdapter",binding.userName.toString())
    }
    private fun getUserName(userId:String){
        DatabaseHelper.getReviewsUserFullName(userId,
            onSuccess = { fullName ->
                if (fullName == null) {
                    binding.userName.text = "Anonymous User"
                } else {
                    binding.userName.text = "$fullName"
                }
            },
            onError = { databaseError ->
                databaseError.toException().printStackTrace()
                Log.e(TAG, "TaskException: $databaseError")
            }
        )
    }
    companion object {
        private const val TAG = "ReviewsAdapter"
    }
}