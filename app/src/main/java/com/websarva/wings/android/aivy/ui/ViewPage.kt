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
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.websarva.wings.android.aivy.ui.ViewPageParts.Add
import com.websarva.wings.android.aivy.ui.ViewPageParts.IsEye
import com.websarva.wings.android.aivy.ui.ViewPageParts.MessageBox
import com.websarva.wings.android.aivy.ui.ViewPageParts.Remove
import com.websarva.wings.android.aivy.ui.ViewPageParts.Toolbar


@Composable
fun ViewPage(navController:NavController){

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
                Toolbar()
            }

            //message
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ){
                MessageBox()
            }

            //isEye
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top,
            ){
                IsEye()
            }

            Column {
                Button(
                    onClick = {
                        // Firebase に値を書き込む
                        Firebase.database.reference.child("button").setValue(true)
                    }
                ) {
                    Text("値を true に設定")
                }
            }



        }
    }
}