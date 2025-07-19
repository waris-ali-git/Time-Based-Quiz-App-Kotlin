package com.example.timerbasedquizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizState())
    val uiState: StateFlow<QuizState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private val questions = QuizData.questions

    init {
        startTimer()
    }

    fun selectAnswer(answerIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedAnswer = answerIndex)
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        val currentQuestion = questions[currentState.currentQuestionIndex]

        // Calculate score
        val newScore = if (currentState.selectedAnswer == currentQuestion.correctAnswer) {
            currentState.score + 1
        } else {
            currentState.score
        }

        // Move to next question or complete quiz
        if (currentState.currentQuestionIndex < questions.size - 1) {
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = currentState.currentQuestionIndex + 1,
                score = newScore,
                selectedAnswer = null,
                timeLeft = 10
            )
            startTimer()
        } else {
            // Quiz completed
            _uiState.value = _uiState.value.copy(
                score = newScore,
                isQuizCompleted = true
            )
            timerJob?.cancel()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (i in 10 downTo 1) {
                _uiState.value = _uiState.value.copy(timeLeft = i)
                delay(1000)
            }
            // Time up - auto move to next question
            nextQuestion()
        }
    }

    fun resetQuiz() {
        timerJob?.cancel()
        _uiState.value = QuizState()
        startTimer()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}