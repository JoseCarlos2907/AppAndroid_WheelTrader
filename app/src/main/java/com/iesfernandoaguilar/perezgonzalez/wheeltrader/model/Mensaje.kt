package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

class Mensaje {

    private var tipo: String = ""
    private var parametros: List<String> = ArrayList()

    fun getTipo(): String {
        return tipo
    }

    fun getParams(): List<String> {
        return parametros
    }

    fun setTipo(tipo: String) {
        this.tipo = tipo
    }

    fun addParam(parametro: String) {
        parametros += parametro
    }

    fun clearParams() {
        parametros = ArrayList()
    }
}