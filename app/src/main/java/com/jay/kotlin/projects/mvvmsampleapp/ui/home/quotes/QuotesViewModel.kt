package com.jay.kotlin.projects.mvvmsampleapp.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.jay.kotlin.projects.mvvmsampleapp.data.repositories.QuotesRepository
import com.jay.kotlin.projects.mvvmsampleapp.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {
   val quotes by lazyDeferred {
       repository.getQuotes()
   }
}