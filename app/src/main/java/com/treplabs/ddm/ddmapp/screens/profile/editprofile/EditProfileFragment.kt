package com.treplabs.ddm.ddmapp.screens.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentEditProfileBinding
import com.treplabs.ddm.databinding.FragmentProfileBinding
import com.treplabs.ddm.ddmapp.screens.diagnosisresult.DiagnoseResultFragmentArgs

import javax.inject.Inject

class EditProfileFragment : BaseViewModelFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: EditProfileViewModel

    lateinit var binding: FragmentEditProfileBinding

    var isSignUpFlow = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        isSignUpFlow = EditProfileFragmentArgs.fromBundle(arguments!!).isSignUpFlow
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("Edit Profile", true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
