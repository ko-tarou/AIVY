package com.websarva.wings.android.aivy.ui.SelectPageParts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.type.content
import com.websarva.wings.android.aivy.ui.theme.DatailsColor

@Composable
fun CreateButton() {
    Column {
        Button(
            onClick = {/*TODO*/},
            modifier = Modifier
                .border(1.dp, DatailsColor)
                .height(50.dp)
                .width(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black.copy(alpha = 0.15f),
                contentColor = Color.White,
            ),
            shape = RectangleShape,
            elevation = ButtonDefaults.buttonElevation(0.dp),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "作成",
                fontSize = 20.sp
            )
        }
    }
}