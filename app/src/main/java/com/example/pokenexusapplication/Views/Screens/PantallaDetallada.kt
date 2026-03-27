package com.example.pokenexusapplication.Views.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokenexusapplication.Domain.EvolucionModel
import com.example.pokenexusapplication.Domain.Pokemon
import com.example.pokenexusapplication.Views.ViewModels.ViewModelDetalles
import com.example.pokenexusapplication.ui.theme.PokedexRojo
import com.example.pokenexusapplication.ui.theme.getColorPokemon
import com.example.pokenexusapplication.ui.theme.silkFamily

@Composable
fun PantallaDetallada(
    myViewModel: ViewModelDetalles,
    nombrePokemon: String?,
    idEspecie: Int?,
    onVolver: () -> Unit
) {
    val model by myViewModel.model.collectAsState()
    LaunchedEffect(Unit) {
        myViewModel.getPokemonPorNombre(nombrePokemon ?: "")
        myViewModel.getEspecieYEvoluciones(idEspecie ?: 2)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(getColorPokemon(model.pokemonActual.tipos?.get(0)?.tipo?.nombre ?: ""))
            .navigationBarsPadding()
    ) {
        tituloDetalles(model.pokemonActual.nombre, onVolver)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            AsyncImage(
                model.pokemonActual.fotoUrl?.other?.officialArtwork?.frontDefault,
                "",
                modifier = Modifier.size(200.dp)
            )
        }
        cardDetalles(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            model.pokemonActual,
            model.listaEvoluciones,
            { myViewModel.getPokemonPorNombre(it) },
            model.pokemonActual.listaNombresMovimientos
        )
    }
}

@Composable
fun tituloDetalles(nombrePokemon: String, onVolver: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(PokedexRojo)
            .padding(25.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2B2B)),
        shape = RoundedCornerShape(12.dp),
    ) {

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            IconButton(
                onVolver,
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White),
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.ArrowBack, "")
            }
            Text(
                nombrePokemon,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .testTag("tituloDetallada"),
                fontSize = 25.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF51ADFB),
                letterSpacing = 5.sp,
                textAlign = TextAlign.Center,
                fontFamily = silkFamily
            )
        }
    }
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(5.dp)
            .background(Color.Black)
    )
}

@Composable
fun cardDetalles(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    listaEvoluciones: List<EvolucionModel>,
    onBuscarPokemon: (String) -> Unit,
    listaMovimientos: List<String>
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.62f))
    ) {
        tabRowDetalles(pokemon, listaEvoluciones, onBuscarPokemon, listaMovimientos)
    }
}

@Composable
fun tabRowDetalles(
    pokemon: Pokemon,
    listaEvoluciones: List<EvolucionModel>,
    onBuscarPokemon: (String) -> Unit,
    listaMovimientos: List<String>
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Información", "Evoluciones", "Movimientos")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontFamily = silkFamily
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (selectedTabIndex) {
                0 -> estructuraDetalles(pokemon)
                1 -> EvolucionesTab(listaEvoluciones, onBuscarPokemon)
                2 -> MovimientosTab(listaMovimientos)
            }
        }
    }
}

@Composable
fun estructuraDetalles(pokemon: Pokemon) {

    val estadoScroll = rememberScrollState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(estadoScroll)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Text(
                "Descripción",
                fontWeight = FontWeight.Bold,
                fontFamily = silkFamily
            )
            Text(
                text = pokemon.descripcion,
                fontSize = 15.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify,
                fontFamily = silkFamily
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(15.dp))
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${pokemon.altura.toFloat() / 10} m",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = silkFamily
                )
                Text(
                    "Altura",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    fontFamily = silkFamily
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${pokemon.peso.toFloat() / 10} kg",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = silkFamily
                )
                Text(
                    "Peso",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    fontFamily = silkFamily
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Tipos:",
            fontWeight = FontWeight.Bold,
            fontFamily = silkFamily
        )
        Row(Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            pokemon.tipos?.forEach { tipo ->
                Text(
                    text = tipo.tipo.nombre.uppercase(),
                    modifier = Modifier
                        .background(getColorPokemon(tipo.tipo.nombre), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = silkFamily
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Estadísticas:",
            fontWeight = FontWeight.Bold,
            fontFamily = silkFamily
        )
        Spacer(Modifier.height(8.dp))

        val nombresStats = listOf("Vida", "Ataque", "Defensa", "Ataque Especial")
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            pokemon.estadisticas?.take(4)?.forEachIndexed { index, stat ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        nombresStats[index],
                        color = Color.Gray,
                        fontFamily = silkFamily
                    )
                    Text(
                        text = stat.valor.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontFamily = silkFamily
                    )
                }
                Spacer(
                    Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Composable
fun EvolucionesTab(evoluciones: List<EvolucionModel>, onPokemonClick: (String) -> Unit) {
    if (evoluciones.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Este Pokémon no tiene evoluciones conocidas.",
                fontFamily = silkFamily
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            evoluciones.forEach { evo ->
                ItemEvolucion(evo, onPokemonClick)
            }
        }
    }
}

@Composable
fun ItemEvolucion(evo: EvolucionModel, onNombreClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNombreClick(evo.nombrePokemonEvolucion) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    evo.nombrePokemonBase.replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.Bold,
                    fontFamily = silkFamily
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "->",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontFamily = silkFamily
                )
                if (evo.nivelMinimo != 0) {
                    Text(
                        "Nivel ${evo.nivelMinimo}",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Light,
                        fontFamily = silkFamily
                    )
                } else {
                    Text(
                        "Evento especial",
                        fontSize = 10.sp,
                        color = Color.Blue,
                        fontFamily = silkFamily
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    evo.nombrePokemonEvolucion.replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.Bold,
                    fontFamily = silkFamily
                )
            }
        }
    }
}

@Composable
fun MovimientosTab(movimientos: List<String>) {
    val scrollState = rememberScrollState()

    if (movimientos.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "No hay movimientos registrados",
                fontFamily = silkFamily
            )
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(15.dp)
                .background(Color.White.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                "Lista de ataques",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                fontFamily = silkFamily
            )

            movimientos.forEach { nombre ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(8.dp)
                                .background(Color.Gray, RoundedCornerShape(50.dp))
                        )
                        Spacer(Modifier.size(12.dp))
                        Text(
                            nombre.replace("-", " ").uppercase(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontFamily = silkFamily
                        )
                    }
                    Spacer(
                        Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray.copy(alpha = 0.5f))
                    )
                }
            }
        }
    }
}