package com.example.snakegame

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snakegame.Screen.Game
import com.example.snakegame.Screen.State
import com.example.snakegame.SoundSettings

@Composable
fun Snake(
    game: Game,
    mediaPlayer: MediaPlayer?,
    onGameOver: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state = game.state.collectAsState(initial = null)
    val PixelFont = FontFamily(Font(R.font.pixel))
    val soundOn = SoundSettings.isSoundOn

    LaunchedEffect(mediaPlayer) {
        SoundSettings.sync(mediaPlayer)
    }

    if (state.value?.gameOver == true) {
        onGameOver(state.value?.score ?: 0)
        return
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0E6A5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }, modifier = Modifier.size(48.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color(0xFF2B311A),
                        modifier = Modifier.size(36.dp)
                    )
                }
                Text(
                    text = "Your Score: ${state.value?.score ?: 0}",
                    color = Color(0xFF2B311A),
                    textAlign = TextAlign.Start,
                    fontSize = 30.sp,
                    fontFamily = PixelFont,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            IconButton(
                onClick = {
                    SoundSettings.toggle()
                    SoundSettings.sync(mediaPlayer)
                },
                modifier = Modifier.size(48.dp)
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
        }

        state.value?.let { Board(it) }

        Buttons { direction -> game.move = direction }
    }
}

@Composable
fun Board(state: State) {
    BoxWithConstraints(modifier = Modifier.padding(16.dp)) {
        val tileSize = maxWidth / Game.BOARD_SIZE

        Box(
            modifier = Modifier
                .size(maxWidth)
                .background(Color(0xFFD0E6A5))
                .border(2.dp, Color(0xFF2C3118))
        )

        Box(
            modifier = Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(Color(0xFF2B311A), CircleShape)
        )

        state.snake.forEach { segment ->
            Box(
                modifier = Modifier
                    .offset(x = tileSize * segment.first, y = tileSize * segment.second)
                    .size(tileSize)
                    .background(Color(0xFF2B311A), RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun Buttons(onDirectionChange: (Pair<Int, Int>) -> Unit) {
    val buttonSize = Modifier.size(64.dp)
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
        Button(
            onClick = { onDirectionChange(Pair(0, -1)) },
            modifier = buttonSize,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B311A)),
            shape = RoundedCornerShape(4.dp)
        ) { Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Up", tint = Color.White) }

        Row {
            Button(
                onClick = { onDirectionChange(Pair(-1, 0)) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B311A)),
                shape = RoundedCornerShape(4.dp)
            ) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Left", tint = Color.White) }

            Spacer(modifier = buttonSize)

            Button(
                onClick = { onDirectionChange(Pair(1, 0)) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B311A)),
                shape = RoundedCornerShape(4.dp)
            ) { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Right", tint = Color.White) }
        }

        Button(
            onClick = { onDirectionChange(Pair(0, 1)) },
            modifier = buttonSize,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B311A)),
            shape = RoundedCornerShape(4.dp)
        ) { Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Down", tint = Color.White) }
    }
}


