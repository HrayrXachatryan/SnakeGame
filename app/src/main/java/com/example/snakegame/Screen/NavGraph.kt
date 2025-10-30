package com.example.snakegame

import android.media.MediaPlayer
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.composable
import com.example.snakegame.Screen.Game
import com.example.snakegame.Screen.GameOverScreen
import com.example.snakegame.Screen.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    mediaPlayer: MediaPlayer?,
    lifecycleScope: LifecycleCoroutineScope
) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = {
            val initial = initialState.destination.route
            val target = targetState.destination.route
            if (initial == target) {
                EnterTransition.None
            } else {
                val direction = when (target) {
                    "home" -> AnimatedContentTransitionScope.SlideDirection.Right
                    else -> AnimatedContentTransitionScope.SlideDirection.Left
                }
                slideIntoContainer(direction, animationSpec = tween(280)) +
                    fadeIn(animationSpec = tween(220))
            }
        },
        exitTransition = {
            val initial = initialState.destination.route
            val target = targetState.destination.route
            if (initial == target) {
                ExitTransition.None
            } else {
                val direction = when (target) {
                    "home" -> AnimatedContentTransitionScope.SlideDirection.Right
                    else -> AnimatedContentTransitionScope.SlideDirection.Left
                }
                slideOutOfContainer(direction, animationSpec = tween(220)) +
                    fadeOut(animationSpec = tween(200))
            }
        },
        popEnterTransition = {
            val initial = initialState.destination.route
            val target = targetState.destination.route
            if (initial == target) {
                EnterTransition.None
            } else {
                val direction = when (target) {
                    "home" -> AnimatedContentTransitionScope.SlideDirection.Left
                    else -> AnimatedContentTransitionScope.SlideDirection.Right
                }
                slideIntoContainer(direction, animationSpec = tween(280)) +
                    fadeIn(animationSpec = tween(220))
            }
        },
        popExitTransition = {
            val initial = initialState.destination.route
            val target = targetState.destination.route
            if (initial == target) {
                ExitTransition.None
            } else {
                val direction = when (target) {
                    "home" -> AnimatedContentTransitionScope.SlideDirection.Left
                    else -> AnimatedContentTransitionScope.SlideDirection.Right
                }
                slideOutOfContainer(direction, animationSpec = tween(220)) +
                    fadeOut(animationSpec = tween(200))
            }
        }
    ) {
        // 🏠 Главный экран
        composable("home") {
            HomeScreen(
                mediaPlayer = mediaPlayer,
                onStartGame = { navController.navigate("game_normal") },
                onStartEndless = { navController.navigate("game_endless") }
            )
        }

        // 🐍 Обычный режим
        composable("game_normal") {
            val game = Game(lifecycleScope, infiniteMode = false)
            Snake(
                game = game,
                mediaPlayer = mediaPlayer,
                onGameOver = { score -> navController.navigate("game_over/$score") },
                onBack = { navController.popBackStack() }
            )
        }

        // ♾️ Бесконечный режим
        composable("game_endless") {
            val game = Game(lifecycleScope, infiniteMode = true)
            Snake(
                game = game,
                mediaPlayer = mediaPlayer,
                onGameOver = { score -> navController.navigate("game_over/$score") },
                onBack = { navController.popBackStack() }
            )
        }

        // 💀 Экран Game Over
        composable("game_over/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            GameOverScreen(
                score = score,
                onTryAgain = { navController.popBackStack("home", inclusive = false) },
                onBack = { navController.popBackStack("home", inclusive = false) }
            )
        }
    }
}


