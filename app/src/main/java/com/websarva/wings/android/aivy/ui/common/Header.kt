package com.websarva.wings.android.aivy.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.websarva.wings.android.aivy.ui.theme.HeaderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {} // actions引数でボタンやアイコンを表示
) {
    val systemUiController = rememberSystemUiController()

    // ステータスバーとナビゲーションバーの色を変更
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(HeaderColor)
        systemUiController.setNavigationBarColor(HeaderColor)
    }

    // ヘッダー（TopAppBar）の実装
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier
                    .offset(y = -20.dp)
            ) // ヘッダーのタイトル
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HeaderColor, // ヘッダー背景色
            titleContentColor = Color.White // タイトル文字色
        ),
        actions = {
            actions()
        }, // ヘッダー右側に表示する要素
        modifier = Modifier.height(56.dp) // ヘッダーの高さ
    )
}