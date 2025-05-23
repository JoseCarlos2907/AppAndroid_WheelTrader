package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun PagoPayPalScreen(
    appViewModel: AppViewModel,
    goToHome: () -> Unit,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()

    var hiloPeticiones by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        appViewModel.viewModelScope.launch(Dispatchers.IO) {
            hiloPeticiones = appViewModel.preguntarEstadoPago()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            hiloPeticiones?.cancel()
            appViewModel.reiniciarNotificacion()
        }
    }

    LaunchedEffect(appUiState.confirmaPago) {
        if(appUiState.confirmaPago){
            appViewModel.reiniciaConfirmaPago()
            goToHome()
        }
    }

    if(appUiState.urlPayPal == null){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Página no\ndisponible"
            )
        }
    }else{
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { context -> WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        url: String
                    ): Boolean {
                        if(url.contains("paypal.com", ignoreCase = true)){
                            view.loadUrl(url)
                            return true
                        }else{
                            view.loadUrl(url)
                        }

                        return false
                    }
                }

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                    setSupportMultipleWindows(true)
                }

                loadUrl(appUiState.urlPayPal!!)
            }}
        )
    }
}