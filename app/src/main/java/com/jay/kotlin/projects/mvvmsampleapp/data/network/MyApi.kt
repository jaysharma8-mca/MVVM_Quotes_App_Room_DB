package com.jay.kotlin.projects.mvvmsampleapp.data.network

import com.jay.kotlin.projects.mvvmsampleapp.data.network.responses.AuthResponse
import com.jay.kotlin.projects.mvvmsampleapp.data.network.responses.QuotesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("login.php")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("passwd") password : String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("register.php")
    suspend fun userRegister(
        @Field("email") email: String,
        @Field("password") password : String,
        @Field("name") name : String,
        @Field("school") school : String
    ): Response<AuthResponse>

    @GET("getData.php")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object{
        operator  fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jaysharma8.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi:: class.java)
        }
    }


}