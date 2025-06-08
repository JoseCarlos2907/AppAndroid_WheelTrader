package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme

@Composable
fun FiltroTodo(
    buscarOnClick: () -> Unit,
    filtroTodoViewModel: FiltroTodoViewModel = viewModel(),
    filtrosViewModel: FiltrosViewModel,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
){
    val filtroTodoUiState by filtroTodoViewModel.uiState.collectAsState()

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
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25F)
        ){
            Text(
                text = stringResource(R.string.texto_cualquier_tipo_de_veh_culo),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Row (
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
                    value = filtroTodoUiState.marca,
                    onValueChange = { filtroTodoViewModel.cambiarMarca_Todo(it) },
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
                    value = filtroTodoUiState.modelo,
                    onValueChange = { filtroTodoViewModel.cambiarModelo_Todo(it) },
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

        Row (
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
                    value = filtroTodoUiState.anioMinimo,
                    onValueChange = { filtroTodoViewModel.cambiarAnioMin_Todo(it) },
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
                    value = filtroTodoUiState.anioMaximo,
                    onValueChange = { filtroTodoViewModel.cambiarAnioMax_Todo(it) },
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

        Row (
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
                    value = filtroTodoUiState.precioMinimo,
                    onValueChange = { filtroTodoViewModel.cambiarPrecioMin_Todo(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_precio_minimo),
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
                    value = filtroTodoUiState.precioMaximo,
                    onValueChange = { filtroTodoViewModel.cambiarPrecioMax_Todo(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_precio_maximo),
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

        Row (
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
                    value = filtroTodoUiState.ciudad,
                    onValueChange = { filtroTodoViewModel.cambiarCiudad_Todo(it) },
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
                    value = filtroTodoUiState.provincia,
                    onValueChange = { filtroTodoViewModel.cambiarProvincia_Todo(it) },
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

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25F)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ){

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5F)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.33F)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = filtroTodoUiState.tipoCoche,
                                    onCheckedChange = { filtroTodoViewModel.cambiarTipoCoche(it) },
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color(0xFF1c1c1c)
                                    )
                                )

                                Text(
                                    text = stringResource(R.string.texto_coche),
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(0.33F)
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = filtroTodoUiState.tipoCamion,
                                    onCheckedChange = { filtroTodoViewModel.cambiarTipoCamion(it) },
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color(0xFF1c1c1c)
                                    )
                                )

                                Text(
                                    text = stringResource(R.string.texto_camion),
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(0.33F)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = filtroTodoUiState.tipoMaquinaria,
                                    onCheckedChange = { filtroTodoViewModel.cambiarTipoMaquinaria(it) },
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color(0xFF1c1c1c)
                                    )
                                )

                                Text(
                                    text = stringResource(R.string.texto_maquinaria),
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5F)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.33F)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = filtroTodoUiState.tipoMoto,
                                    onCheckedChange = { filtroTodoViewModel.cambiarTipoMoto(it) },
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color(0xFF1c1c1c)
                                    )
                                )

                                Text(
                                    text = stringResource(R.string.texto_moto),
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(0.33F)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = filtroTodoUiState.tipoCamioneta,
                                    onCheckedChange = { filtroTodoViewModel.cambiarTipoCamioneta(it) },
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color(0xFF1c1c1c)
                                    )
                                )

                                Text(
                                    text = stringResource(R.string.texto_camioneta),
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(0.33F)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Vacío porque no hay otro tipo de Vehículo, está como relleno
                            }
                        }
                    }
                }
            }
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1F)
        ) {
            Button(
                onClick = {
                    var filtroTodo = FiltroTodo()

                    if (!filtroTodoUiState.marca.isEmpty()) filtroTodo.marca = filtroTodoUiState.marca
                    if (!filtroTodoUiState.modelo.isEmpty()) filtroTodo.modelo = filtroTodoUiState.modelo
                    if (!filtroTodoUiState.anioMinimo.isEmpty()) filtroTodo.anioMinimo = filtroTodoUiState.anioMinimo.toInt() else filtroTodo.anioMinimo = 1950
                    if (!filtroTodoUiState.anioMaximo.isEmpty()) filtroTodo.anioMaximo = filtroTodoUiState.anioMaximo.toInt() else filtroTodo.anioMaximo = 2025
                    if (!filtroTodoUiState.precioMinimo.isEmpty()) filtroTodo.precioMinimo = filtroTodoUiState.precioMinimo.toDouble() else filtroTodo.precioMinimo = 0.0
                    if (!filtroTodoUiState.precioMaximo.isEmpty()) filtroTodo.precioMaximo = filtroTodoUiState.precioMaximo.toDouble() else filtroTodo.precioMaximo = Double.MAX_VALUE
                    if (!filtroTodoUiState.ciudad.isEmpty()) filtroTodo.ciudad = filtroTodoUiState.ciudad
                    if (!filtroTodoUiState.provincia.isEmpty()) filtroTodo.provincia = filtroTodoUiState.provincia

                    if(filtroTodoUiState.tipoCoche){
                        filtroTodo.tiposVehiculo.add("Coche")
                    }

                    if(filtroTodoUiState.tipoMoto){
                        filtroTodo.tiposVehiculo.add("Moto")
                    }

                    if(filtroTodoUiState.tipoCamioneta){
                        filtroTodo.tiposVehiculo.add("Camioneta")
                    }

                    if(filtroTodoUiState.tipoCamion){
                        filtroTodo.tiposVehiculo.add("Camion")
                    }

                    if(filtroTodoUiState.tipoMaquinaria){
                        filtroTodo.tiposVehiculo.add("Maquinaria")
                    }

                    if(filtroTodo.tiposVehiculo.isEmpty()){
                        filtroTodo.tiposVehiculo.add("Coche")
                        filtroTodo.tiposVehiculo.add("Moto")
                        filtroTodo.tiposVehiculo.add("Camioneta")
                        filtroTodo.tiposVehiculo.add("Camion")
                        filtroTodo.tiposVehiculo.add("Maquinaria")

                    }

                    filtrosViewModel.asignarFiltro(filtroTodo)

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

@Preview(showBackground = true)
@Composable
fun PreviewFiltroTodo() {
    WheelTraderTheme {
        // FiltroTodo(FiltroTodoViewModel())
    }
}