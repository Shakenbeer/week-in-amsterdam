package com.shakenbeer.weekinamsterdam

import android.app.Application
import com.shakenbeer.weekinamsterdam.injection.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WiaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@WiaApplication)
            modules(appModule)
        }
    }
}