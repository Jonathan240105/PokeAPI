package com.example.pokenexusapplication

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokenexusapplication.Views.Screens.PantallaDetallada
import com.example.pokenexusapplication.Views.Screens.PantallaInicial
import com.example.pokenexusapplication.Views.Screens.PantallaPrincipal
import com.example.pokenexusapplication.Views.ViewModels.ViewModelDetalles
import com.example.pokenexusapplication.Views.ViewModels.ViewModelPrincipal

@Composable
fun Controller() {
    val navController = rememberNavController()

    val viewModelPrincipal: ViewModelPrincipal = hiltViewModel()
    val viewModelDetalles: ViewModelDetalles = hiltViewModel()
    NavHost(navController, Rutas.PANTALLA_INICIAL) {

        composable(Rutas.PANTALLA_INICIAL) {
            PantallaInicial(
                viewModelPrincipal,
                { navController.navigate(Rutas.PANTALLA_PRINCIPAL) })
        }
        composable(Rutas.PANTALLA_PRINCIPAL) {
            PantallaPrincipal(
                viewModelPrincipal,
                { nombrePokemon, idEspecie -> navController.navigate("pantallaDetallada/$nombrePokemon/$idEspecie") })
        }
        composable(Rutas.PANTALLA_DETALLADA) {
            val nombrePokemon = it.arguments?.getString("nombrePokemon")
            val idEspecie = it.arguments?.getString("idEspecie")
            PantallaDetallada(viewModelDetalles, nombrePokemon, idEspecie?.toInt(),{navController.navigate(
                Rutas.PANTALLA_PRINCIPAL)})
        }
    }
}