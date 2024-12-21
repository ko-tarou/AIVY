package com.websarva.wings.android.aivy.ui.viewpageparts


import android.graphics.drawable.AnimatedImageDrawable
import android.provider.ContactsContract.Data
import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.websarva.wings.android.aivy.R
import com.websarva.wings.android.aivy.ui.theme.DatailsColor
import com.websarva.wings.android.aivy.ui.theme.HeaderColor

// ギフトデータクラス
data class Gift(
    val id: Int,
    val name: String,
    val price: Int,
    val imageRes: Int,
    val description: String
)

// メイン画面のComposable関数
@Composable
fun PresentScreen(
    gifts: List<Gift>,
    selectedGift: Gift?,
    onGiftSelected: (Gift) -> Unit,
    onSendGift: (Gift) -> Unit,
    screenHeight: Dp = 300.dp // 高さを柔軟に設定
) {
    var isDialogVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .height(screenHeight)
            .border(3.dp, DatailsColor, RoundedCornerShape(20.dp)),
        color = HeaderColor.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom, // 下寄せ
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ギフト一覧
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // ギフト一覧の高さ
                contentAlignment = Alignment.Center
            ) {
                GiftRow(gifts = gifts, selectedGift = selectedGift, onGiftClick = onGiftSelected)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 送信ボタン
            Button(
                onClick = {
                    selectedGift?.let { onSendGift(it) }
                    isDialogVisible = true
                },
                enabled = selectedGift != null,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGift != null) DatailsColor.copy(alpha = 0.4f) else Color.Gray
                )
            ) {
                Text(
                    text = selectedGift?.let { "¥${it.price} で送信" } ?: "ギフトを選択してください",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // 送信完了ダイアログ
            if (isDialogVisible) {
                AlertDialog(
                    onDismissRequest = { isDialogVisible = false },
                    title = { Text(text = "送信完了") },
                    text = { Text(text = "ギフトを送信しました！") },
                    confirmButton = {
                        TextButton(onClick = { isDialogVisible = false }) {
                            Text("閉じる")
                        }
                    }
                )
            }
        }
    }
}

// ギフトを横に並べる行
@Composable
fun GiftRow(gifts: List<Gift>, selectedGift: Gift?, onGiftClick: (Gift) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly // 均等配置
    ) {
        gifts.forEach { gift ->
            GiftItem(
                gift = gift,
                isSelected = selectedGift == gift,
                onClick = { onGiftClick(gift) }
            )
        }
    }
}

// ギフトのアイテム表示
@Composable
fun GiftItem(gift: Gift, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .width(50.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            border = BorderStroke(if(isSelected) 2.5.dp else 1.dp, if (isSelected) DatailsColor else Color.Gray.copy(alpha = 0.5f)
            )
        ) {
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        val drawable = context.getDrawable(gift.imageRes)
                        if (drawable is AnimatedImageDrawable) {
                            setImageDrawable(drawable)
                            drawable.start()
                        } else {
                            setImageDrawable(drawable)
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = "¥${gift.price}",
            fontSize = 12.sp,
            color = if (isSelected) DatailsColor.copy(alpha = 0.4f) else Color.Gray
        )
    }
}

// サンプルデータ
fun getSampleGifts(): List<Gift> {
    return listOf(
        Gift(1, "Santa", 10000, R.drawable.gift1_santa, "笑顔のギフトで感謝を伝えましょう！"),
        Gift(2, "Penlight", 5000, R.drawable.gift2_penraito, "楽しい瞬間を次へ繋ぐギフト。"),
        Gift(3, "Dinosaur", 3000, R.drawable.gift3_ebifurai, "可愛い恐竜ギフトで驚きを届けます。"),
        Gift(4, "Chick", 1000, R.drawable.gift4_hiyoko, "カラフルな花のギフトで明るさを！"),
        Gift(5, "Robot", 500, R.drawable.gift5_kaitensurutori, "未来感あふれるロボットのギフト。"),
        Gift(6, "UFO", 100, R.drawable.gift6_hiyoko, "スペシャルなUFOギフトで特別な瞬間を。")
    )
}
