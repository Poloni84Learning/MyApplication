package com.example.myapplication.screens.workspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipPrevious
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import com.example.myapplication.screens.workspace.tools.AudioRecorderTool
import com.example.myapplication.screens.workspace.tools.PianoTool
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.screens.workspace.tools.BassTool
import com.example.myapplication.screens.workspace.tools.ImportTrackTool
import com.example.myapplication.screens.workspace.tools.ToolViewModel
import com.example.myapplication.screens.workspace.tools.ToolType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkSpaceScreen(onNavigateSave: ()-> Unit,
                    toolViewModel: ToolViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf("Audio") }
    var showMenu by remember { mutableStateOf(false) }

    var showBass by remember { mutableStateOf(false) }
    var showImportTrack by remember { mutableStateOf(false) }
    var showAudioRecorder by remember { mutableStateOf(false) }
    var showPiano by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Spacer(modifier = Modifier.height(40.dp))


        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
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

            IconButton(
                onClick = onNavigateSave,
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

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                showAudioRecorder -> AudioRecorderTool(onPowerClick = { showAudioRecorder = false },viewModel = toolViewModel)
                showPiano -> PianoTool(onPowerClick = { showPiano = false },viewModel = toolViewModel)
                showBass -> BassTool()
                showImportTrack -> ImportTrackTool(onPowerClick = { showImportTrack = false })
                selectedTab == "Audio" -> AudioContent(viewModel = toolViewModel)

                else -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Input lyrics:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = toolViewModel.lyricsText,
                            onValueChange = { toolViewModel.updateLyrics(it) },
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
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFF2C3E50))
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Red, shape = MaterialTheme.shapes.medium)
                            .clickable {
                                if (showPiano) {
                                    toolViewModel.toggleTimer(ToolType.PIANO)}
                                else if (showAudioRecorder) {
                                    toolViewModel.toggleTimer(ToolType.AUDIO)
                                }else if (showImportTrack){
                                    toolViewModel.toggleTimer(ToolType.CHORD)}
                            }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Trash",
                        tint = Color.White
                    )
                }
            }
        }


        if (showMenu) {
            ModalBottomSheet(
                onDismissRequest = { showMenu = false },
                sheetState = sheetState,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
            ) {

                MenuOptions(onOptionSelected = { option ->
                    when (option) {

                        "Voice" -> {
                            showAudioRecorder = true
                            toolViewModel.resetTimer()

                            showPiano = false
                            showBass = false
                            showImportTrack = false

                        }
                        "Piano" -> {
                            showPiano = true
                            toolViewModel.resetTimer()

                            showAudioRecorder = false
                            showBass = false
                            showImportTrack = false
                        }
                        "Bass" -> {
                            showBass = true
                            showPiano = false
                            showAudioRecorder = false
                            showImportTrack = false

                        }
                        "Import Chords" -> {
                            showImportTrack = true
                            toolViewModel.resetTimer()

                            showPiano = false
                            showBass = false
                            showAudioRecorder = false

                        }
                        else -> {

                        }
                    }
                    showMenu = false
                })
            }
        }
    }
}


@Composable
fun MenuOptions(onOptionSelected: (String) -> Unit) {
    val options = listOf("Voice", "Piano", "Bass", "Import Chords")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(options) { option ->
            Text(
                text = option,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(option) }
                    .padding(vertical = 12.dp)
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

