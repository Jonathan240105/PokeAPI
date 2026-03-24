package com.example.pokenexusapplication.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokenexusapplication.Domain.ModelInicial
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelInicial : ViewModel() {

    private val _model = MutableStateFlow(ModelInicial())
    val model = _model.asStateFlow()

    init {
        simularCargaDeDatos()
    }

    fun simularCargaDeDatos() {
        viewModelScope.launch {
            _model.update { it.copy(isLoading = true,succes = false) }
            delay(5000)
            _model.update { it.copy(isLoading = false,succes = true) }
        }
    }

    fun resetearEstadoInicial() {
        viewModelScope.launch {
            _model.update {
                it.copy(isLoading = true, succes = false)
            }
        }
    }
}