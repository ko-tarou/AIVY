package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IsEye() {
    var isEyeState by rememberSaveable { mutableStateOf(false) }


    // `currentLineCount` の値を読み取って Spacer の高さを計算
    val dynamicSpacerHeight = (((currentLineCount - 1) * 28) + 50).dp

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isEyeState) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .width(282.dp)
                        .padding(24.dp),
                ) {
                    // ログを非編集可能なテキストボックスに表示
                    BasicTextField(
                        value = buildString {
                            appendLine(log3 ?: "")
                            appendLine("")
                            appendLine(log2 ?: "")
                            appendLine("")
                            appendLine(log1 ?: "")
                        },
                        onValueChange = {}, // 入力変更は不可
                        readOnly = true, // ユーザー入力を無効化
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        decorationBox = { innerTextField ->
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.Black.copy(alpha = 0.3f))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                innerTextField()
                            }
                        },
                        modifier = Modifier
                            .heightIn(min = 50.dp, max = 400.dp)
                            .width(234.dp)
                    )

                    // Spacerの高さは`currentLineCount`に基づいて決定
                    Spacer(modifier = Modifier.height(dynamicSpacerHeight))
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = { isEyeState = !isEyeState },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(8.dp)
            ) {
                if (isEyeState) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Visibility Icon",
                        tint = Color.Black,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "VisibilityOff Icon",
                        tint = Color.Black,
                    )
                }
            }
        }
    }
}
