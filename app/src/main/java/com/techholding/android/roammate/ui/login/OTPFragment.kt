package com.techholding.android.roammate.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.techholding.android.roammate.R
import com.techholding.android.roammate.databinding.FragmentOtpBinding
import com.techholding.android.roammate.utils.AuthManager

class OTPFragment: Fragment() {

    private lateinit var otp: String
    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthManager.initPhoneVerification(onError = {
            Toast.makeText(context, "Verification Unsuccessful", Toast.LENGTH_LONG).show()
            it.printStackTrace()
            Log.e(TAG, "Exception: $it")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)

        AuthManager.sendVerification(arguments?.getString("phone").toString(), requireActivity())
        setVerificationText()
        verifyOtp()

        return binding.root
    }

    private fun setVerificationText() {
        val description = "OTP has been sent to a phone number ${arguments?.getString("phone_number")}"
        binding.verifyDetail.text = description
    }

    private fun verifyOtp(){
        binding.phoneLogin.setOnClickListener {
            otp = binding.otpLogin.text.toString().trim()

            if (otp.isNotEmpty()) {
                AuthManager.signInWithPhoneAuthCredential(otp, onSuccess = {

                    binding.otpLogin.text.clear()
                    goToMainScreen()

                }, onError = {
                    binding.otpLogin.text.clear()
                    it.printStackTrace()
                    Toast.makeText(requireContext(),"Invalid OTP", Toast.LENGTH_LONG).show()
                    Log.e(TAG, "Exception: $it")
                })
            } else {
                Toast.makeText(requireContext(), "Enter OTP", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun goToMainScreen() {
        findNavController().popBackStack()
        findNavController().navigate(
            R.id.navigation_explore
        )
    }

    companion object {
        private const val TAG = "OTPFragment"
    }

}