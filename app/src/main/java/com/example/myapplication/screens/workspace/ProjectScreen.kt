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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.models.JsonFileManager
import com.example.myapplication.models.ToolViewModel
import com.example.myapplication.models.ToolViewModelFactory

@Composable
fun ProjectScreen(onNavigateToWorkSpace: (projectId: String?) -> Unit,
                  toolViewModel: ToolViewModel = viewModel(
                      factory = ToolViewModelFactory(
                          jsonFileManager = JsonFileManager(LocalContext.current)
                      )
                  )
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

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


            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable(onClick = {}),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.avatar),
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {  }
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


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Sort Ascending"
                    )
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Sort Descending"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        toolViewModel.reset()
                        onNavigateToWorkSpace(null)},

                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        val projects = toolViewModel.projects

        LazyColumn {
            if (!projects.isNullOrEmpty()){
            items(projects) { project ->
                ProjectItem(
                    project = project,
                    onClick = { onNavigateToWorkSpace(project.id) },
                    onDelete = { projectId ->
                        toolViewModel.deleteProject(projectId)
                    }
                )
            }}
        }
    }
}

@Composable
fun ProjectItem(
    project: Project,
    onClick: () -> Unit,
    onDelete: (String) -> Unit
) {
    var showOption by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
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

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = project.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = project.completionTime,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        IconButton(
            onClick = {showOption = true },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options"
            )
            DropdownMenu(
                expanded = showOption,
                onDismissRequest = { showOption = false }
            ) {

                DropdownMenuItem(
                    text = { Text("Share") },
                    onClick = {
                        showOption = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        onDelete(project.id)
                        showOption = false
                    }
                )
            }
        }
    }
}

data class Project(
    val id: String ,
    val title: String ,
    val lyrics: String,
    val description: String ,
    val imageRes: Int ,
    val completionTime: String ,
    val recordings: List<Pair<String, Pair<List<Pair<String, Pair<Long, Long>>>, Long>>>
)