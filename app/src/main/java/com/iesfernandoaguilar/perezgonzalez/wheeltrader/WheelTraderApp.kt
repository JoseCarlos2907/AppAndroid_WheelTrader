package com.iesfernandoaguilar.perezgonzalez.wheeltrader

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.login.LoginScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme


enum class WheelTraderScreens(val screenName: String){
    Login(screenName = "login")
}


@Composable
fun wheeltraderApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            mainAppBar()
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = WheelTraderScreens.Login.screenName,
            modifier = modifier.padding(contentPadding)
        ){
            composable(route = WheelTraderScreens.Login.screenName){
                LoginScreen()
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