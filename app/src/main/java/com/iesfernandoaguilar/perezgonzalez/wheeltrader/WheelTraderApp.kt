package com.iesfernandoaguilar.perezgonzalez.wheeltrader

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.util.Properties


enum class WheelTraderScreens(val screenName: String){
    Login(screenName = "login"),
    App(screenName = "app")
}


@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun wheeltraderApp(
    conectionViewModel: ConectionViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
){
    val properties = Properties()
    val assetManager = context.assets

    properties.load(InputStreamReader(assetManager.open("conf.properties")))

    /*LaunchedEffect(Unit) {
        withContext(Dispatchers.IO){
            conectionViewModel.conectar(properties.getProperty("ADDRESS"), Integer.parseInt(properties.getProperty("PORT")))
        }
    }*/

    conectionViewModel.viewModelScope.launch(Dispatchers.IO) {
        conectionViewModel.conectar(properties.getProperty("ADDRESS"), Integer.parseInt(properties.getProperty("PORT")))
    }

    NavHost(
        navController = navController,
        startDestination = WheelTraderScreens.Login.screenName,
        modifier = modifier
    ){
        composable(route = WheelTraderScreens.Login.screenName){
            LoginScreen(
                context = context,
                conectionViewModel = conectionViewModel,
                navController = navController
            )
        }

        composable(route = WheelTraderScreens.App.screenName) {
            AppScreen(
                context = context,
                conectionViewModel = conectionViewModel,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainAppBar(
    onBackButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.logoazul),
                    contentDescription = null,
                    modifier = Modifier.height(54.dp),
                )
            }
        },
        navigationIcon = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun mainBottomBar(
    modifier: Modifier = Modifier
){
    val opciones = listOf("Conf. Usuario", "Home", "Notificaciones", "Publicar Anuncio")
    val iconos = listOf(R.drawable.iconoconfusuarioazul, R.drawable.iconohomeazul, R.drawable.icononotificacionazul, R.drawable.iconoaniadirazul)

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.height(100.dp)
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            opciones.forEach{item->
                NavigationBarItem(
                    selected = false,
                    onClick = {

                    },
                    icon = {
                        Image(
                            painter = painterResource(iconos.get(opciones.indexOf(item))),
                            contentDescription = "",
                            modifier = Modifier.height(30.dp).width(30.dp)
                        )
                    },
                    modifier = Modifier.width(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewBottomBar() {
    WheelTraderTheme {
        mainBottomBar()
    }
}