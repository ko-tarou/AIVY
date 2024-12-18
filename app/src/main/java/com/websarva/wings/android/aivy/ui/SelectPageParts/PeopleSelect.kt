package com.websarva.wings.android.aivy.ui.SelectPageParts

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import com.google.ai.client.generativeai.type.content
import com.websarva.wings.android.aivy.R
import androidx.compose.ui.platform.LocalContext


@Composable
fun PeopleSelect(){

    val context = LocalContext.current
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.watermark)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Row {
            PeopleImage(image = bitmap.asImageBitmap())
            PeopleImage(image = bitmap.asImageBitmap())
            PeopleImage(image = bitmap.asImageBitmap())
        }
        Row {
            PeopleImage(image = bitmap.asImageBitmap())
            PeopleImage(image = bitmap.asImageBitmap())
            PeopleImage(image = bitmap.asImageBitmap())
        }
    }
}