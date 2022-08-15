package com.thelovecats.br.adivinha

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thelovecats.br.adivinha.databinding.ActivityDefinicoesBinding
import com.thelovecats.br.adivinha.databinding.ActivityJogoBinding
import com.thelovecats.br.adivinha.model.Rodada

class DefinicoesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDefinicoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefinicoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onClick(view: View) {

    }
}