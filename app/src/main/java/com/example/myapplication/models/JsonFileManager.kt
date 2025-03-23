package com.example.myapplication.models

import android.content.Context
import android.util.Log
import com.example.myapplication.screens.workspace.Project
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonFileManager(private val context: Context) {
    private val gson = Gson()
    private val fileName = "user_data.json"

    fun saveData(userData: UserData) {
        try {
            val jsonString = gson.toJson(userData)
            val file = File(context.filesDir, fileName)
            file.writeText(jsonString)
        } catch (e: Exception) {
            Log.e("JsonFileManager", "Error saving data: ${e.message}")
        }
    }

    fun loadData(): UserData {
        return try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                val jsonString = file.readText()
                gson.fromJson(jsonString, UserData::class.java) ?: UserData(emptyList())
            } else {
                UserData(emptyList())
            }
        } catch (e: Exception) {
            Log.e("JsonFileManager", "Error loading data: ${e.message}")
            UserData(emptyList())
        }
    }
}

data class UserData(
    val projects: List<Project> = emptyList()
)