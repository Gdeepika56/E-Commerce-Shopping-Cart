package com.example.ecommerceproject.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder().apply{
            baseUrl("https://apolisrises.co.in/myshop/index.php/")
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

}