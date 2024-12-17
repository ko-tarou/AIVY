package com.websarva.wings.android.aivy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.websarva.wings.android.aivy.ui.SelectPage
import com.websarva.wings.android.aivy.ui.ViewPage
import com.websarva.wings.android.aivy.ui.theme.AIVYTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIVYTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "viewpage",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("selectpage") {
                            SelectPage(navController)
                        }
                        composable("viewpage") {
                            ViewPage(navController)
                        }
                    }
                }
            }
        }
    }
}