package com.thelovecats.br.adivinha.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Log.INFO
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.thelovecats.br.adivinha.R
import com.thelovecats.br.adivinha.adapter.ReciclerListAdapter
import com.thelovecats.br.adivinha.client.RetroFitClient
import com.thelovecats.br.adivinha.databinding.ActivityJogoBinding
import com.thelovecats.br.adivinha.infraestrutura.SharedPreferencesUtil
import com.thelovecats.br.adivinha.model.Dica
import com.thelovecats.br.adivinha.model.Equipe
import com.thelovecats.br.adivinha.model.Rodada
import com.thelovecats.br.adivinha.service.AudioService
import com.thelovecats.br.adivinha.service.DicaService
import com.thelovecats.br.adivinha.service.EquipeService
import com.thelovecats.br.adivinha.utils.AdivinhaConstants
import com.thelovecats.br.adivinha.utils.ToastUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log
import kotlin.random.Random


class GameActivity : AppCompatActivity(), View.OnClickListener {

    //padrão
    var numJogadores = 4
    var rodadaAtual = 1
    private var msgErro = ""
    private var indiceDaDicaAtual = 0
    lateinit var listaDeDicasPartida: MutableList<Dica>

    //lista que vai manter todas as dicas inicias para cada inicio de rodada
    lateinit var listaDeDicasPartidaInicial: MutableList<Dica>
    lateinit var listaDeEquipesPartida: MutableList<Equipe>
    lateinit var equipeAtual: Equipe
    lateinit var dicaAtual: Dica
    lateinit var context: Context

    private lateinit var timer: CountDownTimer
    private lateinit var binding: ActivityJogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //esconder barra de navegação
        supportActionBar?.hide()

        binding.btnAcertou.setOnClickListener(this)
        binding.btnPular.setOnClickListener(this)
        binding.btnStart.setOnClickListener(this)

        //prepararDadosJogo()
        buscarDicasParaPartida()

        context = this
        timer = object : CountDownTimer(
            SharedPreferencesUtil(this).getInt(AdivinhaConstants.CHAVES_PREFS.TEMPO_ADIVINHACAO)
                .toLong(), 1000
        ) {
            //a cada segundo atualiza a tela
            override fun onTick(restante: Long) {
                binding.cronometro.text = (restante / 1000).toString()
            }

            override fun onFinish() {
                prepararTelaCardEBotoes()
                binding.btnStart.visibility = View.VISIBLE
                carregarEquipeAtual()
                AudioService().tocarAlarmeDeFimDeTempo(context)

                carregarDicaAtual()
                preencherCardDica()
            }

        }

    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_acertou) {
            AudioService().tocarSomAcerto(context)

            salvarDicaParaAEquipe()
            carregarDicaAtual()
            preencherCardDica()
        }
        //se for btn pular
        else if (view.id == R.id.btn_pular) {
            //se for rodada 1 não pula
            if (rodadaAtual == Rodada.UM.numeroRodada) {
                Toast.makeText(this, "Não é possível pular na rodada 1", Toast.LENGTH_SHORT).show()
            } else if (listaDeDicasPartida.size == 1) {
                ToastUtil(this).showToastShort("É a última dica!!! Não é possível pular")
            } else {
                carregarDicaAtual()
                preencherCardDica()
            }
        } else if (view.id == R.id.btn_start) {

            if (ehRodadaDoisOuTres()) {
                carregarDicaAtual()
                preencherCardDica()
            }

            prepararTelaCardEBotoes()
            binding.btnStart.visibility = View.GONE
            timer.start()

        }
    }

    override fun onStart() {
        super.onStart()

        if (ehRodadaDoisOuTres()) {
            binding.btnStart.visibility = View.VISIBLE
            binding.lblRodada.text = getString(R.string.rodada2) + " $rodadaAtual"
            carregarPrimeiraEquipe()

            //passando uma referencia nova de lista e não a referência da mesma com os dados
            listaDeDicasPartida.clear()
            listaDeDicasPartida =
                listaDeDicasPartidaInicial.map {
                    Dica(
                        id = it.id,
                        titulo = it.titulo,
                        descricao = it.descricao,
                        tipo = it.tipo,
                        pontos = it.pontos,
                        usada = it.usada
                    )
                } as MutableList<Dica>

        } else if (rodadaAtual > Rodada.TRES.numeroRodada) {
            ToastUtil(this).showToastShort("Fim do jogo!!!")
            //fim de jogo
            finish()
        }
    }

    private fun ehRodadaDoisOuTres() =
        rodadaAtual == Rodada.DOIS.numeroRodada || rodadaAtual == Rodada.TRES.numeroRodada

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun preencherCardDica() {
        binding.lblTituloDica.text = dicaAtual.titulo
        binding.lblDescricaoDica.text = dicaAtual.descricao
        binding.lblCategoriaDica.text = dicaAtual.tipo.descricao
        binding.lblPontosDica.text = "Pontos \n " + dicaAtual.pontos.toString()
    }

    private fun prepararDadosJogo() {
        prepararTelaCardEBotoes()
        //buscarDicasParaPartida()
        buscarEquipesParaPartida()
        carregarDicaAtual()
        carregarPrimeiraEquipe()
        preencherCardDica()
    }

    private fun buscarEquipesParaPartida() {
        numJogadores =
            SharedPreferencesUtil(this).getInt(AdivinhaConstants.CHAVES_PREFS.NUMERO_JOGADORES)
        listaDeEquipesPartida = EquipeService().obterEquipes(numJogadores)
    }

    private fun buscarDicasParaPartida() {
        binding.btnStart.visibility = View.INVISIBLE
        val service = RetroFitClient.createDicasClientService()
        val call: Call<MutableList<Dica>> =
            service.listForGame(SharedPreferencesUtil(this).getInt(AdivinhaConstants.CHAVES_PREFS.QTD_DICAS))

        call.enqueue(object : Callback<MutableList<Dica>> {

            override fun onFailure(call: Call<MutableList<Dica>>, t: Throwable) {
                msgErro = t.message.toString()
                ToastUtil(context).showToastShort(msgErro)
            }

            override fun onResponse(
                call: Call<MutableList<Dica>>,
                response: Response<MutableList<Dica>>
            ) {
                //quando receber a resposta, salva a lista e faz aparecer o botão
                listaDeDicasPartida = response.body()!!

                Log.i("GameActivity", "Lista de dicas recebida!!")
                Log.i("GameActivity", "Dadods: ${listaDeDicasPartida.toString()}")

                Log.i("GameActivity", "Visiilidade do botão: ${binding.btnStart.visibility}")
                binding.btnStart.visibility = View.VISIBLE

                prepararDadosJogo()
                //passando uma referencia nova de lista e não a referência da mesma com os dados
                listaDeDicasPartidaInicial =
                    listaDeDicasPartida.map {
                        Dica(
                            id = it.id,
                            titulo = it.titulo,
                            descricao = it.descricao,
                            tipo = it.tipo,
                            pontos = it.pontos,
                            usada = it.usada
                        )
                    } as MutableList<Dica>

            }
        })
    }


    private fun carregarDicaAtual() {

        //se n tiver mais dicas na lista
        if (listaDeDicasPartida.size < 1) {
            //acabou a rodada
            timer.cancel()
            ToastUtil(this).showToastShort("Acabou a ${binding.lblRodada.text}")
            zerarRodadaEMostrarPontos()
        } else {

            // Número aleatório de 0 ao tamanho da lista retornada do filtro
            indiceDaDicaAtual = Random.nextInt(listaDeDicasPartida.size)

            // Retorna string
            dicaAtual = listaDeDicasPartida[indiceDaDicaAtual]

            Log.i("ADIVINHA", "Tamanho da lista: " + listaDeDicasPartida.size)
        }
    }

    private fun zerarRodadaEMostrarPontos() {
        prepararTelaCardEBotoes()

        // creating a new variable for gson.
        val gson = Gson()
        // getting data from gson and storing it in a string.
        val json: String = gson.toJson(listaDeEquipesPartida)
        // below line is to save data in shared
        // prefs in the form of string.
        SharedPreferencesUtil(this).storeString(AdivinhaConstants.CHAVES_EXTRA.LISTA_EQUIPES, json)

        //proxima rodada
        rodadaAtual++

        //cria uma intent de activity
        val intent = Intent(this, ResultadoActivity::class.java)
        intent.putExtra(AdivinhaConstants.CHAVES_EXTRA.NUMERO_RODADA, rodadaAtual)
        startActivity(intent)
    }

    private fun carregarPrimeiraEquipe() {
        //carrega a equipe 1
        equipeAtual = listaDeEquipesPartida[0]
        binding.layoutFundo.setBackgroundResource(equipeAtual.idCor)
    }

    private fun carregarEquipeAtual() {
        var idEquipe = 0

        if ((equipeAtual.id + 1) < listaDeEquipesPartida.size) {
            idEquipe = equipeAtual.id + 1
        }

        //pega o proximo da listagem
        equipeAtual = listaDeEquipesPartida[idEquipe]
        //coloca a cor da equipe
        binding.layoutFundo.setBackgroundResource(equipeAtual.idCor)
    }

    private fun salvarDicaParaAEquipe() {
        equipeAtual.listaDeDicasAcertadas.add(listaDeDicasPartida[indiceDaDicaAtual])
        //remove o item da lista para não ser usado de novo
        //listaDeDicasPartida.remove(dicaAtual)
        listaDeDicasPartida.removeAt(indiceDaDicaAtual)

        Log.i("ADIVINHA", "Dicas restantes: " + listaDeDicasPartida.size)
    }

    private fun prepararTelaCardEBotoes() {
        binding.cronometro.text = "0"
        binding.layoutCardDicas.visibility =
            if (binding.layoutCardDicas.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        binding.layoutBotoesJogo.visibility =
            if (binding.layoutBotoesJogo.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
    }


}