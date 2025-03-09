package com.example.myapplication.screens.workspace

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import kotlin.random.Random


@Composable
fun AudioContent() {
    val tracks = listOf(
        "Guitar 1" to 0.8f,
        "Voice 2" to 0.6f,
        "Bass 1" to 0.7f,
        "Drums" to 1.0f,
        "Synth" to 0.5f
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp)) {
        TimeAxis()

        Row(modifier = Modifier.fillMaxSize()) {
            // Track Labels & Volume Controls
            Column(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxWidth()
                    .padding(1.dp)
            ) {
                tracks.forEach { (name, volume) ->
                    Box(
                        modifier = Modifier
                            .height(58.dp)
                            .background(Color.LightGray,
                        RoundedCornerShape(8.dp))
                    )
                    {
                        TrackLabel(name, volume)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Box(
                    modifier = Modifier
                        .height(58.dp)
                        .background(Color.LightGray,
                            RoundedCornerShape(8.dp))
                )
                {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.width(60.dp)

                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(24.dp),
                            tint = Color.White
                        )
                    }
                }

            }
            Box(modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .background(Color.Red))


            Box(modifier = Modifier.weight(1f)) {
                AudioTracksDisplay(tracks.size)
            }



        }
    }
}

@Composable
fun TimeAxis() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(Color(0xFF34495E))
            .padding(start = 98.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        for (i in 0..7) {
            Row(

            ) {


                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(15.dp)
                        .background(Color.White)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "$i",
                    fontSize = 6.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun TrackLabel(name: String, volume: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width((50 * volume).dp)
                    .background(Color.White, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun AudioTracksDisplay(trackCount: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        repeat(trackCount) {

            Spacer(modifier = Modifier.height(1.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .background(Color(0xFF1E1E1E), RoundedCornerShape(8.dp))


            ) {

                Canvas(modifier = Modifier.padding(5.dp)) {
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    for (i in 0..size.width.toInt() step 20) {
                        drawLine(
                            color = Color.White,
                            start = androidx.compose.ui.geometry.Offset(i.toFloat(), 0f),
                            end = androidx.compose.ui.geometry.Offset(i.toFloat(), size.height),
                            strokeWidth = 1f,
                            pathEffect = pathEffect
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}
