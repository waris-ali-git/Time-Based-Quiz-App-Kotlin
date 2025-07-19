package com.example.timerbasedquizapp

data class Question(
        val id: Int,
        val question: String,
        val options: List<String>,
        val correctAnswer: Int // Index of correct answer (0-3)
    )

    data class QuizState(
        val currentQuestionIndex: Int = 0,
        val score: Int = 0,
        val timeLeft: Int = 10,
        val selectedAnswer: Int? = null,
        val isQuizCompleted: Boolean = false
    )

    object QuizData {
        val questions = listOf(
            Question(
                id = 1,
                question = "What is the capital of France?",
                options = listOf("London", "Berlin", "Paris", "Madrid"),
                correctAnswer = 2
            ),
            Question(
                id = 2,
                question = "Which planet is known as the Red Planet?",
                options = listOf("Venus", "Mars", "Jupiter", "Saturn"),
                correctAnswer = 1
            ),
            Question(
                id = 3,
                question = "What is 2 + 2?",
                options = listOf("3", "4", "5", "6"),
                correctAnswer = 1
            ),
            Question(
                id = 4,
                question = "Who painted the Mona Lisa?",
                options = listOf("Van Gogh", "Picasso", "Da Vinci", "Monet"),
                correctAnswer = 2
            ),
            Question(
                id = 5,
                question = "What is the largest mammal?",
                options = listOf("Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
                correctAnswer = 1
            )
        )
    }
