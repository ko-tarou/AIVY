package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.websarva.wings.android.aivy.ui.common.Header

@Composable
fun ViewPage(navController:NavController){
    Column {
        Header(
            title = "View Page",
            actions = {
                IconButton(
                    onClick = {navController.navigate("selectpage")},
                    modifier = Modifier.offset(y = -20.dp)
                ) {
                    Text("go to select")
                }
            }
        )
    }
}