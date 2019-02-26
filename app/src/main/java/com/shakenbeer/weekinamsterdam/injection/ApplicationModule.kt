package com.shakenbeer.weekinamsterdam.injection

import android.content.Context
import com.google.gson.GsonBuilder
import com.shakenbeer.weekinamsterdam.BuildConfig
import com.shakenbeer.weekinamsterdam.Const
import com.shakenbeer.weekinamsterdam.WiaApplication
import com.shakenbeer.weekinamsterdam.data.FlightsSource
import com.shakenbeer.weekinamsterdam.data.RemoteFlightsRepo
import com.shakenbeer.weekinamsterdam.data.remote.RemoteFlightsSource
import com.shakenbeer.weekinamsterdam.data.rest.FlightsService
import com.shakenbeer.weekinamsterdam.data.rest.model.ServerError
import com.shakenbeer.weekinamsterdam.domain.repo.FlightsRepo
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class ApplicationModule(private val application: WiaApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .create()

        val client = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY })
            }
        }.build()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Const.FLIGHTS_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideFlightsService(retrofit: Retrofit): FlightsService = retrofit.create(FlightsService::class.java)

    @Singleton
    @Provides
    fun provideErrorConverter(retrofit: Retrofit): Converter<ResponseBody, ServerError> {
        return retrofit.responseBodyConverter(ServerError::class.java, emptyArray())
    }

    @Singleton
    @Provides
    @Named(REMOTE_SOURCE)
    fun provideRemoteFlightSource(
        flightsService: FlightsService,
        errorConverter: Converter<ResponseBody, ServerError>
    ): FlightsSource {
        return RemoteFlightsSource(flightsService, errorConverter)
    }

    @Singleton
    @Provides
    @Named(REMOTE_REPO)
    fun provideFlightsRepo(@Named(REMOTE_SOURCE) flightsSource: FlightsSource): FlightsRepo {
        return RemoteFlightsRepo(flightsSource)
    }

    companion object {
        const val REMOTE_SOURCE = "remote_source"
        const val REMOTE_REPO = "remote_repo"
    }
}
