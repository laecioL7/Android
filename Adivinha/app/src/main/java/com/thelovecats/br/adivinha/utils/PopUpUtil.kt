package com.thelovecats.br.adivinha.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.thelovecats.br.adivinha.R

class PopUpUtil(private val context: Context) {

     fun mostrarPopUpSimples(title: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_layout)
        val body = dialog.findViewById(R.id.body) as TextView
        body.text = title
       val btn_ok = dialog.findViewById(R.id.btn_ok) as Button
       // val noBtn = dialog.findViewById(R.id.noBtn) as TextView
         btn_ok.setOnClickListener {
            dialog.dismiss()
        }
        /*noBtn.setOnClickListener { dialog.dismiss() }*/
        dialog.show()
    }
}