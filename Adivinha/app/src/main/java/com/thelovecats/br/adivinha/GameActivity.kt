package com.thelovecats.br.adivinha

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thelovecats.br.adivinha.databinding.ActivityJogoBinding
import com.thelovecats.br.adivinha.model.Rodada

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityJogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAcertou.setOnClickListener(this)
        binding.btnPular.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if(view.id == R.id.btn_acertou){
            //Toast.makeText(this,"Acertou",Toast.LENGTH_SHORT).show()
            //TODO: marcar dica como usada
        }
        //se for btn pular
        else if(view.id == R.id.btn_pular)
        {
            //se for rodada 1 não pula
            if(binding.lblRodada.text.contains(Rodada.UM.numeroRodada)){
                Toast.makeText(this,"Não é possível pular na rodada 1", Toast.LENGTH_SHORT).show()
            }

        }
    }
}