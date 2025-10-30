package com.example.snakegame.Screen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

data class State(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    val score: Int,
    val gameOver: Boolean = false
)

class Game(
    private val scope: CoroutineScope,
    private val infiniteMode: Boolean = true
) {
    companion object {
        const val BOARD_SIZE = 16
        const val INITIAL_LENGTH = 4
    }

    private val mutableState = MutableStateFlow(
        State(
            food = generateFood(emptyList()),
            snake = listOf(Pair(7, 7)),
            score = 0
        )
    )

    val state: Flow<State> = mutableState
    var move = Pair(1, 0)

    init {
        scope.launch {
            var snakeLength = INITIAL_LENGTH

            while (true) {
                delay(150)
                mutableState.update { currentState ->
                    if (currentState.gameOver) return@update currentState

                    val head = currentState.snake.first()
                    var newX = head.first + move.first
                    var newY = head.second + move.second

                    if (infiniteMode) {
                        newX = (newX + BOARD_SIZE) % BOARD_SIZE
                        newY = (newY + BOARD_SIZE) % BOARD_SIZE
                    } else if (newX !in 0 until BOARD_SIZE || newY !in 0 until BOARD_SIZE) {
                        return@update currentState.copy(gameOver = true)
                    }

                    val newHead = Pair(newX, newY)
                    if (currentState.snake.contains(newHead)) {
                        return@update currentState.copy(gameOver = true)
                    }

                    var newScore = currentState.score
                    var newLength = snakeLength
                    val newFood =
                        if (newHead == currentState.food) {
                            newScore += 1
                            newLength++
                            generateFood(listOf(newHead) + currentState.snake.take(snakeLength))
                        } else currentState.food

                    snakeLength = newLength

                    currentState.copy(
                        food = newFood,
                        snake = listOf(newHead) + currentState.snake.take(snakeLength - 1),
                        score = newScore
                    )
                }
            }
        }
    }

    private fun generateFood(snake: List<Pair<Int, Int>>): Pair<Int, Int> {
        var food: Pair<Int, Int>
        do {
            food = Pair(Random.nextInt(BOARD_SIZE), Random.nextInt(BOARD_SIZE))
        } while (snake.contains(food))
        return food
    }
}
