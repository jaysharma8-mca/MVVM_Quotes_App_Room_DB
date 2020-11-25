package com.jay.kotlin.projects.mvvmsampleapp

import android.app.Application
import com.jay.kotlin.projects.mvvmsampleapp.data.db.AppDatabase
import com.jay.kotlin.projects.mvvmsampleapp.data.network.MyApi
import com.jay.kotlin.projects.mvvmsampleapp.data.network.NetworkConnectionInterceptor
import com.jay.kotlin.projects.mvvmsampleapp.data.preferences.PreferenceProvider
import com.jay.kotlin.projects.mvvmsampleapp.data.repositories.QuotesRepository
import com.jay.kotlin.projects.mvvmsampleapp.data.repositories.UserRepository
import com.jay.kotlin.projects.mvvmsampleapp.ui.auth.AuthViewModelFactory
import com.jay.kotlin.projects.mvvmsampleapp.ui.home.profile.ProfileViewModelFactory
import com.jay.kotlin.projects.mvvmsampleapp.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

//DI Class
//Declared in Android Manifest
class MVVMApplication :  Application(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton {
            NetworkConnectionInterceptor(instance())
        }
        bind() from singleton {
            MyApi(instance())
        }
        bind() from singleton {
            AppDatabase(instance())
        }
        bind() from singleton {
            UserRepository(instance(), instance()) // Using both MyApi and AppDatabase in UserRepository
        }
        bind() from provider {
            AuthViewModelFactory(instance())
        }
        bind() from provider {
            ProfileViewModelFactory(instance())
        }
        bind() from singleton {
           PreferenceProvider(instance()) // Using both MyApi and AppDatabase in UserRepository
        }
        bind() from singleton {
            QuotesRepository(instance(), instance(), instance())
        }
        bind() from provider {
            QuotesViewModelFactory(instance())
        }
    }
}