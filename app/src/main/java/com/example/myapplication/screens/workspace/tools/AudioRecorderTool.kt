package com.example.myapplication.screens.workspace.tools


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.models.ToolViewModel

import kotlin.random.Random
import com.example.myapplication.models.JsonFileManager
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.models.ToolViewModelFactory
@Composable
fun AudioRecorderTool(onPowerClick: () -> Unit,
                      viewModel: ToolViewModel = viewModel(
                          factory = ToolViewModelFactory(
                              jsonFileManager = JsonFileManager(LocalContext.current)
                          )
                      )) {
    var isRecording by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }

    val waveformData = remember { generateWaveformData() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = formatTime(viewModel.elapsedTime.toInt()),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.width(80.dp))
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Power",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onPowerClick() }
            )
        }



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF2C2C2C))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 1f,
                    pathEffect = pathEffect
                )


                waveformData.forEachIndexed { index, amplitude ->
                    val x = index * (size.width / waveformData.size)
                    val y = size.height / 2 + amplitude * 50
                    drawLine(
                        color = Color(0xFF00FF00),
                        start = Offset(x, size.height / 2),
                        end = Offset(x, y),
                        strokeWidth = 2f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = if (isPlaying) Color.Green else Color.White
                )
            }


            IconButton(
                onClick = { isRecording = !isRecording },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Record",
                    tint = if (isRecording) Color.Red else Color.White
                )
            }


            IconButton(
                onClick = { isRecording = false; isPlaying = false },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Stop,
                    contentDescription = "Stop",
                    tint = Color.White
                )
            }
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


private fun generateWaveformData(): List<Float> {
    return List(100) { Random.nextFloat() - 1f }
}