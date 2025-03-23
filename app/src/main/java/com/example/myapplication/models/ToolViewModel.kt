package com.example.myapplication.models


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.screens.workspace.tools.ToolType
import com.example.myapplication.R
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.collections.plus
import androidx.lifecycle.viewModelScope
import com.example.myapplication.screens.workspace.Project
import kotlinx.coroutines.delay


class ToolViewModel(private val jsonFileManager: JsonFileManager) : ViewModel() {

    var recordings by mutableStateOf(listOf<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>())
        private set

    var projects by mutableStateOf<List<Project>>(emptyList())
        private set
    var currentProject by mutableStateOf<Project?>(null)
        private set

    var lyricsText by mutableStateOf("")
        private set
    var titleText by mutableStateOf("")
        private set
    var descriptionText by mutableStateOf("")
        private set



    var isTimerRunning by mutableStateOf(false)
    var elapsedTime by mutableStateOf(0L)
    var currentRecording by mutableStateOf(listOf<Pair<String, Pair<Long, Long>>>())

    private var trackCounter1 by mutableStateOf(1)
    private var trackCounter2 by mutableStateOf(1)
    private var trackCounter3 by mutableStateOf(1)

    private var sessionDuration by mutableStateOf(0L)
    private var currentToolType by mutableStateOf(ToolType.PIANO)

    private var timerJob: Job? = null
    private val keyDownTimes = mutableMapOf<String, Long>()




    init {
        loadData()
    }

    private fun loadData() {
        val userData = jsonFileManager.loadData()
        if (userData != null) {
            projects = userData.projects
            }else {
            projects = emptyList() // Đảm bảo projects không bao giờ là null
        }

    }
    fun createNewProject() {
        currentProject = Project(
            id = UUID.randomUUID().toString(),
            title = "",
            lyrics = "",
            description = "",
            imageRes = R.drawable.project1,
            completionTime = "",
            recordings = emptyList()
        )
    }

    fun loadProject(projectId: String) {
        if (projects.isNullOrEmpty()) {
            currentProject = null
            recordings = emptyList()
        } else {
            currentProject = projects.find { it.id == projectId }
            currentProject?.let {
                recordings = it.recordings
            }
        }
    }


    fun updateProject(
        title: String,
        lyrics: String,
        description: String,
        imageRes: Int,
        completionTime: String,
        recordings: List<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>
    ) {
        if (projects.isNullOrEmpty()) {
            return
        }
        currentProject = currentProject?.copy(
            title = title,
            lyrics = lyrics,
            description = description,
            imageRes = imageRes,
            completionTime = completionTime,
            recordings = recordings
        )
        currentProject?.let { project ->
            projects = projects.filterNot { it.id == project.id } + project
            saveUserData()
        }
    }


    fun addProject(
        title: String,
        lyrics: String,
        description: String,
        imageRes: Int,
        completionTime: String,
        recordings: List<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>
    ) {
        val newProject = Project(
            id = UUID.randomUUID().toString(),
            title = title,
            lyrics = lyrics,
            description = description,
            imageRes = imageRes,
            completionTime = completionTime,
            recordings = recordings
        )
        projects = projects + newProject
        saveUserData()
    }

    private fun saveUserData() {
        val userData = UserData(projects = projects)
        jsonFileManager.saveData(userData)
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
        saveUserData()
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
        titleText = ""
        descriptionText = ""
        lyricsText = ""
        currentProject = null
        recordings = emptyList()

    }

    fun deleteProject(projectId: String) {
        projects = projects.filterNot { it.id == projectId }
        saveUserData()
    }


    fun updateLyrics(newText: String) {
        lyricsText = newText
        saveUserData()
    }

    fun clearLyrics() {
        lyricsText = ""
    }



    fun updateTitle(newText: String) {
        titleText = newText
        saveUserData()
    }

    fun clearTitle() {
        titleText = ""
    }

    fun updateDescription(newText: String) {
        descriptionText = newText
        saveUserData()
    }

    fun clearDescription() {
        descriptionText = ""
    }


}