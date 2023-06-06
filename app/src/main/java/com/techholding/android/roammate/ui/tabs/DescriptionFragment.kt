package com.techholding.android.roammate.ui.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.techholding.android.roammate.R
import com.techholding.android.roammate.databinding.FragmentDescriptionBinding


class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    private var msg: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        if (arguments != null) {
            msg = arguments?.getString("description")
            binding.description.text = msg
        }

        binding.translateWithMl.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_translation
            )
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun getInstance(description: String): DescriptionFragment {
            val fragment = DescriptionFragment()
            val bundle = Bundle()
            bundle.putString("description", description)
            fragment.arguments = bundle
            return fragment
        }
    }


}