package com.treplabs.ddm.ddmapp.screens.symptoms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentSymptomsBinding
import com.treplabs.ddm.ddmapp.datasources.repositories.SymptomsRepository
import com.treplabs.ddm.ddmapp.models.request.Symptom
import javax.inject.Inject

class SymptomsFragment : BaseViewModelFragment() {

    lateinit var binding: FragmentSymptomsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var symptomsRepository: SymptomsRepository

    private lateinit var viewModel: SymptomsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSymptomsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SymptomsViewModel::class.java)

        binding.symptomEditText.setAdapter(SymptomsItemsAdapter(mainActivity, symptomsRepository))

        binding.symptomEditText.setOnItemClickListener { adapter, _, position, _ ->
            binding.symptomEditText.text = null
            val symptom = adapter.getItemAtPosition(position) as Symptom
            addChipToGroup(symptom)
        }

        binding.proceedButton.setOnClickListener {
            if (binding.chipGroup.childCount < 1) {
                showSnackBar("Please enter at least one symptom to proceed")
                return@setOnClickListener
            }
            viewModel.getRecommendedCondition(getConditionsMap())
        }

    }


    fun getConditionsMap(): Set<Symptom> {
        val symptoms = mutableSetOf<Symptom>()
        for (chip in binding.chipGroup.children) {
            symptoms.add(chip.tag as Symptom)
        }
        return symptoms
    }

    override fun getViewModel() = viewModel

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("Add Symptoms", false)
        invalidateToolbarElevation(0)
    }


    private fun addChipToGroup(symptom: Symptom) {
        val chip = Chip(context)
        chip.text = symptom.name
        binding.chipGroup.addView(chip)
        chip.setOnCloseIconClickListener { binding.chipGroup.removeView(chip) }
    }
}
