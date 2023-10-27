package org.wit.festival.models

import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class FestivalMemStore : FestivalStore {

    val festivals = ArrayList<FestivalModel>()

    override fun findAll(): List<FestivalModel> {
        return festivals
    }

    override fun create(festival:FestivalModel) {
        festival.id = getId()
        festivals.add(festival)
        logAll()
    }

    override fun update(festival: FestivalModel) {
        var foundFestival: FestivalModel? = festivals.find { p -> p.id == festival.id }
        if (foundFestival != null) {
            foundFestival.title = festival.title
            foundFestival.description = festival.description
            foundFestival.date = festival.date
            logAll()
        }
    }

    fun logAll() {
        festivals.forEach{ i("${it}") }
    }
}