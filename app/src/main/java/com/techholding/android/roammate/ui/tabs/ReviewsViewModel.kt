package com.techholding.android.roammate.ui.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techholding.android.roammate.model.Place
import com.techholding.android.roammate.model.Rating

class ReviewsViewModel: ViewModel()  {

    var reviewsList: MutableLiveData<List<Rating>> = MutableLiveData<List<Rating>>()

}