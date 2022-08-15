package com.thelovecats.br.adivinha.utils

import android.content.Context
import android.widget.Toast

class ToastUtil(private val context: Context) {

    fun showToastShort(texto: String){
        Toast.makeText(context,texto, Toast.LENGTH_SHORT).show()
    }
}