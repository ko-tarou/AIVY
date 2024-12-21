package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                // Language Icon Button with Dropdown
                var isLanguageMenuExpanded by remember { mutableStateOf(false) }
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
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text("English") },
                        onClick = {
                            currentLanguage = "English" // 言語設定を変更
                            isLanguageMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text("Japanese") },
                        onClick = {
                            currentLanguage = "Japanese" // 言語設定を変更
                            isLanguageMenuExpanded = false
                        }
                    )
                }

                // MoreHoriz Icon Button with Dropdown
                var isMoreMenuExpanded by remember { mutableStateOf(false) }
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
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text("Account") },
                        onClick = {
                            // Handle Account option
                            isMoreMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text("Create Vtuber") },
                        onClick = {
                            navController.navigate("selectpage")
                            isMoreMenuExpanded = false
                        }
                    )
                }
            }
        )
    }
}
