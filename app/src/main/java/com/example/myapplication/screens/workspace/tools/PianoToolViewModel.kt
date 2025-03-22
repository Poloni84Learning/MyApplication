package com.example.myapplication.screens.workspace.tools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class PianoToolViewModel : ViewModel() {
    var isTimerRunning by mutableStateOf(false)
    var elapsedTime by mutableStateOf(0L) // Tổng thời gian thu âm
    var currentRecording by mutableStateOf(listOf<Pair<String, Pair<Long, Long>>>()) // Bản ghi âm hiện tại: (note, (startTime, duration))
    var recordings by mutableStateOf(listOf<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>()) // Danh sách các bản ghi âm với tên track và thời gian session

    private var trackCounter by mutableStateOf(1) // Số thứ tự của track
    private var sessionDuration by mutableStateOf(0L) // Thời gian giữa hai lần toggleTimer

    private var timerJob: Job? = null
    private val keyDownTimes = mutableMapOf<String, Long>() // Lưu thời điểm bắt đầu nhấn phím

    fun toggleTimer() {
        if (isTimerRunning) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        isTimerRunning = true
        currentRecording = emptyList() // Bắt đầu một bản ghi âm mới
        elapsedTime = 0L // Reset thời gian
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
        sessionDuration = elapsedTime // Lưu thời gian session

            val trackName = "Piano $trackCounter" // Tạo tên track
            recordings = recordings + (trackName to (currentRecording to sessionDuration)) // Lưu bản ghi âm hiện tại với tên track và thời gian session
            trackCounter++ // Tăng số thứ tự

    }

    // Xử lý sự kiện nhấn phím (key down)
    fun onKeyDown(note: String) {
        if (isTimerRunning) {
            keyDownTimes[note] = elapsedTime // Lưu thời điểm bắt đầu nhấn phím
        }
    }

    // Xử lý sự kiện thả phím (key up)
    fun onKeyUp(note: String) {
        if (isTimerRunning) {
            val startTime = keyDownTimes[note] ?: return // Lấy thời điểm bắt đầu nhấn phím
            val duration = elapsedTime - startTime // Tính thời gian nhấn giữ
            currentRecording = currentRecording + (note to (startTime to duration)) // Lưu vào bản ghi âm
            keyDownTimes.remove(note) // Xóa thời điểm bắt đầu nhấn phím
        }
    }

    fun resetTimer() {
        elapsedTime = 0L
        currentRecording = emptyList()
        keyDownTimes.clear() // Xóa tất cả thời điểm bắt đầu nhấn phím
    }

    fun reset() {
        currentRecording = emptyList()
        elapsedTime = 0L
        keyDownTimes.clear() // Xóa tất cả thời điểm bắt đầu nhấn phím
    }
}