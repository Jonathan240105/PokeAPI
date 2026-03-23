package com.example.pokenexusapplication.Views.Screens

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PantallaInicial() {
    AsyncImage(
        "https://i.pinimg.com/1200x/61/57/2e/61572e00849123bd306dbd59c541f841.jpg",
        "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(50.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .background(Color.White), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Poke-Nexus",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.testTag("tituloInicial")
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewPantallaInicial() {
    PantallaInicial()
}