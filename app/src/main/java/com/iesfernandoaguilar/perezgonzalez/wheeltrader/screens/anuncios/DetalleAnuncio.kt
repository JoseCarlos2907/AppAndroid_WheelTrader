package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Reporte
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel

@Composable
fun DetalleAnuncio(
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    onClickComprar: (Long, Long, String) -> Unit,
    goToReporte: () -> Unit,
    goToCompraComprador: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            appViewModel.salirDetalleAnuncio()
        }
    }

    LaunchedEffect(appUiState.goToCompraComprador) {
        if(appUiState.goToCompraComprador){
            goToCompraComprador()
            appViewModel.reiniciarGoToCompraComprador()
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
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
        item {
            DetallesVehiculo(
                appViewModel = appViewModel,
                conectionUiState = conectionUiState,
                anuncio =  appUiState.anuncioSeleccionado!!,
                imagenes = appUiState.imagenesAnuncioSeleccionado,
                onClickComprar = { onClickComprar(conectionUiState.usuario!!.idUsuario, appUiState.anuncioSeleccionado!!.idAnuncio, appUiState.anuncioSeleccionado!!.tipoVehiculo!!) },
                goToReporte = { goToReporte() }
            )
        }

        item{
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    ) {
                        appUiState.anuncioSeleccionado!!.valoresCaracteristicas!!.forEach { vCaracteristica ->
                            Text(
                                text = textoVC(vCaracteristica.nombreCaracteristica, vCaracteristica.valor),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            HorizontalDivider(color = MaterialTheme.colorScheme.onTertiaryContainer, thickness = 2.dp)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun DetallesVehiculo(
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    onClickComprar: () -> Unit,
    goToReporte: () -> Unit,
    anuncio: Anuncio,
    imagenes: List<ByteArray>,
    modifier: Modifier = Modifier
){
    var marca = ""
    var modelo = ""

    var posImagen by remember { mutableStateOf(0) }

    if(anuncio.valoresCaracteristicas != null){
        anuncio.valoresCaracteristicas!!.forEach { vc ->
            if(vc.nombreCaracteristica.contains("Marca")){
                marca = vc.valor
            }else if(vc.nombreCaracteristica.contains("Modelo")){
                modelo = vc.valor
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = stringResource(R.string.texto_marca_modelo, marca, modelo),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 12.dp)
                )

                IconButton (
                    onClick = {
                        var reporte = Reporte(
                            usuarioEnvia = conectionUiState.usuario!!,
                            usuarioRecibe = anuncio.vendedor!!
                        )

                        appViewModel.asignarReporte(reporte)
                        goToReporte()
                    },
                    enabled = if(conectionUiState.usuario!!.idUsuario == anuncio.vendedor!!.idUsuario) false else true,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.iconoreporte),
                        contentDescription = stringResource(R.string.desc_icono_reportar),
                        tint = if(conectionUiState.usuario!!.idUsuario == anuncio.vendedor!!.idUsuario) Color.Gray else Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                if (!imagenes.isEmpty()) {
                    ImagenAsync(
                        bytes = imagenes.get(posImagen),
                        modifier = Modifier
                            .height(200.dp)
                            .align(Alignment.Center)
                    )
                } else {
                    CircularProgressIndicator()
                }

                Surface(
                    onClick = {
                        if(posImagen > 0) posImagen--
                    },
                    enabled = if(posImagen < 1) false else true,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp
                    ),
                    color = if(posImagen < 1) Color.LightGray else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(width = 36.dp, height = 60.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(R.drawable.iconoflechaizqgris),
                        contentDescription = stringResource(R.string.desc_icono_flecha_izquierda_gris),
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Surface(
                    onClick = {
                        if(posImagen < imagenes.size-1) posImagen++
                    },
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        bottomStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    ),
                    enabled = if(posImagen > imagenes.size-2) false else true,
                    color = if(posImagen > imagenes.size-2) Color.LightGray else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(width = 36.dp, height = 60.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Image(
                        painter = painterResource(R.drawable.iconoflechadrcgris),
                        contentDescription = stringResource(R.string.desc_icono_flecha_derecha_gris),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = anuncio.tipoVehiculo!!,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )

            if(anuncio.vendedor != null){
                Text(
                    text = stringResource(R.string.de_propietario, anuncio.vendedor!!.nombreUsuario),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(12.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.5F)
                    ) {
                        Text(
                            text = stringResource(R.string.precio, anuncio.precio),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.Black
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.5F)
                    ) {
                        Button(
                            onClick = { onClickComprar() },
                            enabled = if(conectionUiState.usuario!!.idUsuario == anuncio.vendedor?.idUsuario) false else true,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = stringResource(R.string.comprar),
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {
                    Text(
                        text = stringResource(R.string.descripcion),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )


                    Text(
                        text = anuncio.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun ImagenAsync(
    bytes: ByteArray?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = bytes,
        contentDescription = null,
        modifier = modifier,
        placeholder = painterResource(R.drawable.iconocargando),
        error = painterResource(R.drawable.iconoerrorimagen),
        contentScale = ContentScale.Crop
    )
}

private fun textoVC(tipo: String, valor: String): String{
    var texto =
    if(tipo.contains("Marca")){
        "Marca"
    }else if(tipo.contains("Modelo")){
        "Modelo"
    }else if(tipo.contains("CV")){
        "CV"
    }else if(tipo.contains("Anio")){
        "Año"
    }else if(tipo.contains("KM")){
        "Kilometraje"
    }else if(tipo.contains("TipoCombustible")){
        "Tipo de Combustible"
    }else if(tipo.contains("Transmision")){
        "Transmisión"
    }else if(tipo.contains("Marchas")){
        "Nº de Marchas"
    }else if(tipo.contains("Puertas")){
        "Nº de Puertas"
    }else if(tipo.contains("Cilindrada")){
        "Cilindrada"
    }else if(tipo.contains("TipoTraccion")){
        "Tipo de Tracción"
    }else if(tipo.contains("CargaKg")){
        "Carga de Kg máxima"
    }else{
        "No existe"
    }

    return texto + ": " + valor
}