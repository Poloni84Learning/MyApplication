package com.example.myapplication.screens.workspace.tools

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.*
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.livedata.observeAsState
import com.example.myapplication.models.ChordViewModel
import com.example.myapplication.models.ToolViewModel


@Composable
fun ImportTrackTool(onPowerClick: () ->  Unit,
                    chordViewModel: ChordViewModel = viewModel(),
                    toolViewModel: ToolViewModel = viewModel()) {
    val chords by chordViewModel.chords.observeAsState()
    val error by chordViewModel.error.observeAsState()
    var selectedNotes by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        chordViewModel.loadChords()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF1E1E1E)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Time: ${formatTime(toolViewModel.elapsedTime.toInt())}s",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "Chord",
                fontSize = 24.sp,
                color = Color.White
            )
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Power",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onPowerClick()
                    }
            )
        }


        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (chords != null) {
            val notes = chords!!.flatMap { (key, chordDetailMap) ->
                chordDetailMap.values.map { it.name }
            }
            NoteMatrix(
                notes = notes,
                onChordDown = { chordNotes ->
                    val allChordNotes = chords!!.flatMap { (key, chordDetailMap) ->
                        chordDetailMap.values.filter { it.name in chordNotes }.flatMap { it.notes }
                    }
                    selectedNotes = if (selectedNotes.containsAll(allChordNotes)) {
                        selectedNotes - allChordNotes
                    } else {
                        selectedNotes + allChordNotes
                    }
                    toolViewModel.onChordDown(allChordNotes)
                    Log.d("Damn","note: $allChordNotes")
                },
                onChordUp = { chordNotes ->
                    val allChordNotes = chords!!.flatMap { (key, chordDetailMap) ->
                        chordDetailMap.values.filter { it.name in chordNotes }.flatMap { it.notes }
                    }
                    toolViewModel.onChordUp(allChordNotes)
                },
                selectedNotes = selectedNotes
            )

        }else {
            if (error == null) {
                Text(
                    text = "Loading chords...",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }

        }
    }

}




@Composable
fun NoteButton(
    note: String,
    onChordDown: (List<String>) -> Unit,
    onChordUp: (List<String>) -> Unit,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(
                color = if (isSelected) Color.Gray else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onChordDown(listOf(note))
                        tryAwaitRelease()
                        onChordUp(listOf(note))
                    }
                )
            }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = note,
            fontSize = 12.sp,
            maxLines = 1,

        )
    }
}

@Composable
fun NoteMatrix(
    notes: List<String>,
    onChordDown: (List<String>) -> Unit,
    onChordUp: (List<String>) -> Unit,
    selectedNotes: List<String>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(notes.size) { index ->
            val note = notes[index]
            NoteButton(
                note = note,
                onChordDown = onChordDown,
                onChordUp = onChordUp,
                isSelected = selectedNotes.contains(note)
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 1000
    val secs = seconds %1000
    val secstr = secs.toString().take(2)
    val secsint = secstr.toInt()
    return String.format("%02d:%02d", minutes, secsint)
}