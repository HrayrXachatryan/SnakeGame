package com.example.snakegame

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.snakegame.ui.theme.SnakeGameTheme

import com.example.snakegame.SoundSettings

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mediaPlayer = MediaPlayer.create(this, R.raw.background_music).apply {
            isLooping = true
        }

        SoundSettings.sync(mediaPlayer)

        setContent {
            SnakeGameTheme {
                AppNavGraph(mediaPlayer, lifecycleScope)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        SoundSettings.sync(mediaPlayer)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

