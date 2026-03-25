package com.example.pokenexusapplication.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.EntityToCompacto
import com.example.pokenexusapplication.Data.Repository.Repository
import com.example.pokenexusapplication.Domain.ModelPrincipal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelPrincipal @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _model = MutableStateFlow(ModelPrincipal())

    val model = _model.asStateFlow()
    private var salto = 0
    private val limite = 20

    init {
        listarPokemons()
        cargarSiguientePagina()
    }

    fun cargarSiguientePagina() {

        if (_model.value.isLoading) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _model.update { it.copy(isLoading = true) }

            val nuevosPokemons = repository.traerPaginaPokemon(limite, salto)

            _model.update {
                it.copy(
                    listaPokemons = it.listaPokemons + nuevosPokemons,
                    isLoading = false,
                    succes = true
                )
            }
            salto += limite
        }
    }

    fun listarPokemons() {
        viewModelScope.launch {
            repository.listarTodosPokemonsCompactos().collect { pokemons ->
                println(pokemons)
                _model.update {
                    it.copy(
                        listaPokemonsCompactos = pokemons.map {
                            EntityToCompacto(it)
                        },
                        succes = true,
                        isLoading = false
                    )
                }
            }
        }
    }
}