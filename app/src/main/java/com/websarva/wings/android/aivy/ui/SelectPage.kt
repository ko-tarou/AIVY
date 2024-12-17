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
import com.websarva.wings.android.aivy.ui.SelectPageParts.SelectHeader
import com.websarva.wings.android.aivy.ui.common.HeaderCommon

@Composable
fun SelectPage(navController: NavController){
    Column {
        SelectHeader(navController = navController)
    }
}