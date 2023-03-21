package com.thelovecats.br.adivinha.client

import com.thelovecats.br.adivinha.service.DicasClientService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitClient {

    companion object{

        //singleton
        private lateinit var INSTANCE: Retrofit

        const val BASE_URL = "https://adivinha-service.herokuapp.com/adivinha/"

        private fun getRetrofitInstance(): Retrofit{
            val http = OkHttpClient.Builder()

            //!se não estiver
            if(!(::INSTANCE.isInitialized)) {
                INSTANCE =  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return INSTANCE
        }

        fun createDicasClientService(): DicasClientService{
            return getRetrofitInstance().create(DicasClientService::class.java)
        }

    }
}