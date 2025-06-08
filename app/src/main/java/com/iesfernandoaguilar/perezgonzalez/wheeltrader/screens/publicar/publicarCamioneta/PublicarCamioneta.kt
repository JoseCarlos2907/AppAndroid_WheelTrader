package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.ValorCaracteristica
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.componenteImagen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.PublicarCocheUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.PublicarCocheViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.formularioSuperiorCoche
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PublicarCamioneta(
    appNavController: NavController,
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    publicarCamionetaViewModel: PublicarCamionetaViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()
    val publicarCamionetaUiState by publicarCamionetaViewModel.uiState.collectAsState()

    LaunchedEffect(appUiState.goToHome) {
        if(appUiState.goToHome){
            appNavController.navigate(AppScreens.Home.screenName)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Black, Color(0xFF525151)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        item{
            formularioSuperiorCamioneta(
                appViewModel,
                publicarCamionetaViewModel,
                publicarCamionetaUiState
            )
        }

        items(
            items = publicarCamionetaUiState.imagenes
        ) { imagen ->

            componenteImagen(
                imagen = imagen,
                onClickBorrarImagen = { publicarCamionetaViewModel.eliminarImagen(imagen) }
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
                            numSerieBastidor = publicarCamionetaUiState.nBastidor,
                            matricula = publicarCamionetaUiState.matricula,
                            tipoVehiculo = "Camioneta",
                            descripcion = publicarCamionetaUiState.descripcion,
                            precio = publicarCamionetaUiState.precio.toDouble(),
                            ciudad = publicarCamionetaUiState.ciudad,
                            provincia = publicarCamionetaUiState.provincia
                        )

                        var vCaracteristicas = listOf(
                            ValorCaracteristica(nombreCaracteristica = "Marca_Camioneta", valor = publicarCamionetaUiState.marca),
                            ValorCaracteristica(nombreCaracteristica = "Modelo_Camioneta", valor = publicarCamionetaUiState.modelo),
                            ValorCaracteristica(nombreCaracteristica = "Anio_Camioneta", valor = publicarCamionetaUiState.anio),
                            ValorCaracteristica(nombreCaracteristica = "CV_Camioneta", valor = publicarCamionetaUiState.cv),
                            ValorCaracteristica(nombreCaracteristica = "KM_Camioneta", valor = publicarCamionetaUiState.kilometaje),
                            ValorCaracteristica(nombreCaracteristica = "Marchas_Camioneta", valor = publicarCamionetaUiState.cantMarchas),
                            ValorCaracteristica(nombreCaracteristica = "Puertas_Camioneta", valor = publicarCamionetaUiState.nPuertas),
                            ValorCaracteristica(nombreCaracteristica = "CargaKg_Camioneta", valor = publicarCamionetaUiState.capacidadCarga),
                            ValorCaracteristica(nombreCaracteristica = "TipoCombustible_Camioneta", valor = publicarCamionetaUiState.tipoCombustible),
                            ValorCaracteristica(nombreCaracteristica = "TipoTraccion_Camioneta", valor = publicarCamionetaUiState.tipoTraccion),
                        )

                        anuncio.valoresCaracteristicas = vCaracteristicas

                        anuncio.vendedor = conectionUiState.usuario

                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            appViewModel.publicarAnuncio(anuncio, publicarCamionetaUiState.imagenes)
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.texto_publicar_anuncio),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun formularioSuperiorCamioneta(
    appViewModel: AppViewModel,
    publicarCamionetaViewModel: PublicarCamionetaViewModel,
    publicarCamionetaUiState: PublicarCamionetaUiState,
    modifier: Modifier = Modifier
) {
    var tiposCombustible = listOf("Gasoleo", "Electrico")
    var tiposCombustiblesDesplegado by remember { mutableStateOf(false) }

    var tiposTraccion = listOf("4x2", "4x4")
    var tiposTraccionDesplegado by remember { mutableStateOf(false) }

    var tamanioCombustible by remember { mutableStateOf(Size.Zero) }
    var tamanioTiposTraccion by remember { mutableStateOf(Size.Zero) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            // Recojo la uri (it), se la paso a un metodo del viewModel de la app (porque es la que contiene el context de la aplicacion),
            // este me devuelve un array de bytes y si no es nulo significa que tengo la imagen en array de bytes para añadirla a la lista de imagenes
            var bytesImagen: ByteArray? = appViewModel.getBytesFromUri(it)
            if (bytesImagen != null) {
                publicarCamionetaViewModel.aniadirImagen(bytesImagen)
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
                text = stringResource(R.string.texto_especificaciones_de_la_camioneta),
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
                    value = publicarCamionetaUiState.marca,
                    onValueChange = { publicarCamionetaViewModel.cambiarMarca_Camioneta(it) },
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
                    value = publicarCamionetaUiState.modelo,
                    onValueChange = { publicarCamionetaViewModel.cambiarModelo_Camioneta(it) },
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionetaUiState.anio,
                    onValueChange = { publicarCamionetaViewModel.cambiarAnio_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_ano),
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
                    value = publicarCamionetaUiState.cv,
                    onValueChange = { publicarCamionetaViewModel.cambiarCv_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_cv),
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
                    value = publicarCamionetaUiState.kilometaje,
                    onValueChange = { publicarCamionetaViewModel.cambiarKm_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_kilometraje),
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
                    value = publicarCamionetaUiState.cantMarchas,
                    onValueChange = { publicarCamionetaViewModel.cambiarCantMarchas_Camioneta(it) },
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
                    value = publicarCamionetaUiState.nBastidor,
                    onValueChange = { publicarCamionetaViewModel.cambiarNBastidor_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_n_de_bastidor),
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
                    value = publicarCamionetaUiState.matricula,
                    onValueChange = { publicarCamionetaViewModel.cambiarMatricula_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_matricula),
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
                    value = publicarCamionetaUiState.nPuertas,
                    onValueChange = { publicarCamionetaViewModel.cambiarNPuertas_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_n_de_puertas),
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
                    value = publicarCamionetaUiState.capacidadCarga,
                    onValueChange = { publicarCamionetaViewModel.cambiarCapacidadCarga_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_carga_max),
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
                    value = publicarCamionetaUiState.tipoCombustible,
                    onValueChange = { publicarCamionetaViewModel.cambiarTipoComb_Camioneta(it) },
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
                                publicarCamionetaViewModel.cambiarTipoComb_Camioneta(tipo)
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
                    value = publicarCamionetaUiState.tipoTraccion,
                    onValueChange = { publicarCamionetaViewModel.cambiarTipoTraccion_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_transmision),
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
                            imageVector = if (tiposTraccionDesplegado) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.desc_icono_flecha_arriba_abajo),
                            modifier = Modifier.clickable {
                                tiposTraccionDesplegado = !tiposTraccionDesplegado
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coords ->
                            tamanioTiposTraccion = coords.size.toSize()
                        }
                )

                DropdownMenu(
                    expanded = tiposTraccionDesplegado,
                    onDismissRequest = { tiposTraccionDesplegado = false },
                    modifier = Modifier.width(
                        with(LocalDensity.current) { tamanioTiposTraccion.width.toDp() }
                    )
                ) {
                    tiposTraccion.forEach { tipo ->
                        DropdownMenuItem(
                            onClick = {
                                publicarCamionetaViewModel.cambiarTipoTraccion_Camioneta(tipo)
                                tiposTraccionDesplegado = false
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
                    value = publicarCamionetaUiState.ciudad,
                    onValueChange = { publicarCamionetaViewModel.cambiarCiudad_Camioneta(it) },
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
                    value = publicarCamionetaUiState.provincia,
                    onValueChange = { publicarCamionetaViewModel.cambiarProvincia_Camioneta(it) },
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5F)
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = publicarCamionetaUiState.precio,
                    onValueChange = { publicarCamionetaViewModel.cambiarPrecio_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_precio),
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
                    value = publicarCamionetaUiState.descripcion,
                    onValueChange = { publicarCamionetaViewModel.cambiarDescripcion_Camioneta(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_descripcion_sinp),
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
                    text = stringResource(R.string.texto_anadir_imagen),
                    color = Color.Black,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
