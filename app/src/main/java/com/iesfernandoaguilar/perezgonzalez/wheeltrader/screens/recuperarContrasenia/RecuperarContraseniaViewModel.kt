package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecuperarContraseniaViewModel(
    private val conectionViewModel: ConectionViewModel
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecuperarContraseniaUiState())
    val uiState: StateFlow<RecuperarContraseniaUiState> = _uiState.asStateFlow()

    fun onCorreoChange(correo: String){
        _uiState.value = _uiState.value.copy(correo = correo)
    }

    fun onCodigoChange(codigo: String){
        _uiState.value = _uiState.value.copy(codigo = codigo)
    }

    fun onContraseniaChange(contrasenia: String){
        _uiState.value = _uiState.value.copy(contrasenia = contrasenia)
    }

    fun onRepetirContraseniaChange(repetirContrasenia: String){
        _uiState.value = _uiState.value.copy(repetirContrasenia = repetirContrasenia)
    }
}

class RecuperarContraseniaViewModelFactory(
    private val conectionViewModel: ConectionViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecuperarContraseniaViewModel::class.java)) {
            return RecuperarContraseniaViewModel(conectionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}