package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreens

@Composable
fun Reg2Screen(
    loginNavController: NavController,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
    ) {
        Text(
            text = "Registro 2"
        )

        Row {
            Button(
                onClick = {
                    loginNavController.navigate(LoginScreens.Reg1.screenName)
                }
            ) {
                Text(
                    text = "Anterior"
                )
            }

            Button(
                onClick = {
                    loginNavController.navigate(LoginScreens.Reg3.screenName)
                }
            ) {
                Text(
                    text = "Siguiente"
                )
            }
        }
    }
}