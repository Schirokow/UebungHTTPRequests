package com.example.bunghttprequests.data

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Starte Koin
        startKoin {
            androidLogger() // Koin-Logs in Logcat ausgeben (nützlich für Debugging)
            androidContext(this@MyApplication) // Den Android-Context für Koin bereitstellen
            modules(appModule) // Lade unser Modul mit den Rezepten
        }
    }
}

