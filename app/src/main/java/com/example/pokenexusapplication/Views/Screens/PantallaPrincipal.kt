package com.example.pokenexusapplication.Views.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokenexusapplication.Views.ViewModels.ViewModelPrincipal

@Composable
fun PantallaPrincipal(myViewModel: ViewModelPrincipal) {

    val model by myViewModel.model.collectAsState()
    val estadoLista = rememberLazyListState()

    LaunchedEffect(estadoLista.canScrollForward) {
        if (!estadoLista.canScrollForward && !model.isLoading) {
            myViewModel.cargarSiguientePagina()
        }
    }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth()) {
            Text("Lista de pokemons", fontSize = 30.sp)
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            state = estadoLista,

            ) {
            items(model.listaPokemons) {
                Text(it.nombre)
                Text(it.altura.toString())
            }
            if (model.listaPokemons.size < 1350) { // O el tamaño de listaPokemosCompactos
                item {
                    Text(
                        "Buscando más Pokémon...",
                        Modifier.padding(20.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}