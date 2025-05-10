package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamion

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
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCamion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheViewModel

@Composable
fun FiltroCamion(
    buscarOnClick: () -> Unit,
    filtrosViewModel: FiltrosViewModel,
    filtroCamionViewModel: FiltroCamionViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    val filtroCamionUiState by filtroCamionViewModel.uiState.collectAsState()

    var tiposCombustible = listOf("Diesel", "Electrico", "GNL", "Hidrogeno")
    var tiposCombustiblesDesplegado by remember { mutableStateOf(false) }

    var tamanioCombustible by remember { mutableStateOf(Size.Zero) }

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
                        value = filtroCamionUiState.marca,
                        onValueChange = { filtroCamionViewModel.cambiarMarca_Camion(it) },
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
                        value = filtroCamionUiState.modelo,
                        onValueChange = { filtroCamionViewModel.cambiarModelo_Camion(it) },
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
                        value = filtroCamionUiState.anioMinimo,
                        onValueChange = { filtroCamionViewModel.cambiarAnioMin_Camion(it) },
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
                        value = filtroCamionUiState.anioMaximo,
                        onValueChange = { filtroCamionViewModel.cambiarAnioMax_Camion(it) },
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
                        value = filtroCamionUiState.cvMinimo,
                        onValueChange = { filtroCamionViewModel.cambiarCvMinimo_Camion(it) },
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
                        value = filtroCamionUiState.cvMaximo,
                        onValueChange = { filtroCamionViewModel.cambiarCvMaximo_Camion(it) },
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
                        value = filtroCamionUiState.ciudad,
                        onValueChange = { filtroCamionViewModel.cambiarCiudad_Camion(it) },
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
                        value = filtroCamionUiState.provincia,
                        onValueChange = { filtroCamionViewModel.cambiarProvincia_Camion(it) },
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
                        value = filtroCamionUiState.kmMinimo,
                        onValueChange = { filtroCamionViewModel.cambiarKmMinimo_Camion(it) },
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
                        value = filtroCamionUiState.kmMaximo,
                        onValueChange = { filtroCamionViewModel.cambiarKmMaximo_Camion(it) },
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
                        value = filtroCamionUiState.tipoCombustible,
                        onValueChange = { filtroCamionViewModel.cambiarTipoComb_Camion(it) },
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
                                    filtroCamionViewModel.cambiarTipoComb_Camion(tipo)
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
                    //Vacío por tema de diseño
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
                    var filtroCamion = FiltroCamion()

                    if (!filtroCamionUiState.marca.isEmpty()) filtroCamion.marca = filtroCamionUiState.marca
                    if (!filtroCamionUiState.modelo.isEmpty()) filtroCamion.modelo = filtroCamionUiState.modelo
                    if (!filtroCamionUiState.anioMinimo.isEmpty()) filtroCamion.anioMinimo = filtroCamionUiState.anioMinimo.toInt() else filtroCamion.anioMinimo = 1950
                    if (!filtroCamionUiState.anioMaximo.isEmpty()) filtroCamion.anioMaximo = filtroCamionUiState.anioMaximo.toInt() else filtroCamion.anioMaximo = 2025
                    if (!filtroCamionUiState.cvMinimo.isEmpty()) filtroCamion.cvMinimo = filtroCamionUiState.cvMinimo.toInt() else filtroCamion.cvMinimo = 100
                    if (!filtroCamionUiState.cvMaximo.isEmpty()) filtroCamion.cvMaximo = filtroCamionUiState.cvMaximo.toInt() else filtroCamion.cvMaximo = 800
                    if (!filtroCamionUiState.ciudad.isEmpty()) filtroCamion.ciudad = filtroCamionUiState.ciudad
                    if (!filtroCamionUiState.provincia.isEmpty()) filtroCamion.provincia = filtroCamionUiState.provincia
                    if (!filtroCamionUiState.kmMinimo.isEmpty()) filtroCamion.kmMinimo = filtroCamionUiState.kmMinimo.toInt() else filtroCamion.kmMinimo = 0
                    if (!filtroCamionUiState.kmMaximo.isEmpty()) filtroCamion.kmMaximo = filtroCamionUiState.kmMaximo.toInt() else filtroCamion.kmMaximo = 2000000
                    if (!filtroCamionUiState.tipoCombustible.isEmpty()) filtroCamion.tipoCombustible = filtroCamionUiState.tipoCombustible


                    filtrosViewModel.asignarFiltro(filtroCamion)

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