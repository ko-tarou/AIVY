package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SelectPage(navController: NavController){
    Column {
        Button(
            onClick = {navController.navigate("viewpage")}
        ) {
            Text("go to view")
        }
    }
}