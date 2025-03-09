package com.example.myapplication.screens.workspace.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp

@Composable
fun PianoTool() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E1E)) // Màu nền tối
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Piano",
                fontSize = 24.sp,

                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Hàng phím đàn
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Phím trắng
                PianoKey(color = Color.White, note = "C")
                PianoKey(color = Color.White, note = "D")
                PianoKey(color = Color.White, note = "E")
                PianoKey(color = Color.White, note = "F")
                PianoKey(color = Color.White, note = "G")
                PianoKey(color = Color.White, note = "A")
                PianoKey(color = Color.White, note = "B")
            }

            // Hàng phím đen
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Phím đen
                PianoKey(color = Color.Black, note = "C#", modifier = Modifier.width(40.dp))
                PianoKey(color = Color.Black, note = "D#", modifier = Modifier.width(40.dp))
                Spacer(modifier = Modifier.width(80.dp)) // Khoảng trống giữa các phím đen
                PianoKey(color = Color.Black, note = "F#", modifier = Modifier.width(40.dp))
                PianoKey(color = Color.Black, note = "G#", modifier = Modifier.width(40.dp))
                PianoKey(color = Color.Black, note = "A#", modifier = Modifier.width(40.dp))
            }
        }

}

@Composable
fun PianoKey(color: Color, note: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(if (color == Color.White) 200.dp else 120.dp)
            .background(color, shape = RoundedCornerShape(4.dp))
            .clickable { println("Key $note pressed") }, // Xử lý khi nhấn phím
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = note,
            fontSize = 14.sp,

            color = if (color == Color.White) Color.Black else Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}