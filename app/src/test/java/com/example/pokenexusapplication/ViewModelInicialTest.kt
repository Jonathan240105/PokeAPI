package com.example.pokenexusapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokenexusapplication.Views.ViewModels.ViewModelInicial
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ViewModelInicialTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcher = Dispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN user data is loaded THEN the succes state is changed`() = runTest {

        //Como el viewmodel es lanzado, espera la carga simulada, así que avanzamos el
        //tiempo manualmente para evitar que falle
        val viewModel = ViewModelInicial()

        advanceTimeBy(5001)
        assertTrue(viewModel.model.value.succes)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN state is reseted THEN succes is false and isLoading true`() = runTest {
        val viewModel = ViewModelInicial()

        advanceTimeBy(5001)

        viewModel.resetearEstadoInicial()
        assertTrue(viewModel.model.value.isLoading)
        assertFalse(viewModel.model.value.succes)
    }
}
