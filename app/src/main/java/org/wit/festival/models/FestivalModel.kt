package org.wit.festival.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class FestivalModel(var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var date: String = "") : Parcelable
