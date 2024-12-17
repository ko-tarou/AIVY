package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.websarva.wings.android.aivy.ui.ViewPageParts.Mail
import com.websarva.wings.android.aivy.ui.ViewPageParts.Present
import com.websarva.wings.android.aivy.ui.ViewPageParts.ViewHeader
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.websarva.wings.android.aivy.ui.ViewPageParts.Add


@Composable
fun ViewPage(navController:NavController){

    var isToolbarVisible by remember { mutableStateOf(false) }

    Column {
        ViewHeader(navController = navController)

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            //toolbar
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                if(isToolbarVisible) {
                    Mail()
                    Present()

                }else{
                    Add(
                        isToolbarVisible = isToolbarVisible,
                        onTool = {isToolbarVisible = !isToolbarVisible}
                    )
                }
            }
        }
    }
}