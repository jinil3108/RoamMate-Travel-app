package com.techholding.android.roammate.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techholding.android.roammate.R
import java.util.concurrent.TimeUnit

object AuthManager {

    @SuppressLint("StaticFieldLeak")
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String = ""

    fun isLoggedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }

    fun signOut() {
        Firebase.auth.signOut()
    }

    fun getUserId(): String {
        return Firebase.auth.currentUser?.uid.toString()
    }

    fun sendVerification(number: String, activity: Activity) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun handleGmailSignInResult(
        data: Intent?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data)
                .getResult(ApiException::class.java)

            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess.invoke()
                    } else {
                        onError.invoke(task.exception!!)
                    }
                }
            } else {
                onError.invoke(Exception("Account is Null"))
            }
        } catch (e: ApiException) {
            onError.invoke(e)
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Task<AuthResult>) -> Unit
    ) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke(task)
                }
            }
    }

    fun setGoogleClient(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun getGoogleSignInClient(): GoogleSignInClient {
        return mGoogleSignInClient
    }

    fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    task.exception?.let {
                        onError.invoke(it)
                    }
                }
            }
    }

    fun signInWithEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Task<AuthResult>) -> Unit
    ) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke(task)
                }
            }
    }

    fun initPhoneVerification(onError: (Exception) -> Unit) {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
                onError.invoke(e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    fun signInWithPhoneAuthCredential(
        otp: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        val credential: PhoneAuthCredential =
            PhoneAuthProvider.getCredential(storedVerificationId, otp)
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess.invoke()
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    onError.invoke(Exception("InvalidOTP"))
                } else {
                    onError.invoke(task.exception!!)
                }
            }
        }
    }
}