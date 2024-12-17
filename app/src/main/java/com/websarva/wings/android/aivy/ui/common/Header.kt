package com.websarva.wings.android.aivy.ui.common

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(title: String) {

    val systemUniController = rememberSystemUiController()

    SideEffect {
        systemUniController.setStatusBarColor(Color.Black)
        systemUniController.setNavigationBarColor(Color.Black)
    }

    TopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor =  Color.White
        )
    )
}