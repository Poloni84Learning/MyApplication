package com.example.myapplication.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ToolViewModelFactory(
    private val jsonFileManager: JsonFileManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToolViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToolViewModel(jsonFileManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}