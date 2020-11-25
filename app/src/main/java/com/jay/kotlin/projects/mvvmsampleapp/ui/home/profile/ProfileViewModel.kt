package com.jay.kotlin.projects.mvvmsampleapp.ui.home.profile

import androidx.lifecycle.ViewModel
import com.jay.kotlin.projects.mvvmsampleapp.data.repositories.UserRepository

//this view model interacts with repository
class ProfileViewModel (
    repository: UserRepository
): ViewModel() {
    val user = repository.getUser()
}