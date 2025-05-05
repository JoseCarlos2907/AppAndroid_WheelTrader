package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FiltroTodo(
    appNavController: NavController,
    filtroTodoViewModel: FiltroTodoViewModel,
    filtrosViewModel: FiltrosViewModel,
    appViewModel: AppViewModel,
    appUiState: AppUiState,
    modifier: Modifier = Modifier
){
    val filtroTodoUiState by filtroTodoViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().background(
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
            modifier = Modifier.fillMaxWidth().weight(0.25F)
        ){
            Text(
                text = "Cualquier tipo\nde vehículo",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Row (
            modifier = Modifier.fillMaxWidth().weight(0.1F)
        ) {
            Column(
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.marca,
                    onValueChange = { filtroTodoViewModel.cambiarMarca_Todo(it) },
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
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.modelo,
                    onValueChange = { filtroTodoViewModel.cambiarModelo_Todo(it) },
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

        Row (
            modifier = Modifier.fillMaxWidth().weight(0.1F)
        ) {
            Column(
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.anioMinimo,
                    onValueChange = { filtroTodoViewModel.cambiarAnioMin_Todo(it) },
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
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.anioMaximo,
                    onValueChange = { filtroTodoViewModel.cambiarAnioMax_Todo(it) },
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

        Row (
            modifier = Modifier.fillMaxWidth().weight(0.1F)
        ) {
            Column(
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.precioMinimo,
                    onValueChange = { filtroTodoViewModel.cambiarPrecioMin_Todo(it) },
                    placeholder = {
                        Text(
                            text = "Precio Mínimo",
                            color = Color(0x00FF1c1c1c)
                        )
                    },
                    // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.precioMaximo,
                    onValueChange = { filtroTodoViewModel.cambiarPrecioMax_Todo(it) },
                    placeholder = {
                        Text(
                            text = "Precio Máximo",
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
            modifier = Modifier.fillMaxWidth().weight(0.1F)
        ) {
            Column(
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.ciudad,
                    onValueChange = { filtroTodoViewModel.cambiarCiudad_Todo(it) },
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
                modifier = Modifier.weight(0.5F).padding(4.dp)
            ) {
                OutlinedTextField(
                    value = filtroTodoUiState.provincia,
                    onValueChange = { filtroTodoViewModel.cambiarProvincia_Todo(it) },
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

        Row (
            modifier = Modifier.fillMaxWidth().weight(0.25F)
        ) {
            // TODO: Hacer el card con los tipos de vehiculos
        }

        Row (
            modifier = Modifier.fillMaxWidth().weight(0.1F)
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

                    filtrosViewModel.asignarFiltroTodo(filtroTodo)

                    appNavController.navigate(AppScreens.ListaAnuncios.screenName)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
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

@Preview(showBackground = true)
@Composable
fun PreviewFiltroTodo() {
    WheelTraderTheme {
        // FiltroTodo(FiltroTodoViewModel())
    }
}