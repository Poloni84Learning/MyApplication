package com.example.myapplication.screens.workspace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun ProjectScreen(onNavigateToWorkSpace: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        // Phần header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "My Projects",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Avatar hình tròn
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable(onClick = {}), // Xử lý sự kiện nhấn avatar
                contentAlignment = Alignment.Center
            ) {
                // Thêm ảnh avatar (nếu có)
                Image(
                    painter = painterResource(id = R.drawable.avatar), // Thay bằng ảnh avatar thực tế
                    contentDescription = "Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Bên trái: Text "All" và mũi tên hướng xuống
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* Xử lý khi nhấn */ }
            ) {
                Text(
                    text = "All",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Bên phải: Hai mũi tên lên xuống và nút "New"
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* Xử lý sắp xếp tăng dần */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Sort Ascending"
                    )
                }
                IconButton(
                    onClick = { /* Xử lý sắp xếp giảm dần */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Sort Descending"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onNavigateToWorkSpace,

                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_add),
//                        contentDescription = "Add",
//                        modifier = Modifier.size(18.dp)
//                    )
                    Text("+")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Phần body: Danh sách dự án
        val projects = listOf(
            Project("Project 1", "6 days ago", R.drawable.project1), // Thay bằng ảnh thực tế
            Project("Project 2", "3 days ago", R.drawable.project2),
            Project("Project 3", "1 day ago", R.drawable.project3)
        )

        LazyColumn {
            items(projects) { project ->
                ProjectItem(project)
            }
        }
    }
}

@Composable
fun ProjectItem(project: Project) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ảnh dự án
        Image(
            painter = painterResource(id = project.imageRes),
            contentDescription = "Project Image",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Tên và thời gian hoàn thiện
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = project.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = project.completionTime,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Menu 3 dấu chấm
        IconButton(
            onClick = { /* Xử lý khi nhấn menu */ },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options"
            )
        }
    }
}

data class Project(
    val name: String,
    val completionTime: String,
    val imageRes: Int // Resource ID của ảnh
)
