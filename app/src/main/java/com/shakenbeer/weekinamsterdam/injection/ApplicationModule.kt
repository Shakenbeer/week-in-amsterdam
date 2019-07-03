package com.shakenbeer.weekinamsterdam.injection

import android.content.Context
import com.google.gson.GsonBuilder
import com.shakenbeer.weekinamsterdam.BuildConfig
import com.shakenbeer.weekinamsterdam.Connectivity
import com.shakenbeer.weekinamsterdam.Const
import com.shakenbeer.weekinamsterdam.data.FlightsSource
import com.shakenbeer.weekinamsterdam.data.RemoteFlightsRepo
import com.shakenbeer.weekinamsterdam.data.remote.RemoteFlightsSource
import com.shakenbeer.weekinamsterdam.data.rest.FlightsService
import com.shakenbeer.weekinamsterdam.data.rest.model.ServerError
import com.shakenbeer.weekinamsterdam.domain.repo.FlightsRepo
import com.shakenbeer.weekinamsterdam.domain.usecase.GetNextWeekFlightsUseCase
import com.shakenbeer.weekinamsterdam.isConnectedToInterned
import com.shakenbeer.weekinamsterdam.presentation.FlightsViewModel
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {

    single { provideConnectivity(androidContext()) }

    single { provideRetrofit() }

    single { provideFlightsService(get()) }

    single { provideErrorConverter(get()) }

    single(named(REMOTE_SOURCE)) { provideRemoteFlightSource(get(), get()) }

    single(named(REMOTE_REPO)) { provideFlightsRepo(get(named(REMOTE_SOURCE))) }

    //TODO flights feature, should be scoped
    viewModel { FlightsViewModel(get(), get()) }

    factory { GetNextWeekFlightsUseCase(get(named(REMOTE_REPO))) }
}

const val REMOTE_SOURCE = "remote_source"
const val REMOTE_REPO = "remote_repo"

fun provideConnectivity(context: Context): Connectivity {
    return object : Connectivity {
        override fun isConnectedToInternet(): Boolean {
            return context.isConnectedToInterned()
        }
    }
}

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

fun provideFlightsService(retrofit: Retrofit): FlightsService = retrofit.create(FlightsService::class.java)

fun provideErrorConverter(retrofit: Retrofit): Converter<ResponseBody, ServerError> {
    return retrofit.responseBodyConverter(ServerError::class.java, emptyArray())
}

fun provideRemoteFlightSource(
    flightsService: FlightsService,
    errorConverter: Converter<ResponseBody, ServerError>
): FlightsSource {
    return RemoteFlightsSource(flightsService, errorConverter)
}

fun provideFlightsRepo(flightsSource: FlightsSource): FlightsRepo {
    return RemoteFlightsRepo(flightsSource)
}