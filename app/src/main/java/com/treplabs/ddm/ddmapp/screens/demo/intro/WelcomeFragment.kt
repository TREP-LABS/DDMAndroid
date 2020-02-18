package com.treplabs.ddm.ddmapp.screens.demo.intro
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.treplabs.ddm.base.BaseFragment

class WelcomeFragment : BaseFragment() {

    lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        binding.getStartedButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment())
        }
        binding.signInButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment(true))
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }
}