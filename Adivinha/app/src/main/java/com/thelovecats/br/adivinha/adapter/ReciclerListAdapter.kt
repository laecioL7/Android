package com.thelovecats.br.adivinha.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thelovecats.br.adivinha.databinding.CardListLayoutBinding
import com.thelovecats.br.adivinha.model.Dica

class ReciclerListAdapter(val dicaLista: MutableList<Dica>) : RecyclerView.Adapter<ReciclerListAdapter.DicaHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaHolder {
        val itemBinding = CardListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DicaHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DicaHolder, position: Int) {
        val dicaBean: Dica = dicaLista[position]
        holder.bind(dicaBean)
    }

    override fun getItemCount(): Int {
        return dicaLista.size
    }

    inner class DicaHolder(private val itemBinding: CardListLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dicaBean: Dica) {
             itemBinding.lblTitulo.text = dicaBean.titulo
             itemBinding.lblPontos.text = dicaBean.pontos.toString()
        }
    }
}