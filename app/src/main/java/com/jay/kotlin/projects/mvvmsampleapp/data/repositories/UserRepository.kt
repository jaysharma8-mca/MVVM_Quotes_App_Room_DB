package com.jay.kotlin.projects.mvvmsampleapp.data.repositories

import com.jay.kotlin.projects.mvvmsampleapp.data.db.AppDatabase
import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.User
import com.jay.kotlin.projects.mvvmsampleapp.data.network.MyApi
import com.jay.kotlin.projects.mvvmsampleapp.data.network.SafeApiRequest
import com.jay.kotlin.projects.mvvmsampleapp.data.network.responses.AuthResponse

//this repo handles all the operations like user login, register, save to local db and get from local db
class UserRepository(
    private val api: MyApi,
    private val db : AppDatabase
) : SafeApiRequest(){

    suspend fun userLogin(
        email : String,
        password: String
    ): AuthResponse{
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userRegister(
        email : String,
        password: String,
        name: String,
        school: String
    ): AuthResponse{
        return apiRequest { api.userRegister(email, password, name, school) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)//User.kt under db/entities package

    fun getUser() = db.getUserDao().getUser()

}