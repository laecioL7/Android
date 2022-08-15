package com.thelovecats.br.adivinha.model

class Dica(
    val titulo: String,
    val descricao: String,
    val tipo: TipoDica,
    val pontos: Int,
    var usada: Boolean = false
    ) {

}