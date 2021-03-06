package com.treplabs.ddm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.treplabs.ddm.ddmapp.screens.condition.ChooseConditionViewModel
import com.treplabs.ddm.ddmapp.screens.demo.DemoViewModel
import com.treplabs.ddm.ddmapp.screens.diagnosisresult.DiagnoseResultViewModel
import com.treplabs.ddm.ddmapp.screens.diagnose.DiagnoseViewModel
import com.treplabs.ddm.ddmapp.screens.history.HistoryViewModel
import com.treplabs.ddm.ddmapp.screens.signin.SignInViewModel
import com.treplabs.ddm.ddmapp.screens.otp.OTPViewModel
import com.treplabs.ddm.ddmapp.screens.otpdialog.OTPDialogViewModel
import com.treplabs.ddm.ddmapp.screens.password.CreatePasswordViewModel
import com.treplabs.ddm.ddmapp.screens.profile.editprofile.EditProfileViewModel
import com.treplabs.ddm.ddmapp.screens.profile.profilepage.ProfileViewModel
import com.treplabs.ddm.ddmapp.screens.settings.SettingsViewModel
import com.treplabs.ddm.ddmapp.screens.shared.FilterableDataSharedViewModel
import com.treplabs.ddm.ddmapp.screens.signup.SignUpViewModel
import com.treplabs.ddm.ddmapp.screens.symptoms.SymptomsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SymptomsViewModel::class)
    abstract fun bindSymptomsViewModel(viewModel: SymptomsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiagnoseResultViewModel::class)
    abstract fun bindDiagnoseResultViewModel(viewModel: DiagnoseResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseConditionViewModel::class)
    abstract fun bindChooseConditionViewModel(viewModel: ChooseConditionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DemoViewModel::class)
    abstract fun bindDemoViewModel(viewModel: DemoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterableDataSharedViewModel::class)
    abstract fun bindFilterableDataSharedViewModel(viewModel: FilterableDataSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    abstract fun bindEditProfileViewModel(viewModel: EditProfileViewModel): ViewModel
}
