package com.example.myapplication.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonFileManager(private val context: Context) {
    private val gson = Gson()
    private val fileName = "user_data.json"

    // Lưu dữ liệu vào file JSON
    fun saveData(userData: UserData) {
        try {
            val jsonString = gson.toJson(userData)
            val file = File(context.filesDir, fileName)
            file.writeText(jsonString)
        } catch (e: Exception) {
            Log.e("JsonFileManager", "Error saving data: ${e.message}")
        }
    }

    // Đọc dữ liệu từ file JSON
    fun loadData(): UserData? {
        return try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                val jsonString = file.readText()
                gson.fromJson(jsonString, UserData::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("JsonFileManager", "Error loading data: ${e.message}")
            null
        }
    }
}

// Data class để lưu trữ tất cả dữ liệu
data class UserData(
    val recordings: List<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>,
    val lyricsText: String,
    val titleText: String,
    val descriptionText: String
)