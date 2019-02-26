package com.shakenbeer.weekinamsterdam.injection

import com.shakenbeer.weekinamsterdam.presentation.FlightsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun inject(flightsViewModel: FlightsViewModel)
}