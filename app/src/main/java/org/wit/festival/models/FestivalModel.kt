package org.wit.festival.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class FestivalModel(var id: Long = 0,
                         var userId: String = "",
                         var title: String = "",
                         var description: String = "",
                         var date: String = "",
                         var valueForMoney: Float = 0.0f,
                         var accessibility: Float = 0.0f,
                         var familyFriendly: Float = 0.0f,
                         var image: Uri = Uri.EMPTY,
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable