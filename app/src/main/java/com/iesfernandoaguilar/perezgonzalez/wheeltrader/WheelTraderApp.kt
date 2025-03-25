package com.iesfernandoaguilar.perezgonzalez.wheeltrader

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.login.LoginScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.login.LoginViewModel
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.logoblanco),
                    contentDescription = null,
                    modifier = Modifier.height(54.dp),
                )
            }
        },
        navigationIcon = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
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