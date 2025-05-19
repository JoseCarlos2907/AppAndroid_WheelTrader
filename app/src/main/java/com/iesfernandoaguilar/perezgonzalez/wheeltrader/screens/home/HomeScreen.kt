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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroBarraBusqueda
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCamion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCamioneta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroMaquinaria
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroMoto
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamion.FiltroCamionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamioneta.FiltroCamionetaViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMaquinaria.FiltroMaquinariaViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMoto.FiltroMotoViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme

@Composable
fun HomeScreen(
    filterButtonOnClick: () -> Unit,
    buscarOnClick: () -> Unit,
    conectionUiState: ConectionUiState,
    filtrosViewModel: FiltrosViewModel,
    filtroCocheViewModel: FiltroCocheViewModel = viewModel(),
    filtroMotoViewModel: FiltroMotoViewModel = viewModel(),
    filtroCamionViewModel: FiltroCamionViewModel = viewModel(),
    filtroCamionetaViewModel: FiltroCamionetaViewModel = viewModel(),
    filtroMaquinariaViewModel: FiltroMaquinariaViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val opciones = listOf("Coches", "Motos", "Camionetas", "Camiones", "Maquinaria")
    val imagenes = listOf(R.drawable.fotofiltrocoche, R.drawable.fotofiltromoto, R.drawable.fotofiltrocamioneta, R.drawable.fotofiltrocamion, R.drawable.fotofiltromaquinaria)
    val funciones = listOf(
        {
            var filtroCoche = FiltroCoche()

            filtroCoche.marca = null
            filtroCoche.modelo = null
            filtroCoche.anioMinimo = 1950
            filtroCoche.anioMaximo = 2025
            filtroCoche.cvMinimo = 40
            filtroCoche.cvMaximo = 1500
            filtroCoche.ciudad = null
            filtroCoche.provincia = null
            filtroCoche.kmMinimo = 0
            filtroCoche.kmMaximo = 2000000
            filtroCoche.cantMarchas = 0
            filtroCoche.nPuertas = 0
            filtroCoche.tipoCombustible = null
            filtroCoche.transmision = null


            filtrosViewModel.asignarFiltro(filtroCoche)

            buscarOnClick()
        },
        {
            var filtroMoto = FiltroMoto()

            filtroMoto.marca = null
            filtroMoto.modelo = null
            filtroMoto.anioMinimo = 1950
            filtroMoto.anioMaximo = 2025
            filtroMoto.cvMinimo = 50
            filtroMoto.cvMaximo = 2000
            filtroMoto.ciudad = null
            filtroMoto.provincia = null
            filtroMoto.kmMinimo = 0
            filtroMoto.kmMaximo = 1000000
            filtroMoto.cantMarchas = 0
            filtroMoto.tipoCombustible = null


            filtrosViewModel.asignarFiltro(filtroMoto)

            buscarOnClick()
        },
        {
            var filtroCamion = FiltroCamion()

            filtroCamion.marca = null
            filtroCamion.modelo = null
            filtroCamion.anioMinimo = 1950
            filtroCamion.anioMaximo = 2025
            filtroCamion.cvMinimo = 100
            filtroCamion.cvMaximo = 800
            filtroCamion.ciudad = null
            filtroCamion.provincia = null
            filtroCamion.kmMinimo = 0
            filtroCamion.kmMaximo = 2000000
            filtroCamion.tipoCombustible = null


            filtrosViewModel.asignarFiltro(filtroCamion)

            buscarOnClick()
        },
        {
            var filtroCamioneta = FiltroCamioneta()

            filtroCamioneta.marca = null
            filtroCamioneta.modelo = null
            filtroCamioneta.anioMinimo = 1950
            filtroCamioneta.anioMaximo = 2025
            filtroCamioneta.cvMinimo = 50
            filtroCamioneta.cvMaximo = 2000
            filtroCamioneta.ciudad = null
            filtroCamioneta.provincia = null
            filtroCamioneta.kmMinimo = 0
            filtroCamioneta.kmMaximo = 2000000
            filtroCamioneta.cantMarchas = 0
            filtroCamioneta.nPuertas = 0
            filtroCamioneta.tipoCombustible = null


            filtrosViewModel.asignarFiltro(filtroCamioneta)

            buscarOnClick()
        },
        {
            var filtroMaquinaria = FiltroMaquinaria()

            filtroMaquinaria.marca = null
            filtroMaquinaria.modelo = null
            filtroMaquinaria.anioMinimo = 1950
            filtroMaquinaria.anioMaximo = 2025
            filtroMaquinaria.ciudad = null
            filtroMaquinaria.provincia = null
            filtroMaquinaria.tipoCombustible = null


            filtrosViewModel.asignarFiltro(filtroMaquinaria)

            buscarOnClick()
        },
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
    ) {
        item{
            parteSuperiorHome(
                filterButtonOnClick = filterButtonOnClick,
                buscarOnClick = buscarOnClick,
                filtrosViewModel = filtrosViewModel,
                conectionUiState = conectionUiState
            )
        }

        items(opciones) { opcion ->
            tipoVehiculoCard(
                { funciones.get(opciones.indexOf(opcion))() },
                imagenes.get(opciones.indexOf(opcion)),
                opcion
            )
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

@Composable
fun parteSuperiorHome(
    filterButtonOnClick: () -> Unit,
    buscarOnClick: () -> Unit,
    filtrosViewModel: FiltrosViewModel,
    conectionUiState: ConectionUiState,
    modifier: Modifier = Modifier
){
    var texto by remember { mutableStateOf("")}

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(60.dp).fillMaxWidth()
        ) {
            Text(
                text = "Hola,${conectionUiState.usuario?.nombreUsuario ?: "_NOMBRE_"}",
                // text = "Hola, prueba123",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }

        Row(
            modifier = Modifier.padding(top = 10.dp,bottom = 10.dp)
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
                                unfocusedIndicatorColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
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
                            onClick = {
                                var filtroBarraBusqueda = FiltroBarraBusqueda()

                                filtroBarraBusqueda.cadena = texto
                                filtroBarraBusqueda.anioMinimo = 1950
                                filtroBarraBusqueda.anioMaximo = 2025
                                filtroBarraBusqueda.precioMinimo = 0.0
                                filtroBarraBusqueda.precioMaximo = Double.MAX_VALUE
                                filtroBarraBusqueda.ciudad = null
                                filtroBarraBusqueda.provincia = null
                                filtroBarraBusqueda.tiposVehiculo.add("Coche")
                                filtroBarraBusqueda.tiposVehiculo.add("Moto")
                                filtroBarraBusqueda.tiposVehiculo.add("Camioneta")
                                filtroBarraBusqueda.tiposVehiculo.add("Camion")
                                filtroBarraBusqueda.tiposVehiculo.add("Maquinaria")

                                filtrosViewModel.asignarFiltro(filtroBarraBusqueda)

                                buscarOnClick()
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
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    WheelTraderTheme {
        // HomeScreen()
    }
}