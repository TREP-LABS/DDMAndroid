package com.treplabs.ddm.ddmapp.screens.diagnose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentDiagnoseBinding
import javax.inject.Inject

class DiagnoseFragment : BaseViewModelFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiagnoseViewModel

    lateinit var binding: FragmentDiagnoseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnoseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiagnoseViewModel::class.java)
        binding.viewModel = viewModel

        binding.illnessYesButton.setOnClickListener {
            findNavController().navigate(DiagnoseFragmentDirections.actionDiagnoseFragmentToChooseConditionFragment())
        }

        binding.illnessNoButton.setOnClickListener {
            findNavController().navigate(DiagnoseFragmentDirections.actionDiagnoseFragmentToSymptomsFragment())
        }

    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("Diagnose", true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
