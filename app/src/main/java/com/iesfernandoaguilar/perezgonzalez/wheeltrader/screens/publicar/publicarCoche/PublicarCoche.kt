package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
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
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios.imagenByteArray
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PublicarCoche(
    appNavController: NavController,
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    publicarCocheViewModel: PublicarCocheViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()
    val publicarCocheUiState by publicarCocheViewModel.uiState.collectAsState()

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
            formularioSuperior(
                appViewModel,
                publicarCocheViewModel,
                publicarCocheUiState
            )
        }

        items(
            items = publicarCocheUiState.imagenes
        ) { imagen ->

            componenteImagen(
                imagen = imagen,
                onClickBorrarImagen = { publicarCocheViewModel.eliminarImagen(imagen) }
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
                            numSerieBastidor = publicarCocheUiState.nBastidor,
                            matricula = publicarCocheUiState.matricula,
                            tipoVehiculo = "Coche",
                            descripcion = publicarCocheUiState.descripcion,
                            precio = publicarCocheUiState.precio.toDouble(),
                            ciudad = publicarCocheUiState.ciudad,
                            provincia = publicarCocheUiState.provincia
                        )

                        var vCaracteristicas = listOf(
                            ValorCaracteristica(nombreCaracteristica = "Marca_Coche", valor = publicarCocheUiState.marca),
                            ValorCaracteristica(nombreCaracteristica = "Modelo_Coche", valor = publicarCocheUiState.modelo),
                            ValorCaracteristica(nombreCaracteristica = "Anio_Coche", valor = publicarCocheUiState.anio),
                            ValorCaracteristica(nombreCaracteristica = "CV_Coche", valor = publicarCocheUiState.cv),
                            ValorCaracteristica(nombreCaracteristica = "KM_Coche", valor = publicarCocheUiState.kilometaje),
                            ValorCaracteristica(nombreCaracteristica = "Marchas_Coche", valor = publicarCocheUiState.cantMarchas),
                            ValorCaracteristica(nombreCaracteristica = "Puertas_Coche", valor = publicarCocheUiState.nPuertas),
                            ValorCaracteristica(nombreCaracteristica = "TipoCombustible_Coche", valor = publicarCocheUiState.tipoCombustible),
                            ValorCaracteristica(nombreCaracteristica = "Transmision_Coche", valor = publicarCocheUiState.transmision),
                        )

                        /*var anuncio = Anuncio(
                            numSerieBastidor = "1234567890123",
                            matricula = "1234DXD",
                            tipoVehiculo = "Coche",
                            descripcion = "Prueba desde android",
                            precio = 4000.toDouble(),
                            ciudad = "Medina",
                            provincia = "Cadiz",
                        )

                        var vCaracteristicas = listOf(
                            ValorCaracteristica(nombreCaracteristica = "Marca_Coche", valor = "prueba"),
                            ValorCaracteristica(nombreCaracteristica = "Modelo_Coche", valor = "android"),
                            ValorCaracteristica(nombreCaracteristica = "Anio_Coche", valor = "2000"),
                            ValorCaracteristica(nombreCaracteristica = "CV_Coche", valor = "110"),
                            ValorCaracteristica(nombreCaracteristica = "KM_Coche", valor = "170000"),
                            ValorCaracteristica(nombreCaracteristica = "Marchas_Coche", valor = "5"),
                            ValorCaracteristica(nombreCaracteristica = "Puertas_Coche", valor = "5"),
                            ValorCaracteristica(nombreCaracteristica = "TipoCombustible_Coche", valor = "Diesel"),
                            ValorCaracteristica(nombreCaracteristica = "Transmision_Coche", valor = "Manual"),
                        )*/

                        anuncio.valoresCaracteristicas = vCaracteristicas

                        anuncio.vendedor = conectionUiState.usuario

                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            appViewModel.publicarAnuncio(anuncio, publicarCocheUiState.imagenes)
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
fun formularioSuperior(
    appViewModel: AppViewModel,
    publicarCocheViewModel: PublicarCocheViewModel,
    publicarCocheUiState: PublicarCocheUiState,
    modifier: Modifier = Modifier
) {
    var tiposCombustible = listOf("Gasolina", "Electrico", "Diesel", "Hibrido")
    var tiposCombustiblesDesplegado by remember { mutableStateOf(false) }

    var transmision = listOf("Manual", "Automatica")
    var transmisionDesplegado by remember { mutableStateOf(false) }

    var tamanioCombustible by remember { mutableStateOf(Size.Zero) }
    var tamanioTransmision by remember { mutableStateOf(Size.Zero) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            // Recojo la uri (it), se la paso a un metodo del viewModel de la app (porque es la que contiene el context de la aplicacion),
            // este me devuelve un array de bytes y si no es nulo significa que tengo la imagen en array de bytes para añadirla a la lista de imagenes
            var bytesImagen: ByteArray? = appViewModel.getBytesFromUri(it)
            if (bytesImagen != null) {
                publicarCocheViewModel.aniadirImagen(bytesImagen)
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
                text = "Especificaciones\ndel Coche",
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
                    value = publicarCocheUiState.marca,
                    onValueChange = { publicarCocheViewModel.cambiarMarca_Coche(it) },
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
                    value = publicarCocheUiState.modelo,
                    onValueChange = { publicarCocheViewModel.cambiarModelo_Coche(it) },
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
                    value = publicarCocheUiState.anio,
                    onValueChange = { publicarCocheViewModel.cambiarAnio_Coche(it) },
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
                    value = publicarCocheUiState.cv,
                    onValueChange = { publicarCocheViewModel.cambiarCv_Coche(it) },
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
                    value = publicarCocheUiState.kilometaje,
                    onValueChange = { publicarCocheViewModel.cambiarKm_Coche(it) },
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
                    value = publicarCocheUiState.cantMarchas,
                    onValueChange = { publicarCocheViewModel.cambiarCantMarchas_Coche(it) },
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
                    value = publicarCocheUiState.nBastidor,
                    onValueChange = { publicarCocheViewModel.cambiarNBastidor_Coche(it) },
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
                    value = publicarCocheUiState.matricula,
                    onValueChange = { publicarCocheViewModel.cambiarMatricula_Coche(it) },
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
                    value = publicarCocheUiState.nPuertas,
                    onValueChange = { publicarCocheViewModel.cambiarNPuertas_Coche(it) },
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

            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                // Vacío por tema de diseño
            }
        }

        // Desplegables
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCocheUiState.tipoCombustible,
                    onValueChange = { publicarCocheViewModel.cambiarTipoComb_Coche(it) },
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
                                publicarCocheViewModel.cambiarTipoComb_Coche(tipo)
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
                    value = publicarCocheUiState.transmision,
                    onValueChange = { publicarCocheViewModel.cambiarTransmision_Coche(it) },
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
                                publicarCocheViewModel.cambiarTransmision_Coche(tipo)
                                transmisionDesplegado = false
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
                    value = publicarCocheUiState.ciudad,
                    onValueChange = { publicarCocheViewModel.cambiarCiudad_Coche(it) },
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
                    value = publicarCocheUiState.provincia,
                    onValueChange = { publicarCocheViewModel.cambiarProvincia_Coche(it) },
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
                    value = publicarCocheUiState.precio,
                    onValueChange = { publicarCocheViewModel.cambiarPrecio_Coche(it) },
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
                    value = publicarCocheUiState.descripcion,
                    onValueChange = { publicarCocheViewModel.cambiarDescripcion_Coche(it) },
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

@Composable
fun componenteImagen(
    imagen: ByteArray,
    onClickBorrarImagen: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            // Imagen
            imagenByteArray(
                imagen,
                modifier = Modifier.fillMaxWidth()
            )

            // Botón de borrado
            IconButton (
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = { onClickBorrarImagen() },
                modifier = Modifier.fillMaxHeight().align(Alignment.TopEnd).size(50.dp).padding(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }

    }
}
