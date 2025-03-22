package com.example.myapplication.screens.workspace

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.screens.workspace.tools.PianoToolViewModel


@Composable
fun AudioContent(viewModel: PianoToolViewModel = viewModel()) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp)) {
        TimeAxis()

        Row(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .width(120.dp)

                    .padding(1.dp)
            ) {
                viewModel.recordings.forEach { (name,_) ->
                    val volume = 0.5f
                    TrackLabelLeft(name = name, volume = volume)
                }

            }


            Box(
                modifier = Modifier
                    .width(1.5.dp)
                    .fillMaxHeight()
                    .background(Color.Red)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val circleRadius = 4.dp.toPx()
                    val circleY = 0f
                    val circleX = size.width / 2


                    drawCircle(
                        color = Color.Red,
                        radius = circleRadius,
                        center = androidx.compose.ui.geometry.Offset(circleX, circleY)
                    )
                }
            }



            Column(modifier = Modifier.weight(1f)) {
                viewModel.recordings.forEach { (_, recordingData) ->

                    val (notes, sessionDuration) = recordingData


                    TrackLabelRight(sessionDuration = sessionDuration, recording = notes)
                    Log.d("Au","session: $sessionDuration")
                    val lastNote = notes.last()
                    val (noteName, info) = lastNote
                    val (startTime, duration) = info
                    val spacing2 = ((sessionDuration - duration - startTime)/ 40).toFloat().dp
                    Spacer(modifier = Modifier.width(spacing2))
                }

            }
        }
    }
}
@Composable
fun TrackLabelRight(sessionDuration: Long, recording: List<Pair<String, Pair<Long, Long>>>) {
    Spacer(modifier = Modifier.height(1.dp))

    Box(
        modifier = Modifier
            .height(58.dp)
            .width(((sessionDuration)/40).toFloat().dp)
            .background(Color(0xFF1E1E1E), RoundedCornerShape(2.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)

        ) {
            var previousTimestamp = 0L
            recording.forEach { recordingEntry ->
                val note = recordingEntry.first
                val startTime = recordingEntry.second.first
                val noteDuration = recordingEntry.second.second

                val notePosition = getNotePosition(note)

                val spacing1 = ((startTime - previousTimestamp) / 40).toFloat().dp


                    Spacer(modifier = Modifier.width(spacing1))

                    Box(
                        modifier = Modifier
                            .width((noteDuration / 40).toFloat().dp)
                    ) {
                        Column (
                            modifier = Modifier
                                .height(5.dp)
                                .fillMaxWidth()
                                .padding(0.dp)
                                .offset(y = (notePosition*8).dp)
                                .background(color = if (note.contains("#")) Color.DarkGray else Color.White)
                        ) {
                            Log.d("Au","note: $note, startTime: $startTime, noteDuration: $noteDuration, pre: $previousTimestamp")
                        }
                    }

                previousTimestamp = startTime + noteDuration
            }
        }
    }
    Spacer(modifier = Modifier.height(1.dp))
}

@Composable
fun TimeAxis() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(Color(0xFF34495E))
            .padding(start = 120.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        for (i in 0..15) {
            Row(

            ) {


                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(15.dp)
                        .background(Color.White)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "$i",
                    fontSize = 6.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun TrackLabelLeft(name: String, volume: Float) {
    Spacer(modifier = Modifier.height(1.dp))
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(58.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {

        Column {
            Text(text = name, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color.Black, RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width((volume * 100).dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(1.dp))

}


fun getNotePosition(note: String): Int {
    return when (note) {
        "C" -> 0
        "C#" -> 0
        "D" -> 1
        "D#" -> 1
        "E" -> 2
        "F" -> 3
        "F#" -> 3
        "G" -> 4
        "G#" -> 4
        "A" -> 5
        "A#" -> 5
        "B" -> 6
        else -> 0
    }
}