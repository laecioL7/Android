package com.thelovecats.br.adivinha.service

import com.thelovecats.br.adivinha.client.RetroFitClient
import com.thelovecats.br.adivinha.model.Dica
import com.thelovecats.br.adivinha.model.TipoDica
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DicaService {

/*
    fun obterTodasAsDicas(): List<Dica>? {
       return obterTodasAsDicasDaApi()
    }

    /**De acordo com a qtd de jogadores ira buscar a lista de dicas
     * e chamar função para atualizar essas dicas no banco como já usadas
     * quando todas as dicas do banco forem utilizadas todas serão resetadas */
    */
    fun obterDicasParaPartida(numeroJogadores: Int): MutableList<Dica>? {
        //TODO:
        return obterTodasAsDicasBase().toMutableList()
    }

    /*
    fun obterTodasAsDicasDaApi(): List<Dica>? {
        val service = RetroFitClient.createDicasClientService()

        if(listaDeDicasCompleta != null && listaDeDicasCompleta!!.isEmpty()) {

            val call: Call<List<Dica>> = service.listAll()

            call.enqueue(object : Callback<List<Dica>> {
                override fun onResponse(call: Call<List<Dica>>, response: Response<List<Dica>>) {
                    listaDeDicasCompleta = response.body()!!
                }

                override fun onFailure(call: Call<List<Dica>>, t: Throwable) {
                    msgErro = t.message.toString()
                }

            })
        }

        return listaDeDicasCompleta
    }
    */

    fun obterTodasAsDicasBase(): MutableList<Dica> {
        val dica1 = Dica(
            titulo = "Irineu você não sabe nem eu",
            descricao = "Irineu irineu irineu voce não sabe nem eu irineu irineu....",
            tipo = TipoDica("Meme"),
            pontos = 3
        )

        val dica2 = Dica(
            titulo = "Xuxa Apresentando",
            descricao = "Xuxa dando uma patada em uma criança em um programa de Tv",
            tipo = TipoDica("Meme"),
            pontos = 2
        )

        val dica3 = Dica(
            titulo = "Silvio Santos",
            descricao = "Apresentador da tv brasileira a não sei quantos anos",
            tipo = TipoDica("Personalidade Brasileira"),
            pontos = 2
        )

        val dica4 = Dica(
            titulo = "Rodrigo Faro",
            descricao = "Apresentador da tv brasileira de programa de namoro",
            tipo = TipoDica("Personalidade Brasileira"),
            pontos = 1
        )

        val dica5 = Dica(
            titulo = "Canarinho da seleção",
            descricao = "Mascote da seleção de futebol",
            tipo = TipoDica("Meme"),
            pontos = 1
        )

        return mutableListOf(dica1, dica2, dica3, dica4, dica5)
    }
}