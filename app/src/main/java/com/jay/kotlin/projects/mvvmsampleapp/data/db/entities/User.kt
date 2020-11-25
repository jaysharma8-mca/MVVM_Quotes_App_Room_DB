package com.jay.kotlin.projects.mvvmsampleapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0
@Entity
data class User(val id: Int, val name: String, val email: String, val school: String){

    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}