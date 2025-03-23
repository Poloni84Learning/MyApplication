package com.example.myapplication.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.screens.workspace.tools.ToolType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.collections.plus
import androidx.lifecycle.viewModelScope
import com.example.myapplication.screens.workspace.Project
import kotlinx.coroutines.delay


class ToolViewModel(private val jsonFileManager: JsonFileManager) : ViewModel() {
    var isTimerRunning by mutableStateOf(false)
    var elapsedTime by mutableStateOf(0L)
    var currentRecording by mutableStateOf(listOf<Pair<String, Pair<Long, Long>>>())
    var recordings by mutableStateOf(listOf<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>())
        private set

    var projects by mutableStateOf(listOf<Project>())
        private set

    private var trackCounter1 by mutableStateOf(1)
    private var trackCounter2 by mutableStateOf(1)
    private var trackCounter3 by mutableStateOf(1)

    private var sessionDuration by mutableStateOf(0L)
    private var currentToolType by mutableStateOf(ToolType.PIANO)

    private var timerJob: Job? = null
    private val keyDownTimes = mutableMapOf<String, Long>()

    var lyricsText by mutableStateOf("")
        private set
    var titleText by mutableStateOf("")
        private set
    var descriptionText by mutableStateOf("")
        private set


    init {
        loadData()
    }

    fun addProject(title: String, description: String, imageRes: Int) {
        val newProject = Project(
            id = (projects.size + 1).toString(), // Tạo ID tự động
            name = title,
            completionTime = "Just now", // Thời gian tạo dự án
            imageRes = imageRes
        )
        projects = projects + newProject
    }

    private fun loadData() {
        viewModelScope.launch {
            val userData = jsonFileManager.loadData()
            if (userData != null) {
                recordings = userData.recordings
                lyricsText = userData.lyricsText
                titleText = userData.titleText
                descriptionText = userData.descriptionText
            }
        }
    }

    fun saveData() {
        viewModelScope.launch {
            val userData = UserData(
                recordings = recordings,
                lyricsText = lyricsText,
                titleText = titleText,
                descriptionText = descriptionText
            )
            jsonFileManager.saveData(userData)
        }
    }




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
                delay(100)
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
            ToolType.CHORD -> "Chord $trackCounter3"
        }

        recordings = recordings + (trackName to (currentRecording to sessionDuration))
        saveData()
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



    fun updateLyrics(newText: String) {
        lyricsText = newText
        saveData()
    }

    fun clearLyrics() {
        lyricsText = ""
    }



    fun updateTitle(newText: String) {
        titleText = newText
        saveData()
    }

    fun clearTitle() {
        titleText = ""
    }

    fun updateDescription(newText: String) {
        descriptionText = newText
        saveData()
    }

    fun clearDescription() {
        descriptionText = ""
    }


}