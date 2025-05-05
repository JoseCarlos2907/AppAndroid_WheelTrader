package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme

@Composable
fun HomeScreen(
    filterButtonOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val opciones = listOf("Coches", "Motos", "Camionetas", "Camiones", "Maquinaria")
    val imagenes = listOf(R.drawable.fotofiltrocoche, R.drawable.fotofiltromoto, R.drawable.fotofiltrocamioneta, R.drawable.fotofiltrocamion, R.drawable.fotofiltromaquinaria)

    var texto by remember { mutableStateOf("")}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        ).padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.1F)
        ) {
            Text(
                //text = "Hola,\n\n${conectionUiState.usuario?.nombreUsuario ?: "_NOMBRE_"}"
                text = "Hola, prueba123",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }

        Row(
            modifier = Modifier.weight(0.4F).padding(top = 10.dp,bottom = 10.dp)
        ){
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxSize().padding(10.dp).height(200.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(0.6F).padding(top = 6.dp, start = 12.dp, end = 12.dp)
                    ) {
                        OutlinedTextField(
                            value = texto,
                            onValueChange = {texto = it},
                            placeholder = { Text("Buscar...") },
                            textStyle = MaterialTheme.typography.labelMedium,
                            singleLine = true,
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                bottomStart = 12.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            ),
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
                            modifier = Modifier.weight(1F).height(50.dp).fillMaxWidth().offset(x = 4.dp),
                        )

                        Button(
                            onClick = { filterButtonOnClick() },
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 0.dp,
                                topEnd = 12.dp,
                                bottomEnd = 12.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.padding(start = 8.dp).height(50.dp).offset(x = (-4).dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.iconofiltroblanco),
                                contentDescription = ""
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().weight(0.4F).padding(bottom = 10.dp)
                    ) {
                        Button(
                            onClick = { },
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
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.weight(0.6F)
        ) {
            items(opciones) { opcion ->
                tipoVehiculoCard(
                    {},
                    imagenes.get(opciones.indexOf(opcion)),
                    opcion
                )
            }
        }
    }
}

@Composable
fun tipoVehiculoCard(
    onClick: () -> Unit,
    imagen: Int,
    texto: String
){
    Row{
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            onClick = onClick,
            modifier = Modifier.height(100.dp).padding(10.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier.fillMaxHeight().weight(0.2F)
                ) {
                    Image(
                        painter = painterResource(imagen),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize().weight(0.6F)
                ) {
                    Text(
                        text = texto,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    WheelTraderTheme {
        // HomeScreen()
    }
}