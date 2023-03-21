package com.thelovecats.br.adivinha.service

import com.thelovecats.br.adivinha.model.Dica
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DicasClientService {

    @GET("all")
    fun listAll(): Call<MutableList<Dica>>

    @GET("list-for-game/{numeroDicas}")
    fun listForGame(@Path("numeroDicas") numeroDicas: Int): Call<MutableList<Dica>>
}