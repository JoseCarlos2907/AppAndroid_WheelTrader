package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro

data class FiltroCoche(
    var marca: String? = null,
    var modelo: String? = null,
    var anioMinimo: Int? = null,
    var anioMaximo: Int? = null,
    var kmMinimo: Int? = null,
    var kmMaximo: Int? = null,
    var tipoCombustible: String? = null,
    var cvMinimo: Int? = null,
    var cvMaximo: Int? = null,
    var cantMarchas: Int? = null,
    @field:JsonProperty("nPuertas") @get:JsonIgnore var nPuertas: Int? = null,
    var provincia: String? = null,
    var ciudad: String? = null,
    var transmision: String? = null,
    override var pagina: Int = 0,
    override var cantidadPorPagina: Int = 2,
    override var tipoFiltro: String = "Coche"
): IFiltro
