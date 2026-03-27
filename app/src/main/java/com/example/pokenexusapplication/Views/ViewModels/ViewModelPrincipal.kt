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
    //variables para el paginado de pokemons
    private var salto = 0
    private val limite = 20

    //Al lanzar la pantalla principal, se lista todos los pokemons compactos,
    // y se carga la primera pagina que es la que ve el usuario
    init {
        listarPokemons()
        cargarSiguientePagina()
    }

    //Función que carga la pagina de pokemons. Solo es lanzada cuando el usuario llega al final de la lista
    //de pokemons que tiene guardada. Cuando se llama, llama al repositorio para que devuelva todos los
    //detalles de cada pokemon de la pagina, y añade los pokemons a la lista para que la pantalla tenga mas scroll
    fun cargarSiguientePagina() {

        if (_model.value.isLoading) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _model.update { it.copy(isLoading = true) }

            try {
                val nuevosPokemons = repository.getPaginaPokemon(limite, salto)

                _model.update {
                    it.copy(
                        listaPokemons = it.listaPokemons + nuevosPokemons,
                        isLoading = false,
                        succes = true
                    )
                }
                salto += limite
            }catch (e: Exception){
                println(e.message)
            }
        }
    }

    //Función que llama al repositorio para que liste todos los pokemons compactos
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