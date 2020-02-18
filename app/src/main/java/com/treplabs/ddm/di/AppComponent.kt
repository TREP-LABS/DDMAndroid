package com.treplabs.ddm.di

import android.app.Application
import com.treplabs.ddm.ddmapp.screens.diagnose.DiagnoseFragment
import com.treplabs.ddm.ddmapp.screens.history.HistoryFragment
import com.treplabs.ddm.ddmapp.screens.login.LoginFragment
import com.treplabs.ddm.ddmapp.screens.profile.ProfileFragment
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
    fun inject(target: LoginFragment)
    fun inject(target: HistoryFragment)
    fun inject(target: ProfileFragment)
    fun inject(target: DiagnoseFragment)


    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
