package com.thelovecats.br.adivinha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thelovecats.br.adivinha.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewJogar.setOnClickListener(this)
        binding.viewDefinicoes.setOnClickListener(this)
        binding.viewListar.setOnClickListener(this)
        binding.viewOpcoes.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id ) {
            R.id.view_jogar  -> iniciarJogo()
            R.id.view_definicoes  -> abrirDefinicoes()
            R.id.view_listar  -> abrirListagem()
            R.id.view_opcoes  -> abrirOpcoes()
        }
    }

    private fun iniciarJogo() {
        //cria uma intent de activity
        val intent = Intent(this, GameActivity::class.java)
        //abre outra activity
        startActivity(intent)
        //encerra a atual
        finish()
    }

    private fun abrirDefinicoes() {
        //cria uma intent de activity
        val intent = Intent(this, DefinicoesActivity::class.java)
        //abre outra activity
        startActivity(intent)
        //encerra a atual
        finish()
    }

    private fun abrirListagem() {
        TODO("Not yet implemented")
    }

    private fun abrirOpcoes() {
        TODO("Not yet implemented")
    }
}