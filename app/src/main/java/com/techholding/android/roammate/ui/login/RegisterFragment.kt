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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.techholding.android.roammate.R
import com.techholding.android.roammate.databinding.FragmentRegisterBinding
import com.techholding.android.roammate.utils.AuthManager
import java.util.regex.Pattern


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confPassword: String

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        initListeners()

        return binding.root
    }


    private fun initListeners() {
        binding.loginButton.setOnClickListener {
            doEmailSignUp()
        }

        binding.phoneLogin.setOnClickListener {
            goToPhoneScreen()
        }

        binding.googleLogin.setOnClickListener {
            doGoogleLogin()
        }

        binding.loginAccount.setOnClickListener {
            goToLoginScreen()
        }
    }

    private fun goToPhoneScreen() {
        findNavController().navigate(
            RegisterFragmentDirections.signupToPhone()
        )
    }

    private fun goToLoginScreen() {
        findNavController().popBackStack()
    }

    private fun doGoogleLogin() {
        val signInIntent: Intent = AuthManager.getGoogleSignInClient().signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun check(email: String, password: String, confPassword: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLogin.error = "Please enter valid e-mail address."
            return false
        } else if (password.isEmpty() || !Pattern.compile(
                "^(?=.*[!@#\$%^&*(),.?\":{}|<>])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}\$"
            ).matcher(password).matches()
        ) {
            binding.passwordLogin.error =
                "Password must contain at least 1 special character with " +
                        "each upper case letter and lower case letter and a digit having minimum length " +
                        "of 8 characters."
            return false
        } else if (password != confPassword) {
            binding.confirmPasswordLogin.error = "Passwords do not match."
            return false
        }
        return true
    }

    private fun doEmailSignUp() {
        email = binding.emailLogin.text.toString().trim()
        password = binding.passwordLogin.text.toString().trim()
        confPassword = binding.confirmPasswordLogin.text.toString().trim()
        if (check(email, password, confPassword)) {
            AuthManager.createUserWithEmailAndPassword(email, password, onSuccess = {
                binding.emailLogin.text.clear()
                binding.passwordLogin.text?.clear()
                binding.confirmPasswordLogin.text?.clear()
                goToMainScreen()
            }, onError = {
                Toast.makeText(context, "Failed to Sign up", Toast.LENGTH_LONG).show()
                it.exception?.printStackTrace()
                Log.e(TAG, "TaskException: " + it.exception!!.toString())
            })
        }
    }

    private fun goToMainScreen() {
        findNavController().popBackStack()
        findNavController().navigate(
            R.id.navigation_explore
        )
    }

    companion object {
        private const val TAG = "RegisterFragment"
    }
}