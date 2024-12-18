package com.websarva.wings.android.aivy.ui.SelectPageParts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp

@Composable
fun PeopleImage(
    image: ImageBitmap,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(100.dp)
            .padding(10.dp)
    ){
        Image(
            bitmap = image,
            contentDescription = "Displayed Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .border(5.dp, Color.DarkGray)
        )
    }
}