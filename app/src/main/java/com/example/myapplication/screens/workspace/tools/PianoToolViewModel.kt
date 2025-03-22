package com.example.myapplication.screens.workspace.tools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class PianoToolViewModel : ViewModel() {
    var isTimerRunning by mutableStateOf(false)
    var elapsedTime by mutableStateOf(0L)
    var currentRecording by mutableStateOf(listOf<Pair<String, Pair<Long, Long>>>())
    var recordings by mutableStateOf(listOf<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>())

    private var trackCounter by mutableStateOf(1)
    private var sessionDuration by mutableStateOf(0L)

    private var timerJob: Job? = null
    private val keyDownTimes = mutableMapOf<String, Long>()

    fun toggleTimer() {
        if (isTimerRunning) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        isTimerRunning = true
        currentRecording = emptyList()
        elapsedTime = 0L
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            val startTime = System.currentTimeMillis()
            while (isTimerRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
            }
        }
    }

    private fun stopTimer() {
        isTimerRunning = false
        timerJob?.cancel()
        sessionDuration = elapsedTime

            val trackName = "Piano $trackCounter"
            recordings = recordings + (trackName to (currentRecording to sessionDuration))
            trackCounter++

    }


    fun onKeyDown(note: String) {
        if (isTimerRunning) {
            keyDownTimes[note] = elapsedTime
        }
    }


    fun onKeyUp(note: String) {
        if (isTimerRunning) {
            val startTime = keyDownTimes[note] ?: return
            val duration = elapsedTime - startTime
            currentRecording = currentRecording + (note to (startTime to duration))
            keyDownTimes.remove(note)
        }
    }

    fun resetTimer() {
        elapsedTime = 0L
        currentRecording = emptyList()
        keyDownTimes.clear()
    }

    fun reset() {
        currentRecording = emptyList()
        elapsedTime = 0L
        keyDownTimes.clear()
    }
}