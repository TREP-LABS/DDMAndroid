package com.treplabs.ddm.di

import android.app.Application
import com.treplabs.ddm.ddmapp.screens.login.LoginFragment
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


    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
