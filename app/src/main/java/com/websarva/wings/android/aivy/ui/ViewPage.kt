package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.websarva.wings.android.aivy.ui.common.Header

@Composable
fun ViewPage(navController:NavController){
    Column {
        Header("View Page")
        Button(
            onClick = {navController.navigate("selectpage")}
        ) {
            Text("go to select")
        }
    }
}