package com.jay.kotlin.projects.mvvmsampleapp.data.network.responses

import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.Quote

//this is the json response
data class QuotesResponse (
    val isSuccessful : Boolean,
    val quotes : List<Quote>
)