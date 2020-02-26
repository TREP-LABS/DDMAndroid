package com.treplabs.ddm.ddmapp.screens.otp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.treplabs.ddm.R
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.base.BaseViewModelDialogFragment
import com.treplabs.ddm.databinding.FragmentSigninBinding
import com.treplabs.ddm.ddmapp.screens.profile.ProfileViewModel
import javax.inject.Inject

class OTPDialogFragment : BaseViewModelDialogFragment() {

    lateinit var binding: FragmentSigninBinding

    private lateinit var viewModel: OTPDialogViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        private const val PHONE_NUMBER_KEY = "phone_number _key"
        private const val TAG = "OTPDialogFragment"

        fun <T> show(
            parentFragment: T,
            phoneNumber: String
        ) where T : Fragment, T : OTPOperationsCallback {
            val dialog = OTPDialogFragment().apply {
                arguments = Bundle().apply { putString(PHONE_NUMBER_KEY, phoneNumber) }
            }
            dialog.show(parentFragment.childFragmentManager, TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentSigninBinding.inflate(LayoutInflater.from(mainActivity))
        binding.lifecycleOwner = this
        return MaterialDialog(mainActivity)
            .customView(view = binding.root, noVerticalPadding = true, dialogWrapContent = true)
            .noAutoDismiss()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OTPDialogViewModel::class.java)
    }

}

interface OTPOperationsCallback {
    fun onOTPVerified(dialog: OTPDialogFragment)
    fun onOTPVerificationFailed(dialog: OTPDialogFragment)
}
