package com.techholding.android.roammate.ui.tabs.gallery.uploadimage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.techholding.android.roammate.databinding.FragmentUploadImageBinding
import com.techholding.android.roammate.utils.DatabaseHelper

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUri: Uri
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val data:Intent = result.data!!
            imageUri = data.data!!
            binding.uploadImage.setImageURI(imageUri)
        }
        else{
            Toast.makeText(context,"No image selected", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadImageBinding.inflate(inflater, container, false)

        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.uploadImage.setOnClickListener {
            uploadImage()
        }

        binding.sendImage.setOnClickListener {
            uploadImageToStorage()
        }
    }

    private fun uploadImageToStorage() {
        DatabaseHelper.uploadImageToFirebase(requireContext(), arguments?.getString("placeId").toString(), imageUri, binding.uploadCaption.text.toString(), onSuccess = {
            Toast.makeText(context, "Thanks for valuable feedback.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }, onError = {
            Toast.makeText(context, "Uploading Failed", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Exception: $it")
            it.printStackTrace()
        })
    }

    private fun uploadImage() {
        val photoPicker = Intent()
        photoPicker.action = Intent.ACTION_GET_CONTENT
        photoPicker.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        photoPicker.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        photoPicker.type = "image/*"
        activityResultLauncher.launch(photoPicker)
    }

    companion object {
        private const val TAG = "UploadFragment"
    }

}