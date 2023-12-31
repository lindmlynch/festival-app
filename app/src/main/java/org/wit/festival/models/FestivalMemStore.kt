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
            foundFestival.valueForMoney = festival.valueForMoney
            foundFestival.accessibility = festival.accessibility
            foundFestival.familyFriendly = festival.familyFriendly
            foundFestival.image = festival.image
            foundFestival.lat = festival.lat
            foundFestival.lng = festival.lng
            foundFestival.zoom = festival.zoom
            logAll()
        }
    }

    override fun delete(festival: FestivalModel) {
        festivals.remove(festival)
    }

    override fun findById(id:Long) : FestivalModel? {
        val foundFestival: FestivalModel? = festivals.find { it.id == id }
        return foundFestival
    }
    override fun findByUserId(userId: String): List<FestivalModel> {
        return festivals.filter { it.userId == userId }
    }
    fun logAll() {
        festivals.forEach{ i("${it}") }
    }
}