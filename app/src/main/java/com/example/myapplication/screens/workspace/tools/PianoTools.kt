package com.example.myapplication.screens.workspace.tools

import androidx.compose.foundation.background
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

@Composable
fun PianoScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        IconButton(
            onClick = { } ,
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
                .background(Color(0xFF333333)) // Màu nền tối
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Hàng phím đàn piano
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Phím trắng
                PianoKey(isBlackKey = false, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = false, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = false, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = false, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = false, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = false, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = false, modifier = Modifier.weight(1f))
            }

            // Hàng phím đen (đặt chồng lên phím trắng)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 24.dp), // Căn chỉnh vị trí phím đen
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Phím đen (bỏ qua vị trí đầu tiên)
                Spacer(modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = true, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = true, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = true, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = true, modifier = Modifier.weight(1f))
                PianoKey(isBlackKey = true, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun PianoKey(isBlackKey: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(if (isBlackKey) 120.dp else 200.dp)
            .background(
                color = if (isBlackKey) Color.Black else Color.White,
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            )
    )
}