package com.treplabs.ddm.di

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rasheed Sulayman.
 */

@Module
class GoogleServicesModule {

    @Provides
    @Singleton
    fun provideGoogleSignInClient(app: Application): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //  .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(app, gso)
    }

}
