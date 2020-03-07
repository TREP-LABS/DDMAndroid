package com.treplabs.ddm.ddmapp.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentDiagnoseBinding
import com.treplabs.ddm.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment : BaseViewModelFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SettingsViewModel

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("Settings", true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
