package com.websarva.wings.android.aivy.ui.components

import android.widget.VideoView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun OpenPageScreen(
    videoPath: String, // 動画のパスを外部から渡す
    onVideoEnd: () -> Unit, // 動画終了時のコールバック
    onTap: () -> Unit // 画面タップ時のコールバック
) {
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
            VideoBackground(
                videoPath = videoPath,
                onVideoEnd = onVideoEnd
            )

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
fun VideoBackground(
    videoPath: String, // 動画のパスを外部から渡す
    onVideoEnd: () -> Unit // 動画終了時のコールバック
) {
    AndroidView(
        factory = { context ->
            VideoView(context).apply {
                setVideoPath(videoPath) // 動画パスを設定
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