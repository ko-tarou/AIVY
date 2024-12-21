package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.websarva.wings.android.aivy.ui.SelectPageParts.CreateButton
import com.websarva.wings.android.aivy.ui.SelectPageParts.DetailsInput
import com.websarva.wings.android.aivy.ui.SelectPageParts.PeopleSelect
import com.websarva.wings.android.aivy.ui.SelectPageParts.SelectHeader
import com.websarva.wings.android.aivy.ui.common.HeaderCommon
import com.websarva.wings.android.aivy.ui.theme.DatailsColor
import com.websarva.wings.android.aivy.ui.theme.HeaderColor

@Composable
fun SelectPage(navController: NavController){
    Column {
        SelectHeader(navController = navController)

        Column (
            modifier = Modifier
                .padding(top = 50.dp, bottom = 50.dp, start = 20.dp, end = 20.dp)
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .border(3.dp, DatailsColor, RoundedCornerShape(20.dp))
                    .background(HeaderColor)
            ){
                PeopleSelect()
                DetailsInput(navController = navController)
            }
        }
    }
}