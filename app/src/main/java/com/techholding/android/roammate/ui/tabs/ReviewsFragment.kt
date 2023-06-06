package com.techholding.android.roammate.ui.tabs

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.techholding.android.roammate.data.ReviewsAdapter
import com.techholding.android.roammate.databinding.FragmentReviewsBinding
import com.techholding.android.roammate.model.Rating
import com.techholding.android.roammate.utils.DatabaseHelper


class ReviewsFragment : Fragment() {
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRating: String
    private lateinit var adapter: ReviewsAdapter
    private val reviewsViewModel: ReviewsViewModel by viewModels()
    private var reviewsList: MutableList<Rating> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        initListeners()
        initRecyclerView()

        return binding.root
    }
    private fun initListeners(){
        binding.ratingStars.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, _ ->
                userRating=rating.toString()
                userRating.replace(".","")
                Log.d("userRating",userRating.dropLast(2))
                initFirebase()
                ratingBar?.clearAnimation()
            }
    }
    private fun initFirebase(){
        binding.feedbackBtn.setOnClickListener {

            DatabaseHelper.setReviews(binding.feedback.text.toString(),arguments?.getString("placeId").toString(),userRating.toDouble(),
            onSuccess = {
                binding.feedback.text.clear()
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                Toast.makeText(context, "review added successfully", Toast.LENGTH_LONG).show()
            },
                onError = {
                    Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
    private fun initRecyclerView(){
        reviewsList.clear()
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ReviewsAdapter(reviewsList)
        binding.reviewsRecyclerView.adapter = adapter
        val dbReference = DatabaseHelper.getDatabaseReference().child("ratings")
        dbReference.addValueEventListener(object: ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for(childSnapshot in snapshot.children){
                    val ratings = childSnapshot.getValue(Rating::class.java)
                    if(ratings!=null && ratings.placeId == arguments?.getString("placeId")){
                        reviewsList.add(ratings)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    companion object {
        private const val TAG = "ReviewsFragment"
        @JvmStatic
        fun getInstance(placesId: String): ReviewsFragment {
            val fragment = ReviewsFragment()
            val bundle = Bundle()
            bundle.putString("placeId", placesId)
            fragment.arguments = bundle
            return fragment
        }


    }

}