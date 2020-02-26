package com.treplabs.ddm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.treplabs.ddm.ddmapp.screens.diagnose.DiagnoseViewModel
import com.treplabs.ddm.ddmapp.screens.history.HistoryViewModel
import com.treplabs.ddm.ddmapp.screens.login.SignInViewModel
import com.treplabs.ddm.ddmapp.screens.otp.OTPViewModel
import com.treplabs.ddm.ddmapp.screens.otpdialog.OTPDialogViewModel
import com.treplabs.ddm.ddmapp.screens.password.CreatePasswordFragment
import com.treplabs.ddm.ddmapp.screens.password.CreatePasswordViewModel
import com.treplabs.ddm.ddmapp.screens.profile.ProfileViewModel
import com.treplabs.ddm.ddmapp.screens.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindLoginViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindHistoryViewModel(viewModel: HistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiagnoseViewModel::class)
    abstract fun bindDiagnoseViewModel(viewModel: DiagnoseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OTPDialogViewModel::class)
    abstract fun bindOTPDialogViewModel(viewModel: OTPDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OTPViewModel::class)
    abstract fun bindOTPViewModel(viewModel: OTPViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatePasswordViewModel::class)
    abstract fun bindCreatePasswordViewModel(viewModel: CreatePasswordViewModel): ViewModel

}
