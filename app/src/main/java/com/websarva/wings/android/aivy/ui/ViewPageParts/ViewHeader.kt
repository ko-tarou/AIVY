package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.websarva.wings.android.aivy.ui.common.HeaderCommon
import com.websarva.wings.android.aivy.ui.theme.DatailsColor

// グローバル変数として言語設定を保持
var currentLanguage by mutableStateOf("English")

@Composable
fun ViewHeader(navController: NavController) {
    Column {
        HeaderCommon(
            title = "",
            actions = {
                var isLanguageMenuExpanded by remember { mutableStateOf(false) }
                var isMoreMenuExpanded by remember { mutableStateOf(false) }

                // Language Icon Button with Dropdown
                IconButton(
                    onClick = { isLanguageMenuExpanded = !isLanguageMenuExpanded },
                    modifier = Modifier.offset(y = -10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Language,
                        contentDescription = "Language Icon"
                    )
                }
                CustomDropdownMenu(
                    expanded = isLanguageMenuExpanded,
                    onDismissRequest = { isLanguageMenuExpanded = false },
                    items = if (currentLanguage == "English") {
                        listOf("English" to "English", "Japanese" to "Japanese")
                    } else {
                        listOf("日本語" to "Japanese", "英語" to "English")
                    },
                    onItemClick = { label, lang ->
                        currentLanguage = lang
                        isLanguageMenuExpanded = false
                    }
                )

                // MoreHoriz Icon Button with Dropdown
                IconButton(
                    onClick = { isMoreMenuExpanded = !isMoreMenuExpanded },
                    modifier = Modifier.offset(y = -10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "MoreHoriz Icon"
                    )
                }
                CustomDropdownMenu(
                    expanded = isMoreMenuExpanded,
                    onDismissRequest = { isMoreMenuExpanded = false },
                    items = if (currentLanguage == "English") {
                        listOf(
                            "Account" to "accountpage",
                            "Create Vtuber" to "selectpage",
                            "View Vtuber" to "viewpage"
                        )
                    } else {
                        listOf(
                            "アカウント" to "accountpage",
                            "Vtuber作成" to "selectpage",
                            "Vtuber閲覧" to "viewpage"
                        )
                    },
                    onItemClick = { label, route ->
                        navController.navigate(route)
                        isMoreMenuExpanded = false
                    }
                )
            }
        )
    }
}

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<Pair<String, String>>,
    onItemClick: (label: String, value: String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.5f)) // 全体の背景を透明に
            .padding(0.dp) // パディングを完全に排除
    ) {
        // カスタム背景をColumnに設定
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp) // 上下の余白を排除
        ) {
            items.forEachIndexed { index, (label, value) ->
                CustomDropdownMenuItem(
                    text = label,
                    onClick = { onItemClick(label, value) },
                    modifier = Modifier.fillMaxWidth()
                )
                // 最後のアイテム以外に線を追加
                if (index < items.size - 1) {
                    Divider(
                        color = Color.White, // 線の色を白に設定
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomDropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White // テキスト色を白に設定
                )
            )
        },
        onClick = onClick,
        modifier = modifier
            .background(Color.Transparent) // メニューアイテム自体の背景を透明に
            .padding(horizontal = 8.dp, vertical = 4.dp) // パディングを設定
    )
}

