package com.example.myapplication.screens.workspace.tools


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Types
import kotlinx.coroutines.launch


@JsonClass(generateAdapter = true)
data class ChordDetail(
    val name: String,
    val notes: List<String>,
    val intervals: List<String>,
    val midiKeys: List<Int>
)

class ChordRepository {
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    suspend fun fetchChords(): Map<String, Map<String, ChordDetail>>? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://piano-chords.p.rapidapi.com/chords")
            .get()
            .addHeader("x-rapidapi-key", "9704d2df31msh9f47f55127e2751p10cce0jsn6ed18b9394ea")
            .addHeader("x-rapidapi-host", "piano-chords.p.rapidapi.com")
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val json = response.body?.string()
                Log.d("ChordRepository", "API Response: $json")
                val type = Types.newParameterizedType(
                    Map::class.java,
                    String::class.java,
                    Types.newParameterizedType(
                        Map::class.java,
                        String::class.java,
                        ChordDetail::class.java
                    )
                )
                val adapter = moshi.adapter<Map<String, Map<String, ChordDetail>>>(type)
                adapter.fromJson(json ?: "")
            } else {
                Log.e("ChordRepository", "API call failed with code: ${response.code}")
                null
            }
        } catch (e: Exception) {
            Log.e("ChordRepository", "Error fetching chords", e)
            null
        }
    }
}

class ChordViewModel : ViewModel() {
    private val repository = ChordRepository()
    val chords = MutableLiveData<Map<String, Map<String, ChordDetail>>?>()
    val error = MutableLiveData<String>()

    fun loadChords() {
        viewModelScope.launch {
            try {
                val response = repository.fetchChords()
                chords.value = response
            } catch (e: Exception) {
                error.value = "Error: ${e.message}"
                Log.e("ChordViewModel", "Error loading chords", e)
            }
        }
    }
}