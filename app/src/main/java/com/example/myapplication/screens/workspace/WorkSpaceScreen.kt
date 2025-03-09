package com.example.myapplication.screens.workspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults

@Composable
fun WorkSpaceScreen() {
    var selectedTab by remember { mutableStateOf("Audio") } // Trạng thái theo dõi tab hiện tại
    var lyricsText by remember { mutableStateOf("") } // Trạng thái lưu lời bài hát
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Phần header
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { /* Xử lý khi nhấn setting */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.Black
                    )
                }

                // Nav Bar
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tab Audio
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { selectedTab = "Audio" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Audio",
                            modifier = Modifier.size(if (selectedTab == "Audio") 28.dp else 24.dp),
                            tint = if (selectedTab == "Audio") Color.Blue else Color.Black
                        )
                        Text(
                            text = "Audio",
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == "Audio") FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == "Audio") Color.Blue else Color.Black
                        )
                    }

                    // Tab Lyrics
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { selectedTab = "Lyrics" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Lyrics",
                            modifier = Modifier.size(if (selectedTab == "Lyrics") 28.dp else 24.dp),
                            tint = if (selectedTab == "Lyrics") Color.Blue else Color.Black
                        )
                        Text(
                            text = "Lyrics",
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == "Lyrics") FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == "Lyrics") Color.Blue else Color.Black
                        )
                    }
                }

                // Bên phải: Icon Save
                IconButton(
                    onClick = { /* Xử lý khi nhấn save */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedTab == "Audio") {
                AudioContent()
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Input lyrics:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = lyricsText,
                        onValueChange = { lyricsText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        placeholder = { Text("Input lyrics here...") },
                        singleLine = false,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color.Blue,
                            unfocusedIndicatorColor = Color.Gray
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // Chiều cao tiêu chuẩn cho thanh điều hướng
                    .background(Color(0xFF2C3E50)) // Màu nền của thanh Nav
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Menu (3 gạch ngang) bên trái
                IconButton(onClick = { /* Xử lý khi nhấn vào menu */ }) {
                    Icon(
                        imageVector = Icons.Default.Menu, // Icon menu từ Material Icons
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                // Các icon ở giữa
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon tam giác trái (sử dụng ArrowBack làm thay thế)
                    IconButton(onClick = { /* Xử lý khi nhấn vào nút quay lại */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious, // Icon mũi tên trái
                            contentDescription = "Quay lại",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Red, shape = MaterialTheme.shapes.medium)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Icon tam giác phải (sử dụng ArrowForward làm thay thế)
                    IconButton(onClick = { /* Xử lý khi nhấn vào nút tiếp theo */ }) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow, // Icon mũi tên phải
                            contentDescription = "Tiếp theo",
                            tint = Color.White
                        )
                    }
                }

                // Biểu tượng thùng rác bên phải
                IconButton(onClick = { /* Xử lý khi nhấn vào thùng rác */ }) {
                    Icon(
                        imageVector = Icons.Default.Delete, // Icon thùng rác từ Material Icons
                        contentDescription = "Thùng rác",
                        tint = Color.White
                    )
                }
            }


        }
    }


}
