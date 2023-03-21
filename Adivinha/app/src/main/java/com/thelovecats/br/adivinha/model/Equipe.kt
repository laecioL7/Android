package com.thelovecats.br.adivinha.model

import android.os.Parcelable
import android.os.Parcel

class Equipe(
    val id: Int,
    val descricao: String?,
    val idCor: Int,
    var listaDeDicasAcertadas: MutableList<Dica>
) {

    internal object Compare {
        fun max(a: Equipe, b: Equipe): Equipe {
            return if (a.contarPontos() > b.contarPontos()) a else b
        }
    }

    private fun contarPontos(): Int {
        var soma = 0
        listaDeDicasAcertadas.forEach { dica ->
            soma += dica.pontos
        }
        return soma
    }
}