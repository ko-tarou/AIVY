package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database

// グローバル変数としてログを保持
var log1 by mutableStateOf<String?>(null)
var log2 by mutableStateOf<String?>(null)
var log3 by mutableStateOf<String?>(null)

@Composable
fun MessageBox(
    onFocusChanged: (Boolean) -> Unit // フォーカス状態を通知するコールバック
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus() // 他の場所をタッチしたときにフォーカス解除
                        onFocusChanged(false) // フォーカス解除を通知
                    }
                )
            }
            .padding(start = 16.dp, bottom = 16.dp)
            .width(250.dp)
    ) {
        Row {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = TextStyle(
                    color = Color.DarkGray,
                    fontSize = 18.sp
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send // キーボードのボタンを送信に変更
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (text.isNotBlank()) { // 入力が空でないかチェック
                            Firebase.database.reference.child("message").setValue(text)
                            updateLogs(text) // ログを更新
                            text = "" // 入力フィールドをクリア
                            focusManager.clearFocus() // キーボードを閉じる
                            onFocusChanged(false) // フォーカス解除を通知
                        }
                    }
                ),
                decorationBox = { innerTextField ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.LightGray.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .heightIn(min = 56.dp, max = 300.dp)
                    .width(250.dp)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused) // フォーカス状態を通知
                    }
            )
        }
    }
}

// ログを更新する関数
fun updateLogs(newMessage: String) {
    log3 = log2 // 古いログを1つずらす
    log2 = log1
    log1 = newMessage // 新しいメッセージを最新ログに
}

