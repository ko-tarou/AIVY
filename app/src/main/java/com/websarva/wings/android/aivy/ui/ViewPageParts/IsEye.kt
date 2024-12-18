package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IsEye(){
    var isEyeState by remember { mutableStateOf(true) }
    Column{
        Button(
            onClick = {isEyeState = !isEyeState},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .padding(16.dp)
        ) {
            if (isEyeState){
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = "Visibility Icon",
                    tint = Color.Black,
                )
            }else{
                Icon(
                    imageVector = Icons.Filled.VisibilityOff,
                    contentDescription = "VisibilityOff Icon",
                    tint = Color.Black,
                )
            }
        }
    }
}