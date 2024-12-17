package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Toolbar() {
    Column {
        var isToolbarVisible by remember { mutableStateOf(false) }

        if(isToolbarVisible) {
            Mail()
            Present()
            Remove(
                isToolbarVisible = isToolbarVisible,
                onTool = {isToolbarVisible = !isToolbarVisible}
            )
        }else{
            Add(
                isToolbarVisible = isToolbarVisible,
                onTool = {isToolbarVisible = !isToolbarVisible}
            )
        }
    }
}