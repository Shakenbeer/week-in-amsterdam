package com.shakenbeer.weekinamsterdam

import android.app.Application
import com.shakenbeer.weekinamsterdam.injection.ApplicationComponent
import com.shakenbeer.weekinamsterdam.injection.ApplicationModule
import com.shakenbeer.weekinamsterdam.injection.DaggerApplicationComponent

class WiaApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}