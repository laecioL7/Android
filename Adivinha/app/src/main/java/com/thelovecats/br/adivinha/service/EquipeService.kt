package com.thelovecats.br.adivinha.service

import com.thelovecats.br.adivinha.R
import com.thelovecats.br.adivinha.model.Dica
import com.thelovecats.br.adivinha.model.Equipe
import com.thelovecats.br.adivinha.utils.AdivinhaConstants

class EquipeService {

    val listaDeCores = listOf<String>(AdivinhaConstants.CORES.COR_EQUIPE_1,AdivinhaConstants.CORES.COR_EQUIPE_2,
        AdivinhaConstants.CORES.COR_EQUIPE_3, AdivinhaConstants.CORES.COR_EQUIPE_4, AdivinhaConstants.CORES.COR_EQUIPE_5,
        AdivinhaConstants.CORES.COR_EQUIPE_6)

    //lista de equipes padrão
    var listaEquipes: MutableList<Equipe> = mutableListOf(Equipe(0,"Equipe Vermelha", R.drawable.layout_time_1_vermelho,
        emptyList<Dica>().toMutableList()
    ),
        Equipe(1,"Equipe Azul", R.drawable.layout_time_2_azul,emptyList<Dica>().toMutableList()),
    )

    fun obterEquipes(numeroJogadores: Int): MutableList<Equipe> {

        if(numeroJogadores == 4){
            return listaEquipes
        }

        var contagemEquipes = 3

        while (contagemEquipes <= numeroJogadores/2) {                    // 1
            listaEquipes.add(Equipe(contagemEquipes - 1,"Equipe "+ getCorEquipe(contagemEquipes),getDrawableColor(contagemEquipes),emptyList<Dica>().toMutableList()))
            contagemEquipes ++
        }


        return listaEquipes
    }

    fun getCorEquipe(equipeId : Int) : String{

        when(equipeId){
            3 -> return "Verde"
            4 -> return "Amarelo"
            5 -> return "Rosa"
            6 -> return "Roxo"
        }

        return ""
    }

    fun getDrawableColor(equipeId : Int) : Int{
        when(equipeId){
            3 -> return R.drawable.layout_time_3_verde
            4 -> return R.drawable.layout_time_4_amarelo
            5 -> return R.drawable.layout_time_5_rosa
            6 -> return R.drawable.layout_time_6_roxo
        }

        return R.drawable.layout_time_1_vermelho
    }
}