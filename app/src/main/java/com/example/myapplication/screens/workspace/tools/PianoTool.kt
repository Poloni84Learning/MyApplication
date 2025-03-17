package com.example.myapplication.screens.workspace.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.*
import androidx.compose.ui.unit.sp

@Composable
fun PianoTool(onPowerClick: () ->  Unit) {
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
                text = "Piano",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
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
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                PianoKey(color = Color.White, note = "C", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "D", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "E", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "F", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "G", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "A", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "B", modifier = Modifier.weight(1f))
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //  C#
                PianoKey(color = Color.Black, note = "C#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = -6.dp, y = 0.dp))
                //  D#
                PianoKey(color = Color.Black, note = "D#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = -14.dp, y = 0.dp))
                //  F#
                PianoKey(color = Color.Black, note = "F#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = 20.dp, y = 0.dp))
                //  G#
                PianoKey(color = Color.Black, note = "G#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = 13.dp, y = 0.dp))
                //  A#
                PianoKey(color = Color.Black, note = "A#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = 5.dp, y = 0.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                PianoKey(color = Color.White, note = "C", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "D", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "E", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "F", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "G", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "A", modifier = Modifier.weight(1f))
                PianoKey(color = Color.White, note = "B", modifier = Modifier.weight(1f))
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //  C#
                PianoKey(color = Color.Black, note = "C#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = -6.dp, y = 0.dp))
                //  D#
                PianoKey(color = Color.Black, note = "D#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = -14.dp, y = 0.dp))
                //  F#
                PianoKey(color = Color.Black, note = "F#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = 20.dp, y = 0.dp))
                //  G#
                PianoKey(color = Color.Black, note = "G#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = 13.dp, y = 0.dp))
                //  A#
                PianoKey(color = Color.Black, note = "A#", modifier = Modifier
                    .width(30.dp)
                    .offset(x = 5.dp, y = 0.dp))
            }
        }
    }
}

@Composable
fun PianoKey(color: Color, note: String, modifier: Modifier = Modifier) {
    val bottomRoundedShape1 = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 8.dp,
        bottomEnd = 8.dp
    )
    val bottomRoundedShape2 = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 2.dp,
        bottomEnd = 2.dp
    )

    Box(
        modifier = modifier
            .height(if (color == Color.White) 200.dp else 120.dp)
            .background(color, shape = if (color == Color.White)  bottomRoundedShape1 else bottomRoundedShape2)
            .clickable { println("Key $note pressed") },
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