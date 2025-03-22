package com.example.myapplication.screens.workspace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LyricsViewModel : ViewModel() {
    var lyricsText by mutableStateOf("")
        private set

    fun updateLyrics(newText: String) {
        lyricsText = newText
    }

    fun clearLyrics() {
        lyricsText = ""
    }
}