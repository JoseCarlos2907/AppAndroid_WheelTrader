package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamion

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.ValorCaracteristica
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.componenteImagen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta.PublicarCamionetaUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta.PublicarCamionetaViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta.formularioSuperiorCamioneta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PublicarCamion(
    appNavController: NavController,
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    publicarCamionViewModel: PublicarCamionViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()
    val publicarCamionUiState by publicarCamionViewModel.uiState.collectAsState()

    LaunchedEffect(appUiState.goToHome) {
        if(appUiState.goToHome){
            appNavController.navigate(AppScreens.Home.screenName)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
    ) {
        item{
            formularioSuperiorCamion(
                appViewModel,
                publicarCamionViewModel,
                publicarCamionUiState
            )
        }

        items(
            items = publicarCamionUiState.imagenes
        ) { imagen ->

            componenteImagen(
                imagen = imagen,
                onClickBorrarImagen = { publicarCamionViewModel.eliminarImagen(imagen) }
            )
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        var anuncio = Anuncio(
                            numSerieBastidor = publicarCamionUiState.nBastidor,
                            matricula = publicarCamionUiState.matricula,
                            tipoVehiculo = "Camion",
                            descripcion = publicarCamionUiState.descripcion,
                            precio = publicarCamionUiState.precio.toDouble(),
                            ciudad = publicarCamionUiState.ciudad,
                            provincia = publicarCamionUiState.provincia
                        )

                        var vCaracteristicas = listOf(
                            ValorCaracteristica(nombreCaracteristica = "Marca_Camion", valor = publicarCamionUiState.marca),
                            ValorCaracteristica(nombreCaracteristica = "Modelo_Camion", valor = publicarCamionUiState.modelo),
                            ValorCaracteristica(nombreCaracteristica = "Anio_Camion", valor = publicarCamionUiState.anio),
                            ValorCaracteristica(nombreCaracteristica = "CV_Camion", valor = publicarCamionUiState.cv),
                            ValorCaracteristica(nombreCaracteristica = "KM_Camion", valor = publicarCamionUiState.kilometaje),
                            ValorCaracteristica(nombreCaracteristica = "Marchas_Camion", valor = publicarCamionUiState.cantMarchas),
                            ValorCaracteristica(nombreCaracteristica = "Marchas_Camion", valor = publicarCamionUiState.cantMarchas),
                            ValorCaracteristica(nombreCaracteristica = "CargaKg_Camion", valor = publicarCamionUiState.capacidadCarga),
                            ValorCaracteristica(nombreCaracteristica = "TipoCombustible_Camion", valor = publicarCamionUiState.tipoCombustible),
                        )

                        anuncio.valoresCaracteristicas = vCaracteristicas

                        anuncio.vendedor = conectionUiState.usuario

                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            appViewModel.publicarAnuncio(anuncio, publicarCamionUiState.imagenes)
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Publicar anuncio",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}


@Composable
fun formularioSuperiorCamion(
    appViewModel: AppViewModel,
    publicarCamionViewModel: PublicarCamionViewModel,
    publicarCamionUiState: PublicarCamionUiState,
    modifier: Modifier = Modifier
){
    var tiposCombustible = listOf("Gasoleo", "Electrico")
    var tiposCombustiblesDesplegado by remember { mutableStateOf(false) }

    var tamanioCombustible by remember { mutableStateOf(Size.Zero) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            // Recojo la uri (it), se la paso a un metodo del viewModel de la app (porque es la que contiene el context de la aplicacion),
            // este me devuelve un array de bytes y si no es nulo significa que tengo la imagen en array de bytes para añadirla a la lista de imagenes
            var bytesImagen: ByteArray? = appViewModel.getBytesFromUri(it)
            if (bytesImagen != null) {
                publicarCamionViewModel.aniadirImagen(bytesImagen)
            }
        }
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Especificaciones\nde la Camion",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.marca,
                    onValueChange = { publicarCamionViewModel.cambiarMarca_Camion(it) },
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
                    value = publicarCamionUiState.modelo,
                    onValueChange = { publicarCamionViewModel.cambiarModelo_Camion(it) },
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.anio,
                    onValueChange = { publicarCamionViewModel.cambiarAnio_Camion(it) },
                    placeholder = {
                        Text(
                            text = "Año",
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
                    value = publicarCamionUiState.cv,
                    onValueChange = { publicarCamionViewModel.cambiarCv_Camion(it) },
                    placeholder = {
                        Text(
                            text = "CV",
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.kilometaje,
                    onValueChange = { publicarCamionViewModel.cambiarKm_Camion(it) },
                    placeholder = {
                        Text(
                            text = "Kilometraje",
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
                    value = publicarCamionUiState.cantMarchas,
                    onValueChange = { publicarCamionViewModel.cambiarCantMarchas_Camion(it) },
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
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.nBastidor,
                    onValueChange = { publicarCamionViewModel.cambiarNBastidor_Camion(it) },
                    placeholder = {
                        Text(
                            text = "Nº de Bastidor",
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
                    value = publicarCamionUiState.matricula,
                    onValueChange = { publicarCamionViewModel.cambiarMatricula_Camion(it) },
                    placeholder = {
                        Text(
                            text = "Matrícula",
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
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.capacidadCarga,
                    onValueChange = { publicarCamionViewModel.cambiarCapacidadCarga_Camion(it) },
                    placeholder = {
                        Text(
                            text = "Carga Máxima (Kg)",
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
                    value = publicarCamionUiState.tipoCombustible,
                    onValueChange = { publicarCamionViewModel.cambiarTipoComb_Camion(it) },
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
                                publicarCamionViewModel.cambiarTipoComb_Camion(tipo)
                                tiposCombustiblesDesplegado = false
                            },
                            text = { Text(text = tipo) }
                        )
                    }
                }
            }
        }


        HorizontalDivider(
            color = Color.White,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 6.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.ciudad,
                    onValueChange = { publicarCamionViewModel.cambiarCiudad_Camion(it) },
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
                    value = publicarCamionUiState.provincia,
                    onValueChange = { publicarCamionViewModel.cambiarProvincia_Camion(it) },
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.precio,
                    onValueChange = { publicarCamionViewModel.cambiarPrecio_Camion(it) },
                    placeholder = {
                        Text(
                            text = "Precio",
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
                // Vacío por tema de diseño
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionUiState.descripcion,
                    onValueChange = { publicarCamionViewModel.cambiarDescripcion_Camion(it) },
                    placeholder = {
                        Text(
                            text = "Descripción",
                            color = Color(0x00FF1c1c1c)
                        )
                    },
                    enabled = true,
                    singleLine = false,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }


        // Imágenes
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Añadir Imagen",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}