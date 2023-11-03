package org.wit.festival.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.wit.festival.helpers.exists
import org.wit.festival.helpers.read
import org.wit.festival.helpers.write
import java.lang.reflect.Type
import java.util.*

const val USERS_JSON_FILE = "users.json"
val userGsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
val userType: Type = object : TypeToken<List<UserModel>>() {}.type

class UserJSONStore(private val context: Context) : UserStore {

    var users: MutableList<UserModel>

    init {
        users = if (exists(context, USERS_JSON_FILE)) {
            deserialize()
        } else {
            mutableListOf()
        }
    }

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = UUID.randomUUID().toString()
        users.add(user)
        serialize()
    }

    override fun update(user: UserModel) {
        val foundUser = findById(user.id)
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.email = user.email
            serialize()
        }
    }

    override fun delete(user: UserModel) {
        users.removeIf { it.id == user.id }
        serialize()
    }

    override fun findById(id: String): UserModel? {
        return users.find { it.id == id }
    }

    private fun serialize() {
        val jsonString = userGsonBuilder.toJson(users, userType)
        write(context, USERS_JSON_FILE, jsonString)
    }

    private fun deserialize(): MutableList<UserModel> {
        val jsonString = read(context, USERS_JSON_FILE)
        return userGsonBuilder.fromJson(jsonString, userType)
    }
}
