package com.example.snakegame.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snakegame.R

@Composable
fun GameOverScreen(score: Int, onTryAgain: () -> Unit, onBack: () -> Unit) {
    val PixelFont = FontFamily(Font(R.font.pixel))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0E6A5))
    ) {
        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF2B311A)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game Over!",
                color = Color(0xFF2B311A),
                fontSize = 50.sp,
                fontFamily = PixelFont,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Your Score: $score",
                color = Color(0xFF2B311A),
                fontSize = 28.sp,
                fontFamily = PixelFont,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { onTryAgain() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B311A)),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(60.dp)
            ) {
                Text("Try Again", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}
