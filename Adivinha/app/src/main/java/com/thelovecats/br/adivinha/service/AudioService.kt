package com.thelovecats.br.adivinha.service

import android.content.Context
import android.media.MediaPlayer
import com.thelovecats.br.adivinha.R

class AudioService {

    fun tocarAlarmeDeFimDeTempo(context: Context){
        val mediaPlayer = MediaPlayer.create(
            context,
            R.raw.alarm)
        mediaPlayer.start()
    }

    fun tocarSomAcerto(context: Context){
        val mediaPlayer = MediaPlayer.create(
            context,
            R.raw.coin)
        mediaPlayer.start()
    }
}