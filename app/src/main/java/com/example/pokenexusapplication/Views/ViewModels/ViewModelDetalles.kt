package com.example.pokenexusapplication.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokenexusapplication.Data.LocalData.PokemonData.EntityToPokemon
import com.example.pokenexusapplication.Data.Repository.Repository
import com.example.pokenexusapplication.Domain.ModelDetalles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDetalles @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _model = MutableStateFlow(ModelDetalles())
    val model = _model.asStateFlow()

    fun getPokemonPorNombre(nombrePokemon: String) {
        viewModelScope.launch {
            repository.getPokemonPorNombre(nombrePokemon).collect { pokemon ->
                _model.update { it.copy(pokemonActual = EntityToPokemon(pokemon)) }
            }
        }
    }

    fun getEspecieYEvoluciones(idEspecie: Int) {
        viewModelScope.launch {
            repository.getEspecie(idEspecie).collect { especie ->
                val idEvoluciones = especie.idEvoluciones
                repository.getEvolucion(idEvoluciones).collect { evoluciones ->
                    _model.update { it.copy(listaEvoluciones = evoluciones) }
                }
            }
        }
    }
}