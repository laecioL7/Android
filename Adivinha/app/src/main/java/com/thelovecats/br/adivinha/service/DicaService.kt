package com.thelovecats.br.adivinha.service

import com.thelovecats.br.adivinha.model.Dica
import com.thelovecats.br.adivinha.model.TipoDica

class DicaService {

    fun obterDicas(): MutableList<Dica> {
        val dica1 = Dica(titulo = "Irineu você não sabe nem eu",
            descricao = "Irineu irineu irineu voce não sabe nem eu irineu irineu....",
            tipo = TipoDica("Meme"),
            pontos = 3)

        val dica2 = Dica(titulo = "Xuxa falando senta lá Claudia",
            descricao = "Xuxa dando uma patada em uma criança em um programa de Tv",
            tipo = TipoDica("Meme"),
            pontos = 2)

        val dica3 = Dica(titulo = "Silvio Santos caindo de patinete",
            descricao = "Apresentador da tv brasileira a não sei quantos anos",
            tipo = TipoDica("Personalidade Brasileira"),
            pontos = 4)

        var list = mutableListOf(dica1, dica2, dica3)

        return list
    }

    //TODO: chamar client para baixar dicas
}