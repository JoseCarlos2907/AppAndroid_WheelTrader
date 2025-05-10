package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel

@Composable
fun FiltroCoche(
    buscarOnClick: () -> Unit,
    filtrosViewModel: FiltrosViewModel,
    filtroCocheViewModel: FiltroCocheViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val filtroCocheUiState by filtroCocheViewModel.uiState.collectAsState()

    var tiposCombustible = listOf("Gasolina", "Electrico", "Diesel", "Hibrido")
    var tiposCombustiblesDesplegado by remember { mutableStateOf(false) }

    var transmision = listOf("Manual", "Automatica")
    var transmisionDesplegado by remember { mutableStateOf(false) }

    var tamanioCombustible by remember { mutableStateOf(Size.Zero) }
    var tamanioTransmision by remember { mutableStateOf(Size.Zero) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Black, Color(0xFF525151)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.15F)
        ) {
            Text(
                text = "Filtro de coches",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier.weight(0.7F)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1F)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.marca,
                        onValueChange = { filtroCocheViewModel.cambiarMarca_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Marca",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.modelo,
                        onValueChange = { filtroCocheViewModel.cambiarModelo_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Modelo",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1F)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.anioMinimo,
                        onValueChange = { filtroCocheViewModel.cambiarAnioMin_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Año Mínimo",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.anioMaximo,
                        onValueChange = { filtroCocheViewModel.cambiarAnioMax_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Año Máximo",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1F)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.cvMinimo,
                        onValueChange = { filtroCocheViewModel.cambiarCvMinimo_Coche(it) },
                        placeholder = {
                            Text(
                                text = "CV Mínimo",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.cvMaximo,
                        onValueChange = { filtroCocheViewModel.cambiarCvMaximo_Coche(it) },
                        placeholder = {
                            Text(
                                text = "CV Máximo",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1F)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.ciudad,
                        onValueChange = { filtroCocheViewModel.cambiarCiudad_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Ciudad",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.provincia,
                        onValueChange = { filtroCocheViewModel.cambiarProvincia_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Provincia",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1F)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.kmMinimo,
                        onValueChange = { filtroCocheViewModel.cambiarKmMinimo_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Km mínimo",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.kmMaximo,
                        onValueChange = { filtroCocheViewModel.cambiarKmMaximo_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Km máximo",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1F)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.cantMarchas,
                        onValueChange = { filtroCocheViewModel.cambiarCantMarchas_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Cant. de Marchas",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.nPuertas,
                        onValueChange = { filtroCocheViewModel.cambiarNPuertas_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Nº de Puertas",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1F)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.tipoCombustible,
                        onValueChange = { filtroCocheViewModel.cambiarTipoComb_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Combustible",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        readOnly = true,
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp),
                        trailingIcon = {
                            Icon(
                                imageVector = if (tiposCombustiblesDesplegado) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    tiposCombustiblesDesplegado = !tiposCombustiblesDesplegado
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coords ->
                                tamanioCombustible = coords.size.toSize()
                            }
                    )

                    DropdownMenu(
                        expanded = tiposCombustiblesDesplegado,
                        onDismissRequest = { tiposCombustiblesDesplegado = false },
                        modifier = Modifier.width(
                            with(LocalDensity.current) { tamanioCombustible.width.toDp() }
                        )
                    ) {
                        tiposCombustible.forEach { tipo ->
                            DropdownMenuItem(
                                onClick = {
                                    filtroCocheViewModel.cambiarTipoComb_Coche(tipo)
                                    tiposCombustiblesDesplegado = false
                                },
                                text = { Text(text = tipo) }
                            )
                        }
                    }
                }



                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = filtroCocheUiState.transmision,
                        onValueChange = { filtroCocheViewModel.cambiarTransmision_Coche(it) },
                        placeholder = {
                            Text(
                                text = "Transmisión",
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        readOnly = true,
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Color(0x00FF1c1c1c),
                            focusedTextColor = Color(0x00FF1c1c1c),
                            unfocusedTextColor = Color(0x00FF1c1c1c),
                            focusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedLabelColor = Color(0x00FF1c1c1c),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(22.dp),
                        trailingIcon = {
                            Icon(
                                imageVector = if (transmisionDesplegado) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    transmisionDesplegado = !transmisionDesplegado
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coords ->
                                tamanioTransmision = coords.size.toSize()
                            }
                    )

                    DropdownMenu(
                        expanded = transmisionDesplegado,
                        onDismissRequest = { transmisionDesplegado = false },
                        modifier = Modifier.width(
                            with(LocalDensity.current) { tamanioTransmision.width.toDp() }
                        )
                    ) {
                        transmision.forEach { tipo ->
                            DropdownMenuItem(
                                onClick = {
                                    filtroCocheViewModel.cambiarTransmision_Coche(tipo)
                                    transmisionDesplegado = false
                                },
                                text = { Text(text = tipo) }
                            )
                        }
                    }
                }
            }
        }



        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.15F)
        ) {
            Button(
                onClick = {
                    var filtroCoche = FiltroCoche()

                    if (!filtroCocheUiState.marca.isEmpty()) filtroCoche.marca = filtroCocheUiState.marca
                    if (!filtroCocheUiState.modelo.isEmpty()) filtroCoche.modelo = filtroCocheUiState.modelo
                    if (!filtroCocheUiState.anioMinimo.isEmpty()) filtroCoche.anioMinimo = filtroCocheUiState.anioMinimo.toInt() else filtroCoche.anioMinimo = 1950
                    if (!filtroCocheUiState.anioMaximo.isEmpty()) filtroCoche.anioMaximo = filtroCocheUiState.anioMaximo.toInt() else filtroCoche.anioMaximo = 2025
                    if (!filtroCocheUiState.cvMinimo.isEmpty()) filtroCoche.cvMinimo = filtroCocheUiState.cvMinimo.toInt() else filtroCoche.cvMinimo = 40
                    if (!filtroCocheUiState.cvMaximo.isEmpty()) filtroCoche.cvMaximo = filtroCocheUiState.cvMaximo.toInt() else filtroCoche.cvMaximo = 1500
                    if (!filtroCocheUiState.ciudad.isEmpty()) filtroCoche.ciudad = filtroCocheUiState.ciudad
                    if (!filtroCocheUiState.provincia.isEmpty()) filtroCoche.provincia = filtroCocheUiState.provincia
                    if (!filtroCocheUiState.kmMinimo.isEmpty()) filtroCoche.kmMinimo = filtroCocheUiState.kmMinimo.toInt() else filtroCoche.kmMinimo = 0
                    if (!filtroCocheUiState.kmMaximo.isEmpty()) filtroCoche.kmMaximo = filtroCocheUiState.kmMaximo.toInt() else filtroCoche.kmMaximo = 2000000
                    if (!filtroCocheUiState.cantMarchas.isEmpty()) filtroCoche.cantMarchas = filtroCocheUiState.cantMarchas.toInt() else filtroCoche.cantMarchas = 0
                    if (!filtroCocheUiState.nPuertas.isEmpty()) filtroCoche.nPuertas = filtroCocheUiState.nPuertas.toInt() else filtroCoche.nPuertas = 0
                    if (!filtroCocheUiState.tipoCombustible.isEmpty()) filtroCoche.tipoCombustible = filtroCocheUiState.tipoCombustible
                    if (!filtroCocheUiState.transmision.isEmpty()) filtroCoche.transmision = filtroCocheUiState.transmision


                    filtrosViewModel.asignarFiltro(filtroCoche)

                    buscarOnClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Buscar",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}