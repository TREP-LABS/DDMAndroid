package com.treplabs.ddm.ddmapp.screens.condition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentChooseConditionBinding
import com.treplabs.ddm.databinding.FragmentSymptomsBinding
import com.treplabs.ddm.ddmapp.datasources.repositories.SymptomsRepository
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.ddmapp.FilterableItemsAdapter
import com.treplabs.ddm.ddmapp.datasources.repositories.ConditionsRepository
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.ddmapp.screens.symptoms.SymptomsFragmentDirections
import com.treplabs.ddm.ddmapp.screens.symptoms.SymptomsViewModel
import com.treplabs.ddm.utils.EventObserver
import javax.inject.Inject

class ChooseConditionFragment : BaseViewModelFragment() {

    lateinit var binding: FragmentChooseConditionBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var conditionsRepository: ConditionsRepository

    private lateinit var viewModel: ChooseConditionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseConditionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChooseConditionViewModel::class.java)

        binding.symptomEditText.setAdapter(FilterableItemsAdapter(mainActivity, conditionsRepository))

        binding.symptomEditText.setOnItemClickListener { adapter, _, position, _ ->
            binding.symptomEditText.text = null
            val condition = adapter.getItemAtPosition(position) as Condition
            addChipToGroup(condition)
        }

        binding.proceedButton.setOnClickListener {
            if (binding.chipGroup.childCount < 1) {
                showSnackBar("Please a condition to proceed")
                return@setOnClickListener
            }

            if (binding.chipGroup.childCount > 1) {
                showSnackBar("Please enter jut one condition to proceed")
                return@setOnClickListener
            }

            findNavController().navigate(
                SymptomsFragmentDirections.actionSymptomsFragmentToDiagnoseResultFragment(getSelectedCondition())
            )
        }
    }


    private fun getSelectedCondition() = binding.chipGroup[0].tag as Condition

    override fun getViewModel() = viewModel

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("Select condition", false)
        invalidateToolbarElevation(0)
    }

    private fun addChipToGroup(condition: Condition) {
        val chip = Chip(context)
        chip.text = condition.name
        binding.chipGroup.removeAllViews()
        binding.chipGroup.addView(chip)
        chip.tag = condition
        chip.setOnCloseIconClickListener { binding.chipGroup.removeView(chip) }
    }
}
