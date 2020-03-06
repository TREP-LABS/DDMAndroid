package com.treplabs.ddm.di

import com.treplabs.ddm.BuildConfig
import com.treplabs.ddm.auth.AccessTokenAuthenticator
import com.treplabs.ddm.auth.AccessTokenInterceptor
import com.treplabs.ddm.auth.AccessTokenProvider
import com.treplabs.ddm.ddmapp.accesstoken.AccessTokenProviderImpl
import com.treplabs.ddm.ddmapp.apis.ExampleAPIAuthService
import com.treplabs.ddm.ddmapp.apis.ExampleApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Rasheed Sulayman.
 */
@Module(includes = [LocalDataModule::class])
class APIServiceModule {

    @Provides
    @Named("DDMAPIService")
    @Singleton
    fun provideExampleServiceHttpClient(
        upstream: OkHttpClient,
        @Named("DDMAPIService") accessTokenProvider: AccessTokenProvider
    ): OkHttpClient {
        return upstream.newBuilder()
            .addInterceptor(AccessTokenInterceptor(accessTokenProvider))
            .authenticator(AccessTokenAuthenticator(accessTokenProvider))
            .build()
    }

    @Provides
    @Singleton
    fun provideExampleAPIAuthService(
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): ExampleAPIAuthService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ExampleAPIAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideExampleAPIService(
        @Named("DDMAPIService") client: Lazy<OkHttpClient>,
        gson: Gson
    ): ExampleApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ExampleApiService::class.java)
    }

    @Provides
    @Named("DDMAPIService")
    fun provideAccessTokenProvider(accessTokenProvider: AccessTokenProviderImpl): AccessTokenProvider =
        accessTokenProvider

    @Provides
    @Singleton
    fun provideGenericOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().serializeNulls().create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
