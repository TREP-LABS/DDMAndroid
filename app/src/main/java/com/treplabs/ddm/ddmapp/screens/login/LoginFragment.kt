package com.treplabs.ddm.ddmapp.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.databinding.FragmentLoginBinding
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }
}
