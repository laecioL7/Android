package com.thelovecats.br.adivinha.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thelovecats.br.adivinha.adapter.ReciclerListAdapter
import com.thelovecats.br.adivinha.client.RetroFitClient
import com.thelovecats.br.adivinha.databinding.ActivityListaDicasBinding
import com.thelovecats.br.adivinha.model.Dica
import com.thelovecats.br.adivinha.service.DicaService
import com.thelovecats.br.adivinha.utils.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaDicaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaDicasBinding
    private var msgErro = ""
    var listaDeDicasCompleta: List<Dica>? = null
    val dicaService = DicaService()
    val context = this

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ReciclerListAdapter.DicaHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaDicasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //esconder barra de navegação
        supportActionBar?.hide()

        layoutManager = LinearLayoutManager(this)
        binding.recyclerviewDicas.layoutManager = layoutManager

        adapter = ReciclerListAdapter(dicaService.obterTodasAsDicasBase())
        binding.recyclerviewDicas.adapter = adapter

        obterTodasAsDicasDaApi()
    }


    private fun obterTodasAsDicasDaApi(): List<Dica>? {
        val service = RetroFitClient.createDicasClientService()

        if(listaDeDicasCompleta.isNullOrEmpty()) {

            val call: Call<MutableList<Dica>> = service.listAll()

            call.enqueue(object : Callback<MutableList<Dica>> {

                override fun onFailure(call: Call<MutableList<Dica>>, t: Throwable) {
                    msgErro = t.message.toString()
                    ToastUtil(context).showToastShort(msgErro)
                }

                override fun onResponse(
                    call: Call<MutableList<Dica>>,
                    response: Response<MutableList<Dica>>
                ) {
                    listaDeDicasCompleta = response.body()!!
                    binding.recyclerviewDicas.adapter = ReciclerListAdapter(response.body()!!)

                }

            })
        }

        return listaDeDicasCompleta
    }
}