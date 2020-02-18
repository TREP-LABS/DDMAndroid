package com.treplabs.ddm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.treplabs.ddm.ddmapp.screens.diagnose.DiagnoseViewModel
import com.treplabs.ddm.ddmapp.screens.history.HistoryViewModel
import com.treplabs.ddm.ddmapp.screens.login.SignInViewModel
import com.treplabs.ddm.ddmapp.screens.profile.ProfileViewModel
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

}
