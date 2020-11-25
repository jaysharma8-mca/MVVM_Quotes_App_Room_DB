package com.jay.kotlin.projects.mvvmsampleapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jay.kotlin.projects.mvvmsampleapp.data.db.AppDatabase
import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.Quote
import com.jay.kotlin.projects.mvvmsampleapp.data.network.MyApi
import com.jay.kotlin.projects.mvvmsampleapp.data.network.SafeApiRequest
import com.jay.kotlin.projects.mvvmsampleapp.data.preferences.PreferenceProvider
import com.jay.kotlin.projects.mvvmsampleapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private const val MINIMUM_INTERVAL = 6
class QuotesRepository (
    private val api: MyApi,
    private val db : AppDatabase,
    private val prefs : PreferenceProvider
): SafeApiRequest() { //Whenever api request needed then use safeapirequest
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever{
            saveQuotes(it)
        }
    }

    suspend fun getQuotes() : LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes(){
        val lastSavedAt = prefs.getLastSavedAt()
        if(lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))){
            val response = apiRequest {
                api.getQuotes()
            }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean{
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefs.saveLastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}