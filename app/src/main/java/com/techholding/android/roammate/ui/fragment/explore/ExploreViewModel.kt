package com.techholding.android.roammate.ui.fragment.explore

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techholding.android.roammate.model.Place
import com.techholding.android.roammate.utils.DatabaseHelper

class ExploreViewModel : ViewModel() {
    var generalPlaces: MutableLiveData<List<Place>> = MutableLiveData<List<Place>>()
    var topPlaces: MutableLiveData<List<Place>> = MutableLiveData<List<Place>>()
    fun searchPlaces(query: String = "") {
        DatabaseHelper.getPlaces(
            query = query,
            onSuccess = { generalList, topList ->
                generalPlaces.value = generalList
                topPlaces.value = topList
            },
            onError = { databaseError ->
                databaseError.toException().printStackTrace()
                Log.e(TAG, "TaskException: $databaseError")
            }
        )
    }

    companion object {
        private const val TAG = "ExploreViewModel"
    }
}