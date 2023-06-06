package com.techholding.android.roammate.ui.fragment.plan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.techholding.android.roammate.data.TripAdapter
import com.techholding.android.roammate.data.TripItemListener
import com.techholding.android.roammate.databinding.FragmentPlanBinding
import com.techholding.android.roammate.model.Trip
import com.techholding.android.roammate.ui.singleplace.BottomSheetAddDialog
import com.techholding.android.roammate.utils.Utils

class PlanFragment : Fragment(), TripItemListener {

    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!
    private val planViewModel: PlanViewModel by viewModels()
    private lateinit var adapter: TripAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView()
        initObservers()
    }


    private fun initRecyclerView() {
        binding.tripRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TripAdapter(this)
        binding.tripRecyclerView.adapter = adapter
        Utils.onSwipeDelete(adapter,planViewModel, requireContext(),binding.tripRecyclerView)

    }


    private fun initListeners() {
        binding.addPlan.setOnClickListener {
            val bottomSheetDialog = BottomSheetAddDialog()
            bottomSheetDialog.show(childFragmentManager, "Modal Bottom Sheet")
        }
    }

    private fun initObservers() {
        planViewModel.tripsList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.emptyLayout.visibility = View.VISIBLE
            } else {
                adapter.updateTripList(list)
                binding.emptyLayout.visibility = View.GONE
            }
        }

        planViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                planViewModel.errorMessageShown()
            }
        }
    }

    override fun onItemClicked(item: Trip) {
        findNavController().navigate(
            PlanFragmentDirections.planToAddPlan(item.title)
        )
    }

    companion object {
        private const val TAG = "PlanFragment"
    }
}