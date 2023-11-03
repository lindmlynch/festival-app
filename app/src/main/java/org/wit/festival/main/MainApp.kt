package org.wit.festival.main

import android.app.Application
import org.wit.festival.models.FestivalJSONStore
import org.wit.festival.models.FestivalMemStore
import org.wit.festival.models.FestivalStore
import org.wit.festival.models.UserJSONStore
import org.wit.festival.models.UserStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var festivals: FestivalStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // festivals = FestivalMemStore()
        festivals = FestivalJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        i("Festival started")
    }
}