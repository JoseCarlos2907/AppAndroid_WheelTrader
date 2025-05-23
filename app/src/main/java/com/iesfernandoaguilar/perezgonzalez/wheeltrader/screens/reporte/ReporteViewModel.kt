package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.reporte

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReporteViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(ReporteUiState())
    val uiState: StateFlow<ReporteUiState> = _uiState.asStateFlow()

    fun onMotivoChange(motivo: String){
        _uiState.value = _uiState.value.copy(motivo = motivo)
    }

    fun onExplicacionChange(explicacion: String){
        _uiState.value = _uiState.value.copy(explicacion = explicacion)
    }
}