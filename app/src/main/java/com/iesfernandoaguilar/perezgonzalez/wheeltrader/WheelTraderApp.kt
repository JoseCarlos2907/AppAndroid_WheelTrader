package com.iesfernandoaguilar.perezgonzalez.wheeltrader

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.util.Properties


enum class WheelTraderScreens(val screenName: String){
    Login(screenName = "login"),
    Home(screenName = "home")
}


@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun wheeltraderApp(
    conectionViewModel: ConectionViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            mainAppBar()
        }
    ) { contentPadding ->

        val properties = Properties()
        val assetManager = context.assets

        properties.load(InputStreamReader(assetManager.open("conf.properties")))

        conectionViewModel.viewModelScope.launch(Dispatchers.IO) {
            conectionViewModel.conectar(properties.getProperty("ADDRESS"), Integer.parseInt(properties.getProperty("PORT")))
        }

        NavHost(
            navController = navController,
            startDestination = WheelTraderScreens.Login.screenName,
            modifier = modifier.padding(contentPadding)
        ){
            composable(route = WheelTraderScreens.Login.screenName){
                LoginScreen(
                    context = context,
                    conectionViewModel = conectionViewModel,
                    navController = navController
                )
            }

            composable(route = WheelTraderScreens.Home.screenName) {
                HomeScreen(
                    conectionViewModel = conectionViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun mainAppBar(
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

@Preview(showBackground = true)
@Composable
fun previewTopBar() {
    WheelTraderTheme {
        mainAppBar()
    }
}