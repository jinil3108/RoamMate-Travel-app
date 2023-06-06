package com.techholding.android.roammate.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.techholding.android.roammate.databinding.FragmentSettingsBinding
import com.techholding.android.roammate.ui.MainActivity
import com.techholding.android.roammate.utils.AuthManager
import com.techholding.android.roammate.utils.DatabaseHelper


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayUser()
        initListeners()
    }

    private fun displayUser() {
        DatabaseHelper.getUserFullName(
            onSuccess = { fullName ->
                if (fullName == null) {
                    binding.viewUserName.text = "Welcome User"
                } else {
                    binding.viewUserName.text = "Welcome $fullName"
                }
            },
            onError = { databaseError ->
                databaseError.toException().printStackTrace()
                Log.e(TAG, "TaskException: $databaseError")
            }
        )
    }

    private fun initListeners() {
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.settingsToEditProfile())
        }

        binding.logOutButton.setOnClickListener {
            AuthManager.signOut()
            val intent = Intent(context, MainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finishAffinity()
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, _ ->
            if (binding.switchDarkMode.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    companion object {
        private const val TAG = "SettingsFragment"
    }
}