package com.example.pokenexusapplication.Views.Screens

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.pokenexusapplication.Views.ViewModels.ViewModelInicial

@Composable
fun PantallaInicial(myViewModel: ViewModelInicial, navegarAPrincipal: () -> Unit) {

    val model by myViewModel.model.collectAsState()

    //Una vez los datos se hayan cargado, el estado de la pantalla será un succes true, asi que una vez este en true se navegará automáticamente
    LaunchedEffect(model.succes) {
        if (model.succes) {
            navegarAPrincipal()
            myViewModel.resetearEstadoInicial()
        }
    }
    Box(Modifier.fillMaxSize()) {
        AsyncImage(
            "https://i.pinimg.com/1200x/61/57/2e/61572e00849123bd306dbd59c541f841.jpg",
            "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .padding(horizontal = 50.dp)
                .background(Color.White), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Poke-Nexus",
                textAlign = TextAlign.Center,
                fontSize = 45.sp,
                color = Color.Black,
                modifier = Modifier.testTag("tituloInicial")
            )
        }
        Row(
            Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .background(Color.Black)
                .padding(bottom = 30.dp, end = 30.dp)
        ) {
            Text(
                "Cargando...",
                color = Color.White,
                fontSize = 35.sp,
                modifier = Modifier.testTag("textoCarga")
            )

        }
    }
}