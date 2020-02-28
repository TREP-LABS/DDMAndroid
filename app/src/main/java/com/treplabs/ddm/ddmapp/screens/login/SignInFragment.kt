package com.treplabs.ddm.ddmapp.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.databinding.FragmentSigninBinding
import com.treplabs.ddm.extensions.underline

class SignInFragment : BaseFragment() {

    lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        binding.signUpTextView.underline()

        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }
}
