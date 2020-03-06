package com.treplabs.ddm.ddmapp.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.databinding.FragmentSigninBinding
import com.treplabs.ddm.extensions.underline
import timber.log.Timber
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    lateinit var binding: FragmentSigninBinding

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val TAG = "GoogleActivity"
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

        binding.signUpTextView.underline()
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        binding.googleSignInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
             //   firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.e(e, "Google sign in failed")
             //   updateUI(null)
            }
        }
    }


    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }
}
