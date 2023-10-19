package org.wit.festival.main

import android.app.Application
import org.wit.festival.models.FestivalModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val festivals = ArrayList<FestivalModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Festival started")

        // festivals.add(FestivalModel("One", "About one..."))
        // festivals.add(FestivalModel("Two", "About two..."))
        // festivals.add(FestivalModel("Three", "About three..."))
    }
}