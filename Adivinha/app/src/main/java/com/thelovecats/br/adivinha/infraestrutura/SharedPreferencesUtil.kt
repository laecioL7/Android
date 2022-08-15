package com.thelovecats.br.adivinha.infraestrutura

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

    private val spInstance : SharedPreferences =
        context.getSharedPreferences("Adivinha", Context.MODE_PRIVATE)

    fun storeString(chave: String, texto: String){
        spInstance.edit().putString(chave,texto).apply()
    }

    fun storeInt(chave: String, texto: Int){
        spInstance.edit().putInt(chave,texto).apply()
    }

    fun getString(chave: String) : String{
        //operador elvis
        return spInstance.getString(chave, "") ?: ""
    }

    fun getInt(chave: String) : Int{
        //operador elvis
        return spInstance.getInt(chave, 0) ?: 0
    }
}