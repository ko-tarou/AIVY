package com.websarva.wings.android.aivy.ui.viewpageparts

import android.graphics.drawable.AnimatedImageDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.websarva.wings.android.aivy.R

// ギフトデータクラス
data class Gift(
    val id: Int,
    val name: String,
    val price: Int,
    val imageRes: Int, // DrawableのリソースID
    val description: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PresentScreen()
        }
    }
}

// メイン画面のComposable関数
@Composable
fun PresentScreen() {
    val gifts = getSampleGifts()
    var selectedGift by remember { mutableStateOf<Gift?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ギフト一覧
            GiftRow(gifts = gifts, selectedGift = selectedGift, onGiftClick = { selectedGift = it })

            Spacer(modifier = Modifier.height(16.dp))

            // 送信ボタン
            Button(
                onClick = { isDialogVisible = true },
                enabled = selectedGift != null,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGift != null) Color.Blue else Color.Gray
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
            .padding(horizontal = 8.dp),
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
            .width(50.dp) // 各アイテムの幅を調整
            .clickable(onClick = onClick), // クリック可能にする
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(50.dp), // 画像のサイズを調整
            shape = RoundedCornerShape(8.dp),
            color = if (isSelected) Color(0xFFE0FFE0) else Color.LightGray
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
            color = if (isSelected) Color.Blue else Color.Gray
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

// プレビュー用
@Preview(showBackground = true)
@Composable
fun PresentScreenPreview() {
    PresentScreen()
}
