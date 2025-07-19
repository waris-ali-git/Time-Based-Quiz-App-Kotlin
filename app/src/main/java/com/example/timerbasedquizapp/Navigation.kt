package com.example.timerbasedquizapp

// Create a new file: Navigation.kt

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun QuizNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartQuiz = {
                    navController.navigate("quiz")
                }
            )
        }

        composable("quiz") {
            val viewModel: QuizViewModel = viewModel()
            QuizScreen(
                viewModel = viewModel,
                onQuizComplete = { score ->
                    navController.navigate("result/$score") {
                        popUpTo("quiz") { inclusive = true }
                    }
                }
            )
        }

        composable("result/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            ResultScreen(
                score = score,
                onPlayAgain = {
                    navController.navigate("quiz") {
                        popUpTo("result/{score}") { inclusive = true }
                    }
                },
                onGoHome = {
                    navController.navigate("home") {
                        popUpTo(0) // Clear entire back stack
                    }
                }
            )
        }
    }
}