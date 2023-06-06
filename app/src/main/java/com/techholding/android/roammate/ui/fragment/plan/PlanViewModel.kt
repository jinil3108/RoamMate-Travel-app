package com.techholding.android.roammate.ui.fragment.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techholding.android.roammate.model.Trip
import com.techholding.android.roammate.utils.DatabaseHelper

class PlanViewModel : ViewModel() {

    private val _tripsList = MutableLiveData<List<Trip>>(emptyList())
    val tripsList: LiveData<List<Trip>> get() =  _tripsList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() =  _errorMessage

    init {
        getTrip()
    }

    private fun getTrip() {
        DatabaseHelper.getTrips(
            onSuccess = { list ->
                _tripsList.value = list
            }, onError = {
                _errorMessage.value = "Trips can't be fetched."
                it.toException().printStackTrace()
            }
        )
    }

    fun deleteFromFirebase(title: String) {
        DatabaseHelper.deleteTrip(
            title = title,
            onError = {
                _errorMessage.value = "Deletion Failed from Database."
                it.toException().printStackTrace()
            }
        )
    }

    fun errorMessageShown() {
        _errorMessage.value = ""
    }
}