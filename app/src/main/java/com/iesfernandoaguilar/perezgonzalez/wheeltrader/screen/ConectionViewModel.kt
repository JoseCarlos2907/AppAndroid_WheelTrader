package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import java.net.Socket

class ConectionViewModel: ViewModel() {
    private var _uiState = MutableStateFlow(ConectionUiState())
    val uiState: StateFlow<ConectionUiState> = _uiState.asStateFlow()

    fun conectar(address: String, port: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val socket = Socket(address, port)
            _uiState.value = _uiState.value.copy(
                socket = socket,
                input = socket.getInputStream(),
                output = socket.getOutputStream()
            )
        }

    }

    fun cerrarConexion(){
        _uiState.value = _uiState.value.copy(
            socket = null,
            input = null,
            output = null
        )
    }

}