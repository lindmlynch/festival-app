package org.wit.festival.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
    fun findById(id: String): UserModel?
}

