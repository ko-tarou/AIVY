package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.aivy.ui.viewpageparts.PresentScreen
import com.websarva.wings.android.aivy.ui.viewpageparts.Gift
import com.websarva.wings.android.aivy.ui.viewpageparts.getSampleGifts

@Composable
fun Present() {
    var isGiftVisible by remember { mutableStateOf(false) } // ギフト表示フラグ
    val gifts = getSampleGifts()
    var selectedGift by remember { mutableStateOf<Gift?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // 全体の余白を設定
        horizontalAlignment = Alignment.End, // コンテンツを右寄せ
        verticalArrangement = Arrangement.Bottom // コンテンツを下寄せ
    ) {
        // ギフト表示切り替えボタン
        Button(
            onClick = { isGiftVisible = !isGiftVisible },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CardGiftcard,
                contentDescription = "GiftCard Icon",
                tint = Color.Black
            )
        }

        // ギフト表示
        if (isGiftVisible) {
            PresentScreen(
                gifts = gifts,
                selectedGift = selectedGift,
                onGiftSelected = { selectedGift = it },
                onSendGift = { gift -> println("送信されたギフト: ${gift.name}") }
            )
        }
    }
}
