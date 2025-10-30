package com.example.snakegame

import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SoundSettings {
    var isSoundOn by mutableStateOf(true)
        private set

    fun setSoundEnabled(enabled: Boolean) {
        isSoundOn = enabled
    }

    fun toggle(): Boolean {
        val newValue = !isSoundOn
        setSoundEnabled(newValue)
        return newValue
    }

    fun sync(mediaPlayer: MediaPlayer?) {
        mediaPlayer?.let {
            if (isSoundOn && !it.isPlaying) {
                it.start()
            } else if (!isSoundOn && it.isPlaying) {
                it.pause()
            }
        }
    }
}
