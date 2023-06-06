package com.techholding.android.roammate.ui.singleplace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.techholding.android.roammate.databinding.BottomSheetAddDialogBinding
import com.techholding.android.roammate.utils.DatabaseHelper

class BottomSheetAddDialog : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAddDialogBinding.inflate(inflater, container, false)
        initListener()
        return binding.root
    }
    private fun initListener() {
        binding.addTrip.setOnClickListener {
            if(checkInList())
            {
                binding.createTrip.error = "Name already Exists"
            }
            else
            {
                addInPlan(binding.createTrip.text.toString())
                this.dismiss()
            }
        }
    }
    private fun addInPlan(trip: String) {
        DatabaseHelper.addTripInPlan(trip,
        onSuccess = {
            Log.d("addInPlan","added")
        },
            onError = {err->
                err.printStackTrace()
            }
            )
    }
    private fun checkInList(): Boolean {
        var bool=false
        DatabaseHelper.checkTripList(binding.createTrip.text.toString(),
        onSuccess = {flag->
            Toast.makeText(context,"Place already exists!!",Toast.LENGTH_LONG).show()
            bool=flag
        },
            onError = {err->
                err.toException().printStackTrace()
                Log.e(TAG, "TaskException: $err")
            }
            )
        if(bool){
            return true
        }
        return false
    }
    companion object{
        private const val TAG = "BottomSheetAddDialog"
    }
}