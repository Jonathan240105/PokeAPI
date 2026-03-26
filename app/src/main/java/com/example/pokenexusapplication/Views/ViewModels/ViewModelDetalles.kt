package com.example.pokenexusapplication.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokenexusapplication.Data.Repository.Repository
import com.example.pokenexusapplication.Domain.ModelDetalles
import com.example.pokenexusapplication.Domain.ModelPrincipal
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

    fun listarEvoluciones(idPokemon: Int) {
        viewModelScope.launch {
            repository.getEvolucion(idPokemon).collect {
                _model.update { it.copy(listaEvoluciones = it.listaEvoluciones) }
            }
        }
    }
}