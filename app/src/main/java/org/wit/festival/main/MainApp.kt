package org.wit.festival.main

import android.app.Application
import org.wit.festival.models.FestivalJSONStore
import org.wit.festival.models.FestivalMemStore
import org.wit.festival.models.FestivalStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var festivals: FestivalStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // festivals = FestivalMemStore()
        festivals = FestivalJSONStore(applicationContext)
        i("Festival started")
    }
}