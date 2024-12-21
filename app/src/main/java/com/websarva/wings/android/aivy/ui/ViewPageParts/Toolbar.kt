package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//mutableStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager


@Composable
fun Toolbar(isInputFocused:Boolean) {
    Column {
//      var isToolbarVisible by remember { mutableStateOf(false) }

        if (!isInputFocused) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ){
//                Mail()
                Present()
            }
        }else{
            Column {
                Present()
            }
        }
//        if(isToolbarVisible) {
//            Mail()
//            Present()
//            Remove(
//                isToolbarVisible = isToolbarVisible,
//                onTool = {isToolbarVisible = !isToolbarVisible}
//            )
//        }else{
//            Add(
//                isToolbarVisible = isToolbarVisible,
//                onTool = {isToolbarVisible = !isToolbarVisible}
//            )
//        }
    }
}