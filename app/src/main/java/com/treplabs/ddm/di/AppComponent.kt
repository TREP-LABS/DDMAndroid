package com.treplabs.ddm.di

import android.app.Application
import com.treplabs.ddm.ddmapp.screens.condition.ChooseConditionFragment
import com.treplabs.ddm.ddmapp.screens.demo.DemoFragment
import com.treplabs.ddm.ddmapp.screens.diagnosisresult.DiagnoseResultFragment
import com.treplabs.ddm.ddmapp.screens.diagnose.DiagnoseFragment
import com.treplabs.ddm.ddmapp.screens.history.HistoryFragment
import com.treplabs.ddm.ddmapp.screens.signin.SignInFragment
import com.treplabs.ddm.ddmapp.screens.otp.OTPFragment
import com.treplabs.ddm.ddmapp.screens.otpdialog.OTPDialogFragment
import com.treplabs.ddm.ddmapp.screens.password.CreatePasswordFragment
import com.treplabs.ddm.ddmapp.screens.profile.editprofile.EditProfileFragment
import com.treplabs.ddm.ddmapp.screens.profile.profilepage.ProfileFragment
import com.treplabs.ddm.ddmapp.screens.settings.SettingsFragment
import com.treplabs.ddm.ddmapp.screens.signup.SignUpFragment
import com.treplabs.ddm.ddmapp.screens.symptoms.SymptomsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rasheed Sulayman.
 */
@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class, GoogleServicesModule::class])
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
    fun inject(target: SettingsFragment)
    fun inject(target: SymptomsFragment)
    fun inject(target: DiagnoseResultFragment)
    fun inject(target: ChooseConditionFragment)
    fun inject(target: DemoFragment)
    fun inject(target: EditProfileFragment)


    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
