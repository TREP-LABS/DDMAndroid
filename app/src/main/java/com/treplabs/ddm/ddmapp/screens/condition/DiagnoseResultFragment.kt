package com.treplabs.ddm.ddmapp.screens.condition

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentDiagnoseBinding
import com.treplabs.ddm.databinding.FragmentDiagnoseResultBinding
import com.treplabs.ddm.databinding.FragmentSymptomsBinding
import com.treplabs.ddm.ddmapp.datasources.repositories.SymptomsRepository
import javax.inject.Inject

class DiagnoseResultFragment : BaseViewModelFragment() {

    lateinit var binding: FragmentDiagnoseResultBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiagnoseResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnoseResultBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiagnoseResultViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun getViewModel() = viewModel

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("Diagnose Result", false)
        invalidateToolbarElevation(0)
    }

}
