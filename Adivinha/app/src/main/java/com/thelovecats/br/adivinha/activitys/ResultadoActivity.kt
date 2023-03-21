package com.thelovecats.br.adivinha.activitys

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thelovecats.br.adivinha.R
import com.thelovecats.br.adivinha.databinding.ActivityResultadosBinding
import com.thelovecats.br.adivinha.infraestrutura.SharedPreferencesUtil
import com.thelovecats.br.adivinha.model.Dica
import com.thelovecats.br.adivinha.model.Equipe
import com.thelovecats.br.adivinha.model.Rodada
import com.thelovecats.br.adivinha.utils.AdivinhaConstants
import java.lang.reflect.Type

class ResultadoActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityResultadosBinding
    lateinit var listaDeEquipesPartida: MutableList<Equipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadosBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //esconder barra de navegação
        supportActionBar?.hide()

        //binding
        binding.btnProxima.setOnClickListener(this)

        carregarEquipesEPontos()

        preencherResultadoDasEquipes()

        defineEquipeVencedoraRodada()
    }

    private fun carregarEquipesEPontos() {
        // creating a variable for gson.
        val gson = Gson()
        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        val json = SharedPreferencesUtil(this).getString(AdivinhaConstants.CHAVES_EXTRA.LISTA_EQUIPES)
        // below line is to get the type of our array list.
        val type: Type? = object : TypeToken<MutableList<Equipe?>?>() {}.type
        // in below line we are getting data from gson
        // and saving it to our array list
        listaDeEquipesPartida = gson.fromJson<Any>(json, type) as MutableList<Equipe>

        val rodada = intent.getIntExtra(AdivinhaConstants.CHAVES_EXTRA.NUMERO_RODADA,1)

        if(rodada > Rodada.TRES.numeroRodada){
            binding.btnProxima.text = "Finalizar jogo"
            binding.lblVencedorRodada.text = "Equipe Vencedora do jogo:"
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_proxima) {
            finish()
        }
    }

    fun preencherResultadoDasEquipes(){
        val sb = StringBuilder()
        var cont = 1

        listaDeEquipesPartida.forEach { equipe ->
            sb.append("Dicas e pontos ${equipe.descricao} :\n")
            sb.append("Dicas acertadas = ${equipe.listaDeDicasAcertadas.count()}")
            sb.append("\n")
            sb.append("Soma de pontos = ${contarPontos(equipe.listaDeDicasAcertadas)}")
            preecherTextoDaEquipe(cont, sb.toString(), equipe.idCor)
            sb.clear()
            cont++
        }
    }

    private fun contarPontos(listaDeDicasAcertadas: MutableList<Dica>): String {
        var soma = 0
        listaDeDicasAcertadas.forEach { dica ->
            soma += dica.pontos
        }

        return soma.toString()
    }

    fun preecherTextoDaEquipe(numeroEquipe: Int, textoResultado: String, idCor: Int){
        when(numeroEquipe){
            1 -> {
                binding.lblEquipe1.visibility = View.VISIBLE
                binding.lblEquipe1.text = textoResultado
                binding.lblEquipe1.setBackgroundResource(idCor)
            }
            2 -> {
                binding.lblEquipe2.visibility = View.VISIBLE
                binding.lblEquipe2.text = textoResultado
                binding.lblEquipe2.setBackgroundResource(idCor)
            }
            3 -> {
                binding.lblEquipe3.visibility = View.VISIBLE
                binding.lblEquipe3.text = textoResultado
                binding.lblEquipe3.setBackgroundResource(idCor)
            }
            4 -> {
                binding.lblEquipe4.visibility = View.VISIBLE
                binding.lblEquipe4.text = textoResultado
                binding.lblEquipe4.setBackgroundResource(idCor)
            }
            5 -> {
                binding.lblEquipe5.visibility = View.VISIBLE
                binding.lblEquipe5.text = textoResultado
                binding.lblEquipe5.setBackgroundResource(idCor)
            }
            6 -> {
                binding.lblEquipe6.visibility = View.VISIBLE
                binding.lblEquipe6.text = textoResultado
                binding.lblEquipe6.setBackgroundResource(idCor)
            }
        }
    }

    fun defineEquipeVencedoraRodada(){
        val equipeVencedora = listaDeEquipesPartida.reduce(Equipe.Compare::max)
        binding.lblEquipeVencedora.text = equipeVencedora.descricao
        binding.lblEquipeVencedora.setBackgroundResource(equipeVencedora.idCor)
    }
}
