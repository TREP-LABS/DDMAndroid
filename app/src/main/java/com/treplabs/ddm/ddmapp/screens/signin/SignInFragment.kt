package com.treplabs.ddm.ddmapp.screens.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.base.BaseViewModelFragment
import com.treplabs.ddm.databinding.FragmentSigninBinding
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.screens.otp.OTPViewModel
import com.treplabs.ddm.ddmapp.validateTextLayouts
import com.treplabs.ddm.extensions.observeNonNull
import com.treplabs.ddm.extensions.stringContent
import com.treplabs.ddm.extensions.underline
import com.treplabs.ddm.utils.EventObserver
import timber.log.Timber
import javax.inject.Inject

class SignInFragment : BaseViewModelFragment() {

    lateinit var binding: FragmentSigninBinding

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SignInViewModel

    companion object {
        private const val RC_SIGN_IN = 1001
    }

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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)
        binding.signUpTextView.underline()

        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        binding.googleSignInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.passwordSignInButton.setOnClickListener {
            if (validateTextLayouts(binding.emailEditText, binding.passwordEditText)) {
                viewModel.signInWithPassword(
                    SignInRequest(binding.emailEditText.stringContent(), binding.passwordEditText.stringContent())
                )
            }
        }

        viewModel.signInComplete.observe(this, EventObserver {
            if (it) findNavController().navigate(
                SignInFragmentDirections.actionLoginFragmentToDiagnoseFragment()
            )
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                viewModel.signInWithGoogle(account)
            } catch (e: ApiException) {
                Timber.e(e, "Google sign in failed")
            }
        }
    }

    override fun getViewModel()  = viewModel

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }
}
