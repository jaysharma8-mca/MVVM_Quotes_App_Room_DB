package com.jay.kotlin.projects.mvvmsampleapp.data.network.responses

import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.User

//this is the json response
data class AuthResponse (
    val code : String?,
    val message : String?,
    val user: User?
)