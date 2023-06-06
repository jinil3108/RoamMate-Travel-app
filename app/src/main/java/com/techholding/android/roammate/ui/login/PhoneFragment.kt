package com.techholding.android.roammate.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.techholding.android.roammate.databinding.FragmentPhoneBinding


class PhoneFragment : Fragment() {

    private var _binding: FragmentPhoneBinding? = null
    private val binding get() = _binding!!

    private lateinit var number: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneBinding.inflate(inflater, container, false)

        doVerification()

        return binding.root
    }

    private fun doVerification() {
        binding.verifyButton.setOnClickListener {
            number = binding.phoneLogin.text.toString().trim()

            if (checkPhone(number)) {
                findNavController().navigate(
                    PhoneFragmentDirections.phoneToOtp(number)
                )
            } else {
                binding.phoneLogin.error = "Please enter a number in proper format."
            }
        }
    }

    private fun checkPhone(number: String): Boolean {
        if (number.isNotEmpty()) {
            return true
        }
        return false
    }
}