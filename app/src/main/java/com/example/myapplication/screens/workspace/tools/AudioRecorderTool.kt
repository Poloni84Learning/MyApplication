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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap

import kotlin.random.Random

@Composable
fun AudioRecorderTool() {
    var isRecording by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var recordingTime by remember { mutableStateOf(0) } // Thời gian ghi âm (giây)
    val waveformData = remember { generateWaveformData() } // Dữ liệu waveform giả lập
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back to Login",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)) // Màu nền tối
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Phần header: Hiển thị thời gian ghi âm
        Text(
            text = formatTime(recordingTime),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Phần body: Hiển thị waveform
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF2C2C2C)) // Màu nền waveform
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

                // Vẽ waveform
                waveformData.forEachIndexed { index, amplitude ->
                    val x = index * (size.width / waveformData.size)
                    val y = size.height / 2 + amplitude * 50
                    drawLine(
                        color = Color(0xFF00FF00), // Màu waveform
                        start = Offset(x, size.height / 2),
                        end = Offset(x, y),
                        strokeWidth = 2f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Phần footer: Các nút điều khiển
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút phát lại
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

            // Nút ghi âm
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

            // Nút dừng
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
}

// Hàm định dạng thời gian (giây -> "mm:ss")
private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}

// Hàm tạo dữ liệu waveform giả lập
private fun generateWaveformData(): List<Float> {
    return List(100) { Random.nextFloat() - 0.5f } // Tạo dữ liệu ngẫu nhiên
}