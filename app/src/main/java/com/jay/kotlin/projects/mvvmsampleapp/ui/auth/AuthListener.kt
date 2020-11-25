package com.jay.kotlin.projects.mvvmsampleapp.ui.auth

import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.User

interface AuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}