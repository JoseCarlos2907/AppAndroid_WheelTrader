package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo

data class FiltroTodoUiState (
    var marca: String = "",
    var modelo: String = "",
    var anioMinimo: String = "",
    var anioMaximo: String = "",
    var precioMinimo: String = "",
    var precioMaximo: String = "",
    var ciudad: String = "",
    var provincia: String = ""
)