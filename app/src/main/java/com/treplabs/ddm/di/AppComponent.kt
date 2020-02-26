package com.treplabs.ddm.di

import android.app.Application
import com.treplabs.ddm.ddmapp.screens.diagnose.DiagnoseFragment
import com.treplabs.ddm.ddmapp.screens.history.HistoryFragment
import com.treplabs.ddm.ddmapp.screens.login.SignInFragment
import com.treplabs.ddm.ddmapp.screens.otp.OTPFragment
import com.treplabs.ddm.ddmapp.screens.otpdialog.OTPDialogFragment
import com.treplabs.ddm.ddmapp.screens.password.CreatePasswordFragment
import com.treplabs.ddm.ddmapp.screens.profile.ProfileFragment
import com.treplabs.ddm.ddmapp.screens.signup.SignUpFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rasheed Sulayman.
 */
@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    //TODO inject stuff
    fun inject(target: SignInFragment)
    fun inject(target: HistoryFragment)
    fun inject(target: ProfileFragment)
    fun inject(target: DiagnoseFragment)
    fun inject(target: SignUpFragment)
    fun inject(target: OTPDialogFragment)
    fun inject(target: OTPFragment)
    fun inject(target: CreatePasswordFragment)


    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
