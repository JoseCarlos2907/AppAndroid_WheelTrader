package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMoto

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroMoto
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltroMoto(
    buscarOnClick: () -> Unit,
    filtrosViewModel: FiltrosViewModel,
    filtroMotoViewModel: FiltroMotoViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    val filtroMotoUiState by filtroMotoViewModel.uiState.collectAsState()

    var tiposCombustible = listOf("Gasolina", "Electrico", "Diesel", "Hibrido")
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
                text = stringResource(R.string.texto_filtro_de_motos),
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
                        value = filtroMotoUiState.marca,
                        onValueChange = { filtroMotoViewModel.cambiarMarca_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_marca),
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
                        value = filtroMotoUiState.modelo,
                        onValueChange = { filtroMotoViewModel.cambiarModelo_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_modelo),
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
                        value = filtroMotoUiState.anioMinimo,
                        onValueChange = { filtroMotoViewModel.cambiarAnioMin_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_ano_minimo),
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
                        value = filtroMotoUiState.anioMaximo,
                        onValueChange = { filtroMotoViewModel.cambiarAnioMax_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_ano_maximo),
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
                        value = filtroMotoUiState.cvMinimo,
                        onValueChange = { filtroMotoViewModel.cambiarCvMinimo_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_cv_minimo),
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
                        value = filtroMotoUiState.cvMaximo,
                        onValueChange = { filtroMotoViewModel.cambiarCvMaximo_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_cv_maximo),
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
                        value = filtroMotoUiState.ciudad,
                        onValueChange = { filtroMotoViewModel.cambiarCiudad_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_ciudad),
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
                        value = filtroMotoUiState.provincia,
                        onValueChange = { filtroMotoViewModel.cambiarProvincia_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_provincia),
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
                        value = filtroMotoUiState.kmMinimo,
                        onValueChange = { filtroMotoViewModel.cambiarKmMinimo_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_km_minimo),
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
                        value = filtroMotoUiState.kmMaximo,
                        onValueChange = { filtroMotoViewModel.cambiarKmMaximo_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_km_maximo),
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
                        value = filtroMotoUiState.cantMarchas,
                        onValueChange = { filtroMotoViewModel.cambiarCantMarchas_Moto(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_cant_de_marchas),
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
                    ExposedDropdownMenuBox(
                        expanded = tiposCombustiblesDesplegado,
                        onExpandedChange = { tiposCombustiblesDesplegado = !tiposCombustiblesDesplegado },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = filtroMotoUiState.tipoCombustible,
                            onValueChange = { },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.texto_combustible),
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
                                    contentDescription = stringResource(R.string.desc_icono_flecha_arriba_abajo),
                                )
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .onGloballyPositioned { coords ->
                                    tamanioCombustible = coords.size.toSize()
                                }
                        )

                        ExposedDropdownMenu (
                            expanded = tiposCombustiblesDesplegado,
                            onDismissRequest = { tiposCombustiblesDesplegado = false },
                            modifier = Modifier.width(
                                with(LocalDensity.current) { tamanioCombustible.width.toDp() }
                            )
                        ) {

                            tiposCombustible.forEach { tipo ->
                                DropdownMenuItem(
                                    onClick = {
                                        filtroMotoViewModel.cambiarTipoComb_Moto(tipo)
                                        tiposCombustiblesDesplegado = false
                                    },
                                    text = { Text(text = tipo) }
                                )
                            }
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
                    var filtroMoto = FiltroMoto()

                    if (!filtroMotoUiState.marca.isEmpty()) filtroMoto.marca = filtroMotoUiState.marca
                    if (!filtroMotoUiState.modelo.isEmpty()) filtroMoto.modelo = filtroMotoUiState.modelo
                    if (!filtroMotoUiState.anioMinimo.isEmpty()) filtroMoto.anioMinimo = filtroMotoUiState.anioMinimo.toInt() else filtroMoto.anioMinimo = 1950
                    if (!filtroMotoUiState.anioMaximo.isEmpty()) filtroMoto.anioMaximo = filtroMotoUiState.anioMaximo.toInt() else filtroMoto.anioMaximo = 2025
                    if (!filtroMotoUiState.cvMinimo.isEmpty()) filtroMoto.cvMinimo = filtroMotoUiState.cvMinimo.toInt() else filtroMoto.cvMinimo = 50
                    if (!filtroMotoUiState.cvMaximo.isEmpty()) filtroMoto.cvMaximo = filtroMotoUiState.cvMaximo.toInt() else filtroMoto.cvMaximo = 2000
                    if (!filtroMotoUiState.ciudad.isEmpty()) filtroMoto.ciudad = filtroMotoUiState.ciudad
                    if (!filtroMotoUiState.provincia.isEmpty()) filtroMoto.provincia = filtroMotoUiState.provincia
                    if (!filtroMotoUiState.kmMinimo.isEmpty()) filtroMoto.kmMinimo = filtroMotoUiState.kmMinimo.toInt() else filtroMoto.kmMinimo = 0
                    if (!filtroMotoUiState.kmMaximo.isEmpty()) filtroMoto.kmMaximo = filtroMotoUiState.kmMaximo.toInt() else filtroMoto.kmMaximo = 1000000
                    if (!filtroMotoUiState.cantMarchas.isEmpty()) filtroMoto.cantMarchas = filtroMotoUiState.cantMarchas.toInt() else filtroMoto.cantMarchas = 0
                    if (!filtroMotoUiState.tipoCombustible.isEmpty()) filtroMoto.tipoCombustible = filtroMotoUiState.tipoCombustible


                    filtrosViewModel.asignarFiltro(filtroMoto)

                    buscarOnClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = stringResource(R.string.texto_buscar),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}