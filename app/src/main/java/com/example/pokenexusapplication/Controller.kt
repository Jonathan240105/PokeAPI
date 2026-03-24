package com.example.pokenexusapplication

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokenexusapplication.Views.Screens.PantallaCarga
import com.example.pokenexusapplication.Views.Screens.PantallaDetallada
import com.example.pokenexusapplication.Views.Screens.PantallaEvoluciones
import com.example.pokenexusapplication.Views.Screens.PantallaInicial
import com.example.pokenexusapplication.Views.Screens.PantallaMovimientos
import com.example.pokenexusapplication.Views.Screens.PantallaPrincipal
import com.example.pokenexusapplication.Views.ViewModels.ViewModelInicial

@Composable
fun Controller() {
    val navController = rememberNavController()

    val viewModelInicial: ViewModelInicial = viewModel()
    NavHost(navController, Rutas.PANTALLA_INICIAL) {

        composable(Rutas.PANTALLA_INICIAL) {
            PantallaInicial(viewModelInicial, { navController.navigate(Rutas.PANTALLA_PRINCIPAL) })
        }
        composable(Rutas.PANTALLA_CARGA) {
            PantallaCarga()
        }
        composable(Rutas.PANTALLA_PRINCIPAL) {
            PantallaPrincipal()
        }
        composable(Rutas.PANTALLA_DETALLADA) {
            PantallaDetallada()
        }
        composable(Rutas.PANTALLA_EVOLUCIONES) {
            PantallaEvoluciones()
        }
        composable(Rutas.PANTALLA_MOVIMIENTOS) {
            PantallaMovimientos()
        }
    }
}