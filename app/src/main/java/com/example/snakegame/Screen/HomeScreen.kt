package com.example.snakegame.Screen

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snakegame.R
import com.example.snakegame.SoundSettings

@Composable
fun HomeScreen(
    mediaPlayer: MediaPlayer?,
    onStartGame: () -> Unit,
    onStartEndless: () -> Unit
) {
    val PixelFont = FontFamily(Font(R.font.pixel))
    val soundOn = SoundSettings.isSoundOn

    LaunchedEffect(mediaPlayer) {
        SoundSettings.sync(mediaPlayer)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0E6A5))
    ) {
        // 🔊 Кнопка звука
        IconButton(
            onClick = {
                SoundSettings.toggle()
                SoundSettings.sync(mediaPlayer)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (soundOn)
                    Icons.AutoMirrored.Filled.VolumeUp
                else
                    Icons.AutoMirrored.Filled.VolumeOff,
                contentDescription = "Sound",
                tint = Color(0xFF2B311A),
                modifier = Modifier.size(36.dp)
            )
        }

        // Основное содержимое
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(2.dp, Color(0xFF2C3118), RoundedCornerShape(16.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Snake",
                fontSize = 75.sp,
                color = Color(0xFF2B311A),
                textAlign = TextAlign.Center,
                fontFamily = PixelFont
            )

            Spacer(modifier = Modifier.height(24.dp))

            val itemsList = listOf(
                "Classic mode" to onStartGame,
                "Infinite mode" to onStartEndless
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(itemsList) { (text, onClick) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF2B311A), RoundedCornerShape(50.dp))
                            .padding(vertical = 16.dp)
                            .clickable { onClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            color = Color.White,
                            fontSize = 20.sp,
//                            fontFamily = PixelFont
                        )
                    }
                }
            }
        }
    }
}
