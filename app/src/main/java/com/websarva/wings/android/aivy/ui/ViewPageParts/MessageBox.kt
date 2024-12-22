package com.websarva.wings.android.aivy.ui.ViewPageParts

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlin.math.roundToInt

// グローバル変数
var log1 by mutableStateOf<String?>(null)
var log2 by mutableStateOf<String?>(null)
var log3 by mutableStateOf<String?>(null)
var currentLineCount by mutableStateOf(1)
var currentHeightPx by mutableStateOf(0f)

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
                    .onGloballyPositioned { coordinates ->
                        val heightPx = coordinates.size.height.toFloat() // 高さを取得
                        updateLineCountBasedOnHeightFromHeightPx(heightPx)
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

// 高さから行数を計算
fun updateLineCountBasedOnHeightFromHeightPx(heightPx: Float) {
    // 高さが変化していない場合は更新しない
    if (currentHeightPx == heightPx) return

    // 高さを更新
    currentHeightPx = heightPx

    // `(高さ - 88) / 80` で行数を計算し、1行以上に調整
    val newLineCount = ((heightPx - 88) / 80).roundToInt().coerceAtLeast(1)

    // 行数が変更されていない場合は更新しない
    if (currentLineCount != newLineCount) {
        Log.d(
            "LineCount",
            "行数が変更されました: $currentLineCount -> $newLineCount, 高さ: ${currentHeightPx}px"
        )
        currentLineCount = newLineCount
    }
}
