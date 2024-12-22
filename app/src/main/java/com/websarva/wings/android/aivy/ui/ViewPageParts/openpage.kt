package com.websarva.wings.android.aivy.ui.openpage

import android.content.Intent
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.websarva.wings.android.aivy.MainActivity
import com.websarva.wings.android.aivy.R

class OpenPageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenPageScreen(
                onVideoEnd = { navigateToNextScreen() },
                onTap = { navigateToNextScreen() }
            )
        }
    }

    private fun navigateToNextScreen() {
        // 次の画面に遷移（例: MainActivity へ）
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // 現在の画面を終了
    }
}

@Composable
fun OpenPageScreen(onVideoEnd: () -> Unit, onTap: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onTap() }, // タップで次の画面へ遷移
        color = Color.Black
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 背景動画
            VideoBackground(onVideoEnd = onVideoEnd)

            // テキストを画面の下部に配置
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 48.dp) // 下部に余白を設定
                    .wrapContentHeight(Alignment.Bottom), // テキストを下部に揃える
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "AIVYでAIが創る新しい未来を楽しもう",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "革新的なストリーミング体験を、今ここで。",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun VideoBackground(onVideoEnd: () -> Unit) {
    AndroidView(
        factory = { context ->
            VideoView(context).apply {
                setVideoPath("android.resource://" + context.packageName + "/" + R.raw.aivy_white) // 動画ファイル名
                setOnPreparedListener { mp ->
                    mp.start() // 再生開始
                }
                setOnCompletionListener {
                    onVideoEnd() // 動画終了時にコールバック
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun OpenPagePreview() {
    OpenPageScreen(
        onVideoEnd = { /* 動画終了時の処理をプレビューでは実行しない */ },
        onTap = { /* タップ時の処理をプレビューでは実行しない */ }
    )
}
