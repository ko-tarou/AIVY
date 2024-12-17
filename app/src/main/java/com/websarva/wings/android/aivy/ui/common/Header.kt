package com.websarva.wings.android.aivy.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.websarva.wings.android.aivy.ui.theme.HeaderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(title: String) {

    val systemUniController = rememberSystemUiController()

    SideEffect {
        systemUniController.setStatusBarColor(HeaderColor)
        systemUniController.setNavigationBarColor(HeaderColor)
    }

    TopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HeaderColor,
            titleContentColor =  Color.White
        ),
        modifier = Modifier.height(56.dp)
    )
}