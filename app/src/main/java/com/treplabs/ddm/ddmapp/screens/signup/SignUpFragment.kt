package com.treplabs.ddm.ddmapp.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentSigninBinding
import com.treplabs.ddm.databinding.FragmentSignupBinding
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.models.request.SignUpRequest
import com.treplabs.ddm.ddmapp.screens.signin.SignInViewModel
import com.treplabs.ddm.ddmapp.validateTextLayouts
import com.treplabs.ddm.extensions.stringContent
import com.treplabs.ddm.extensions.underline
import com.treplabs.ddm.utils.EventObserver
import javax.inject.Inject

class SignUpFragment : BaseViewModelFragment() {

    lateinit var binding: FragmentSignupBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun getViewModel() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding.signInLink.underline()

        binding.proceedButton.setOnClickListener {
            if (validateTextLayouts(
                    binding.firstNameEditText, binding.lastNameEditText, binding.emailEditText,
                    binding.passwordEditText, binding.confirmPasswordEditText
                )
            ) {
                val firstPassword = binding.passwordEditText.stringContent()
                val secondPassword = binding.confirmPasswordEditText.stringContent()

                if (firstPassword.length < 8) {
                    showSnackBar("Password must be greater than or equal to 8 characters")
                    return@setOnClickListener
                }
                if (firstPassword != secondPassword) {
                    showSnackBar("Passwords do not match")
                    return@setOnClickListener
                }
                viewModel.signUpWithPassword(
                    SignUpRequest(
                        binding.emailEditText.stringContent(),
                        binding.passwordEditText.stringContent(),
                        "${binding.firstNameEditText.stringContent()} ${binding.lastNameEditText.stringContent()}"
                    )
                )
            }
        }

        binding.signInLink.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

        viewModel.signUpComplete.observe(this, EventObserver {
            if (it) {
                showDialogWithAction(title = "Sign up complete",
                    body = "SignUp complete, click okay to sign in with your newly created account",
                    positiveAction = {
                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
                    })
            }
        })
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }
}
