package com.pourush.hotelaradhya

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pourush.hotelaradhya.ui.theme.HotelAradhyaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HotelAradhyaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        // WebView with weight
                        HotelWebView(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )

                        // Footer text
                        AppFooter()
                    }
                }
            }
        }
    }
}

@Composable
fun HotelWebView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        AndroidView(
            factory = {
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading.value = false
                        }
                    }
                    settings.javaScriptEnabled = true
                    loadUrl("https://hotelaradhya.co.in/")
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading.value) {
            // Splash image
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.halogo),
                    contentDescription = "Splash Logo"
                )
            }
        }
    }
}

@Composable
fun AppFooter() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://linktr.ee/pourush"))
                context.startActivity(intent)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Source: hotelaradhya.co.in © Hotel Aradhya •  \nMobile App interface by Pourush Pandey",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
