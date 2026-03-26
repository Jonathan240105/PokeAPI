package com.example.pokenexusapplication.Views.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.pokenexusapplication.Views.ViewModels.ViewModelDetalles

@Composable
fun PantallaDetallada(myViewModel: ViewModelDetalles) {
    LaunchedEffect(Unit) {
        myViewModel.listarEvoluciones(1)
    }

}