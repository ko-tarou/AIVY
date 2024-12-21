package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
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
                DropdownMenu(
                    expanded = isLanguageMenuExpanded,
                    onDismissRequest = { isLanguageMenuExpanded = false }
                ) {
                    val languages = if (currentLanguage == "English") {
                        listOf("English" to "English", "Japanese" to "Japanese")
                    } else {
                        listOf("日本語" to "Japanese", "英語" to "English")
                    }

                    languages.forEach { (label, lang) ->
                        CustomDropdownMenuItem(
                            text = label,
                            onClick = {
                                currentLanguage = lang
                                isLanguageMenuExpanded = false
                            }
                        )
                    }
                }

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
                DropdownMenu(
                    expanded = isMoreMenuExpanded,
                    onDismissRequest = { isMoreMenuExpanded = false }
                ) {
                    val menuItems = if (currentLanguage == "English") {
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
                    }

                    menuItems.forEach { (label, route) ->
                        CustomDropdownMenuItem(
                            text = label,
                            onClick = {
                                navController.navigate(route)
                                isMoreMenuExpanded = false
                            }
                        )
                    }
                }
            }
        )
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
                    color = Color.Black // テキスト色
                )
            )
        },
        onClick = onClick,
        modifier = modifier
            .background(Color.LightGray) // 背景色
            .padding(horizontal = 8.dp, vertical = 4.dp) // パディング
    )
}

