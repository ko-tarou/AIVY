package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.websarva.wings.android.aivy.ui.common.Header

@Composable
fun SelectPage(navController: NavController){
    Column {
        Header(
            title = "",
            actions = {
                IconButton(
                    onClick = {navController.navigate("viewpage")},
                    modifier = Modifier.offset(y = -10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Language,
                        contentDescription = "Language Icon"
                    )
                }
                IconButton(
                    onClick = {navController.navigate("viewpage")},
                    modifier = Modifier.offset(y = -10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "MoreHoriz Icon"
                    )
                }
            }
        )
    }
}