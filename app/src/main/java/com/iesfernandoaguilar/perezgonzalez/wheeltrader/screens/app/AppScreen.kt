package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainAppBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainBottomBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios.DetalleAnuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios.ListaAnuncios
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.reporte.ReporteScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras.CompraCompradorScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras.CompraVendedorScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras.MisComprasScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras.PagoPayPalScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.confUsuario.ConfUsuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModelFactory
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.TiposFiltros
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamion.FiltroCamion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamioneta.FiltroCamioneta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMaquinaria.FiltroMaquinaria
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMoto.FiltroMoto
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.notificaciones.NotificacionesScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.ListaTiposVehiculos
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamion.PublicarCamion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta.PublicarCamioneta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.PublicarCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMaquinaria.PublicarMaquinaria
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMoto.PublicarMoto
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia.RecuperarContraseniaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class AppScreens(val screenName: String){
    Home(screenName = "home"),

    ConfUsu(screenName = "conf_usu"),

    ListaTiposPublicar(screenName = "lista_tipos_publicar"),
    PublicarCoche(screenName = "publicar_coche"),
    PublicarMoto(screenName = "publicar_moto"),
    PublicarCamioneta(screenName = "publicar_camioneta"),
    PublicarCamion(screenName = "publicar_camion"),
    PublicarMaquinaria(screenName = "publicar_maquinaria"),

    Notificaciones(screenName = "notificaciones"),

    TipoFiltros(screenName = "tipo_filtros"),
    FiltroTodo(screenName = "filtro_todo"),
    FiltroCoche(screenName = "filtro_coche"),
    FiltroMoto(screenName = "filtro_moto"),
    FiltroCamioneta(screenName = "filtro_camioneta"),
    FiltroCamion(screenName = "filtro_camion"),
    FiltroMaquinaria(screenName = "filtro_maquinaria"),

    ListaAnuncios(screenName = "lista_anuncios"),
    DetalleAnuncio(screenName = "detalle_anuncio"),
    ReportarUsuario(screenName = "reportar_usuario"),

    CompraComprador(screenName = "compra_comprador"),
    CompraVendedor(screenName = "compra_vendedor"),
    ConfirmarPagoPayPal(screenName = "confirmar_pago_paypal"),
    MisCompras(screenName = "mis_compras")
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AppScreen(
    context: Context,
    navController: NavHostController,
    appNavController: NavHostController = rememberNavController(),
    conectionViewModel: ConectionViewModel,
    appViewModel: AppViewModel,
    filtrosViewModel: FiltrosViewModel = viewModel(
        factory = FiltrosViewModelFactory()
    ),
    modifier: Modifier = Modifier
){

    val conectionUiState by conectionViewModel.uiState.collectAsState()
    val filtrosUiState by filtrosViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            appViewModel.confVM(context)
            appViewModel.showMsg = { context, msg ->
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
        appViewModel.escucharDelServidor_App()
    }

    DisposableEffect(Unit) {
        onDispose {
            Log.d("App", "Se cierra el hilo principal")
            appViewModel.pararEscuchaServidor_App()
        }
    }

    LaunchedEffect (conectionUiState.usuario) {
        if(conectionUiState.usuario == null){
            navController.navigate(WheelTraderScreens.Login.screenName) {
                popUpTo(WheelTraderScreens.App.screenName) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            mainAppBar()
        },
        bottomBar = {
            mainBottomBar(
                confUsuarioOnClick = {
                    appNavController.navigate(AppScreens.ConfUsu.screenName)
                },
                homeOnClick = {
                    appNavController.navigate(AppScreens.Home.screenName)
                },
                notificacionesOnClick = {
                    appNavController.navigate(AppScreens.Notificaciones.screenName)
                },
                publicarAnuncioOnClick = {
                    appNavController.navigate(AppScreens.ListaTiposPublicar.screenName)
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = appNavController,
            startDestination = AppScreens.Home.screenName,
            modifier = modifier.fillMaxSize().padding(innerPadding)
        ){
            composable(route = AppScreens.Home.screenName){
                HomeScreen(
                    filterButtonOnClick = { appNavController.navigate(AppScreens.TipoFiltros.screenName) },
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.TipoFiltros.screenName){
                TiposFiltros(
                    filtrosViewModel = filtrosViewModel,
                    appNavController = appNavController
                )
            }

            composable(route = AppScreens.FiltroTodo.screenName){
                FiltroTodo(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    appViewModel = appViewModel,
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroCoche.screenName){
                FiltroCoche(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroMoto.screenName){
                FiltroMoto(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroCamioneta.screenName){
                FiltroCamioneta(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroCamion.screenName){
                FiltroCamion(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroMaquinaria.screenName){
                FiltroMaquinaria(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.ListaAnuncios.screenName){
                ListaAnuncios(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    filtrosUiState = filtrosUiState,
                    onClickAnuncio = { anuncio ->
                        // Log.d("App", anuncio.idAnuncio.toString())

                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            appViewModel.cambiarAnuncioSeleccionado(anuncio)
                            appViewModel.obtenerImagenesAnuncioSel(anuncio.idAnuncio)
                        }
                    },
                    onImagenesCargadas = { appNavController.navigate(AppScreens.DetalleAnuncio.screenName) }
                )
            }

            composable(route = AppScreens.DetalleAnuncio.screenName){
                DetalleAnuncio(
                    appViewModel  = appViewModel,
                    conectionUiState = conectionUiState,
                    onClickComprar = { idComprador, idAnuncio, tipoAnuncio ->
                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            appViewModel.obtenerPDFAcuerdo(idComprador, idAnuncio, tipoAnuncio)
                        }
                    },
                    goToCompraComprador = { appNavController.navigate(AppScreens.CompraComprador.screenName) },
                    goToReporte = { appNavController.navigate(AppScreens.ReportarUsuario.screenName) }
                )
            }

            composable(route = AppScreens.ReportarUsuario.screenName){
                ReporteScreen(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    goToHome = {
                        appNavController.navigate(AppScreens.Home.screenName)
                    }
                )
            }

            composable(route = AppScreens.ConfUsu.screenName){
                ConfUsuario(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    filtrosViewModel = filtrosViewModel,
                    appNavController = appNavController,
                    onClickCerrarSesion = {
                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            appViewModel.cerrarSesion(conectionUiState.usuario!!.idUsuario)
                        }
                        conectionViewModel.cerrarSesion()
                        navController.navigate(WheelTraderScreens.Login.screenName)

                    }
                )
            }

            composable(route = AppScreens.Notificaciones.screenName){
                NotificacionesScreen(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    goToCompraVendedor = {
                        appNavController.navigate(AppScreens.CompraVendedor.screenName)
                    },
                    goToPayPalScreen = {
                        appNavController.navigate(AppScreens.ConfirmarPagoPayPal.screenName)
                    }
                )
            }

            composable(route = AppScreens.ListaTiposPublicar.screenName){
                ListaTiposVehiculos(
                    appViewModel = appViewModel,
                    appNavController = appNavController
                )
            }

            composable(route = AppScreens.PublicarCoche.screenName){
                PublicarCoche(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarMoto.screenName){
                PublicarMoto(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarCamioneta.screenName){
                PublicarCamioneta(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarCamion.screenName){
                PublicarCamion(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarMaquinaria.screenName){
                PublicarMaquinaria(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.CompraComprador.screenName) {
                CompraCompradorScreen(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    onOfrecer = {
                        appNavController.navigate(AppScreens.Home.screenName)
                    }
                )
            }

            composable(route = AppScreens.CompraVendedor.screenName){
                CompraVendedorScreen(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    onButtonClicked = {
                        appNavController.navigate(AppScreens.Home.screenName)
                    }
                )
            }

            composable(route = AppScreens.ConfirmarPagoPayPal.screenName){
                PagoPayPalScreen(
                    appViewModel = appViewModel,
                    goToHome = {
                        appNavController.navigate(AppScreens.Home.screenName)
                    }
                )
            }

            composable(route = AppScreens.MisCompras.screenName){
                MisComprasScreen(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    filtrosUiState = filtrosUiState
                )
            }
        }
    }
}