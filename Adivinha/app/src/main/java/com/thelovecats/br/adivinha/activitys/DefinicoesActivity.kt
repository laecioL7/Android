package com.thelovecats.br.adivinha.activitys

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.thelovecats.br.adivinha.R
import com.thelovecats.br.adivinha.databinding.ActivityDefinicoesBinding
import com.thelovecats.br.adivinha.infraestrutura.SharedPreferencesUtil
import com.thelovecats.br.adivinha.utils.AdivinhaConstants
import com.thelovecats.br.adivinha.utils.ToastUtil


class DefinicoesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDefinicoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefinicoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //esconder barra de navegação
        supportActionBar?.hide()

        binding.btnSalvar.setOnClickListener(this)

        carregarDefinicoes()
    }

    private fun carregarDefinicoes() {
        val numJogadores = SharedPreferencesUtil(this).getInt(AdivinhaConstants.CHAVES_PREFS.NUMERO_JOGADORES)

        when(numJogadores){
            0 ->  binding.radioGroup.check(binding.rb4.id)
            4 -> binding.radioGroup.check(binding.rb4.id)
            6 -> binding.radioGroup.check(binding.rb6.id)
            8 -> binding.radioGroup.check(binding.rb8.id)
            10 -> binding.radioGroup.check(binding.rb10.id)
            12 -> binding.radioGroup.check(binding.rb12.id)
        }

        val tempoAdivinhacao = SharedPreferencesUtil(this).getInt(AdivinhaConstants.CHAVES_PREFS.TEMPO_ADIVINHACAO)

        when(tempoAdivinhacao) {
            30*1000 -> binding.radioGroupTime.check(binding.rbTime30.id)
            45*1000 -> binding.radioGroupTime.check(binding.rbTime45.id)
            60*1000 -> binding.radioGroupTime.check(binding.rbTime60.id)
        }

        val qtdDicas = SharedPreferencesUtil(this).getInt(AdivinhaConstants.CHAVES_PREFS.QTD_DICAS)

        when(qtdDicas) {
            5 -> binding.radioGroupQtdDicas.check(binding.rbQtd5.id)
            8 -> binding.radioGroupQtdDicas.check(binding.rbQtd8.id)
            10 -> binding.radioGroupQtdDicas.check(binding.rbQtd10.id)
        }
    }

    override fun onClick(view: View) {
        if(view.id == R.id.btn_salvar){
            val radioButton: RadioButton = binding.root.findViewById(binding.radioGroup.checkedRadioButtonId)
            val radioButtonTime: RadioButton = binding.root.findViewById(binding.radioGroupTime.checkedRadioButtonId)
            val radioButtonQtdDicas: RadioButton = binding.root.findViewById(binding.radioGroupQtdDicas.checkedRadioButtonId)

            salvarDefinicoes(radioButton.text.toString().toInt(),radioButtonTime.text.toString().toInt(),radioButtonQtdDicas.text.toString().toInt())

            //encerra a actvity atual
            finish()
        }
    }

    fun salvarDefinicoes(numJogadores: Int, tempoAdivinhacao: Int, QuantidadeDeDicas: Int){
        ToastUtil(this).showToastShort("O jogo será jogado com $numJogadores jogadores")
        SharedPreferencesUtil(this).storeInt(AdivinhaConstants.CHAVES_PREFS.NUMERO_JOGADORES, numJogadores)
        SharedPreferencesUtil(this).storeInt(AdivinhaConstants.CHAVES_PREFS.TEMPO_ADIVINHACAO, tempoAdivinhacao * 1000)
        SharedPreferencesUtil(this).storeInt(AdivinhaConstants.CHAVES_PREFS.QTD_DICAS, QuantidadeDeDicas)
    }
}