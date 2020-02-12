package com.treplabs.ddm.di

import android.app.Application
import com.treplabs.ddm.ddmapp.screens.intro.WelcomeFragment
import com.treplabs.ddm.ddmapp.screens.link.ShareLinkFragment
import com.treplabs.ddm.ddmapp.screens.otp.OtpFragment
import com.treplabs.ddm.ddmapp.screens.password.CreatePasswordFragment
import com.treplabs.ddm.ddmapp.screens.profile.CreateProfileFragment
import com.treplabs.ddm.ddmapp.screens.signin.SignInFragment
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

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
