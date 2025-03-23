package com.example.myapplication.screens.workspace


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.models.ToolViewModel
import com.example.myapplication.R
import com.example.myapplication.models.JsonFileManager
import com.example.myapplication.models.ToolViewModelFactory


@Composable
fun SaveProjectScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPro: () -> Unit,

    toolViewModel: ToolViewModel = viewModel(
        factory = ToolViewModelFactory(
            jsonFileManager = JsonFileManager(LocalContext.current)
        )
    )
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        IconButton(
            onClick = onNavigateBack,
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Save Project",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .aspectRatio(1f)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cover ART",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }


            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = toolViewModel.titleText,
                    onValueChange = { toolViewModel.updateTitle(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Title name",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                TextField(
                    value = toolViewModel.descriptionText,
                    onValueChange = { toolViewModel.updateDescription(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = {
                        Text(
                            text = "Description",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    ),
                    maxLines = 5
                )
            }

            
            Button(
                onClick = {
                    toolViewModel.addProject(
                        title = toolViewModel.titleText,
                        description = toolViewModel.descriptionText,
                        imageRes = R.drawable.project1
                    )
                    onNavigateToPro()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE)
                )
            ) {
                Text(
                    text = "Post",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}