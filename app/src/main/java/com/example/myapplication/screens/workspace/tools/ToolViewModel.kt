package com.example.myapplication.screens.workspace.tools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*



class ToolViewModel : ViewModel() {
    var isTimerRunning by mutableStateOf(false)
    var elapsedTime by mutableStateOf(0L)
    var currentRecording by mutableStateOf(listOf<Pair<String, Pair<Long, Long>>>())
        var recordings by mutableStateOf(listOf<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>())

    private var trackCounter1 by mutableStateOf(1)
    private var trackCounter2 by mutableStateOf(1)
    private var trackCounter3 by mutableStateOf(1)

    private var sessionDuration by mutableStateOf(0L)
    private var currentToolType by mutableStateOf(ToolType.PIANO)

    private var timerJob: Job? = null
    private val keyDownTimes = mutableMapOf<String, Long>()

    fun toggleTimer(toolType: ToolType) {
        if (isTimerRunning) {
            stopTimer()
        } else {
            startTimer(toolType)
        }
    }

    private fun startTimer(toolType: ToolType) {
        currentToolType = toolType
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

        val trackName = when (currentToolType) {
            ToolType.PIANO -> "Piano $trackCounter1"
            ToolType.AUDIO -> "Audio $trackCounter2"
            ToolType.CHORD -> "Piano $trackCounter3"
        }

        recordings = recordings + (trackName to (currentRecording to sessionDuration))

        when (currentToolType) {
            ToolType.AUDIO -> trackCounter2++
            ToolType.PIANO -> trackCounter1++
            ToolType.CHORD -> trackCounter3++
        }
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

    fun onChordDown(notes: List<String>) {
        if (isTimerRunning) {
            notes.forEach { note ->
                keyDownTimes[note] = elapsedTime
            }
        }
    }


    fun onChordUp(notes: List<String>) {
        if (isTimerRunning) {
            val noteCount = notes.size
            notes.forEachIndexed  { index,note ->
                val startTime = keyDownTimes[note] ?: return@forEachIndexed
                val duration = (elapsedTime - startTime)/noteCount
                val customizedStartTime = startTime + index * duration
                currentRecording = currentRecording + (note to (customizedStartTime  to duration))
                keyDownTimes.remove(note)
            }
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

    var lyricsText by mutableStateOf("")
        private set

    fun updateLyrics(newText: String) {
        lyricsText = newText
    }

    fun clearLyrics() {
        lyricsText = ""
    }

    var titleText by mutableStateOf("")
        private set

    fun updateTitle(newText: String) {
        titleText = newText
    }

    fun clearTitle() {
        titleText = ""
    }

    var descriptionText by mutableStateOf("")
        private set

    fun updateDescription(newText: String) {
        descriptionText = newText
    }

    fun clearDescription() {
        descriptionText = ""
    }


}