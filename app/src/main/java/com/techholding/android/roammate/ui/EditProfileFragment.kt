package com.techholding.android.roammate.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.techholding.android.roammate.databinding.FragmentEditProfileBinding
import com.techholding.android.roammate.utils.DatabaseHelper

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        initListeners()
        setToolBar()
        return binding.root
    }
    private fun initListeners(){
        binding.saveBtn.setOnClickListener {
            DatabaseHelper.setUserDetails(binding.firstName.text.toString(),binding.lastName.text.toString(),binding.emailId.text.toString(),binding.address.text.toString(),
            onSuccess = {
                Toast.makeText(context,"Data added successfully",Toast.LENGTH_LONG).show()

            },
                onError = {
                    Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()
                }
                )
        }
    }
    private fun setToolBar() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "Edit your Profile"
    }

}