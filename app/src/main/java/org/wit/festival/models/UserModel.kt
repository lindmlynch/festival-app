package org.wit.festival.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: String = "",
    var username: String = "",
    var email: String = "",
    var password: String? = null) : Parcelable