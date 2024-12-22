package com.websarva.wings.android.aivy.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.websarva.wings.android.aivy.R
import com.websarva.wings.android.aivy.ui.SelectPageParts.CreateButton
import com.websarva.wings.android.aivy.ui.SelectPageParts.DetailsInput
import com.websarva.wings.android.aivy.ui.SelectPageParts.PeopleSelect
import com.websarva.wings.android.aivy.ui.SelectPageParts.SelectHeader
import com.websarva.wings.android.aivy.ui.ViewPageParts.ViewHeader
import com.websarva.wings.android.aivy.ui.common.HeaderCommon
import com.websarva.wings.android.aivy.ui.theme.DatailsColor
import com.websarva.wings.android.aivy.ui.theme.HeaderColor
import com.websarva.wings.android.aivy.ui.theme.SelectColor
import kotlinx.coroutines.selects.select

@Composable
fun SelectPage(navController: NavController){

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column (
            verticalArrangement = Arrangement.Bottom
        ){
            Image(
                painterResource(id = R.drawable.okumono_sf30),
                contentDescription = "okumono_sf30",
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop,
            )
        }
        Column{
            ViewHeader(navController = navController)
            Column(
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 50.dp, start = 20.dp, end = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        PeopleSelect()
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Spacer(modifier = Modifier.padding(100.dp))

                        DetailsInput(navController = navController)
                    }
                }
            }
        }
    }
}