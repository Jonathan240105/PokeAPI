package com.example.pokenexusapplication.Views.Screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokenexusapplication.Domain.Pokemon
import com.example.pokenexusapplication.Views.ViewModels.ViewModelPrincipal
import com.example.pokenexusapplication.ui.theme.FondoPantalla
import com.example.pokenexusapplication.ui.theme.GrisBorde
import com.example.pokenexusapplication.ui.theme.PokedexRojo
import com.example.pokenexusapplication.ui.theme.getColorPokemon


@Composable
fun PantallaPrincipal(myViewModel: ViewModelPrincipal) {

    val model by myViewModel.model.collectAsState()
    val estadoLista = rememberLazyGridState()

    LaunchedEffect(estadoLista.canScrollForward) {
        if (!estadoLista.canScrollForward && !model.isLoading) {
            myViewModel.cargarSiguientePagina()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        tituloListado()
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(GrisBorde)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            state = estadoLista
        ) {
            items(model.listaPokemons) {
                EstructuraPokemon(it)
            }
            if (model.listaPokemons.size < 1350) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        "Buscando más Pokémon...",
                        Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun EstructuraPokemon(pokemon: Pokemon) {

    val tipoPrincipal = pokemon.tipos?.getOrNull(0)?.tipo?.nombre ?: "Desconocido"
    val colorPokemon = getColorPokemon(tipoPrincipal)
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            colorPokemon.copy(alpha = 0.8f),
//                            Color.White.copy(alpha = 0.9f)
//                        )
//                    ),
                    colorPokemon,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                pokemon.nombre.replaceFirstChar { it.uppercase() },
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(5.dp))
            Card(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
            ) {
                AsyncImage(
                    model = pokemon.fotoUrl?.frontDefault,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
            Spacer(Modifier.height(12.dp))

            Row(
                Modifier
                    .background(Color(0xFF2B2B2B), shape = CircleShape)
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .clip(CircleShape),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    tipoPrincipal.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = colorPokemon,
                    letterSpacing = 1.5.sp
                )
                Spacer(Modifier.width(15.dp))
                Text(
                    "HP ${pokemon.estadísticas?.getOrNull(0)?.valor?.toString() ?: "0"}",
                    fontSize = 14.sp,
                    fontWeight = ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun tituloListado() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(PokedexRojo)
            .padding(25.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2B2B)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            "POKÉDEX",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically),
            fontSize = 25.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF51ADFB),
            letterSpacing = 5.sp,
            textAlign = TextAlign.Center
        )
    }
}