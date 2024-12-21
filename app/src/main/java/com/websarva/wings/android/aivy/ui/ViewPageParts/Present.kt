package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Present(){
    Column {
        Button(
            onClick = {/*TODO*/},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CardGiftcard,
                contentDescription = "GiftCard Icon",
                tint = Color.Black,
            )
        }
    }
}