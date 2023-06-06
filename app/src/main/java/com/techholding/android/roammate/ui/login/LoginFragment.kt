package com.techholding.android.roammate.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.navigation.fragment.findNavController
import com.techholding.android.roammate.R
import com.techholding.android.roammate.databinding.FragmentLoginBinding
import com.techholding.android.roammate.utils.AuthManager
import com.techholding.android.roammate.utils.Utils

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var googleSignInLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data

            AuthManager.handleGmailSignInResult(data, onSuccess = {
                goToMainScreen()
            }, onError = {
                Toast.makeText(context, "Sign-in Unsuccessful", Toast.LENGTH_SHORT)
                    .show()
                it.printStackTrace()
                Log.e(TAG, "Exception: $it")
            })

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthManager.setGoogleClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.loginButton.setOnClickListener {
            doEmailLogin()
        }

        binding.phoneLogin.setOnClickListener {
            goToPhoneLogin()
        }

        binding.googleLogin.setOnClickListener {
            doGoogleLogin()
        }

        binding.createAccount.setOnClickListener {
            goToSignupScreen()
        }

        binding.forgotPassword.setOnClickListener {
            sendResetPassword()
        }
    }

    private fun sendResetPassword() {
        val email = binding.emailLogin.text.toString().trim()

        if (!Utils.isValidEmail(email)) {
            binding.emailLogin.error = getString(R.string.error_enter_valid_email)
            return
        }

        AuthManager.sendPasswordResetEmail(
            email = email,
            onSuccess = {
                Toast.makeText(
                    context,
                    "Password has been sent to an e-mail address $email",
                    Toast.LENGTH_LONG
                ).show()
            },
            onError = { exception ->
                Toast.makeText(context, "Failed while sending link", Toast.LENGTH_LONG).show()
                exception.printStackTrace()
                Log.e(TAG, "TaskException: " + exception.toString())
            }
        )
    }

    private fun doEmailLogin() {
        val email = binding.emailLogin.text.toString().trim()
        val password = binding.passwordLogin.text.toString().trim()

        if (!Utils.isValidEmail(email)) {
            binding.emailLogin.error = getString(R.string.error_enter_valid_email)
            return
        }

        if (!Utils.isValidPassword(password)) {
            binding.passwordLogin.error = getString(R.string.error_enter_valid_password)
            return
        }

        AuthManager.signInWithEmailPassword(
            email = email,
            password = password,
            onSuccess = {
                binding.emailLogin.text.clear()
                binding.passwordLogin.text?.clear()
                goToMainScreen()
            },
            onError = {
                Toast.makeText(context, "Failed to Sign in", Toast.LENGTH_LONG).show()
                it.exception?.printStackTrace()
                Log.e(TAG, "TaskException: " + it.exception!!.toString())
            }
        )
    }

    private fun goToMainScreen() {
        findNavController().popBackStack()
        findNavController().navigate(
            R.id.navigation_explore
        )
    }

    private fun goToPhoneLogin() {
        findNavController().navigate(
            LoginFragmentDirections.loginToPhone()
        )
    }

    private fun doGoogleLogin() {
        val signInIntent: Intent = AuthManager.getGoogleSignInClient().signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun goToSignupScreen() {
        findNavController().navigate(
            LoginFragmentDirections.loginToSignup()
        )
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}