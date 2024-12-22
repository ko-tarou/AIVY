package com.websarva.wings.android.aivy.ui.components

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

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
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // 背景動画
            ExoPlayerBackground(
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
fun ExoPlayerBackground(
    videoPath: String, // 動画のパスを外部から渡す
    onVideoEnd: () -> Unit // 動画終了時のコールバック
) {
    val context = LocalContext.current

    // ExoPlayer を Compose のライフサイクルで管理
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoPath))
            prepare()
            playWhenReady = true
        }
    }

    // ExoPlayer にリスナーを追加して終了時の処理を設定
    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoEnd() // 動画終了時にコールバックを呼び出す
                }
            }
        }

        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // ExoPlayer のビデオビューを組み込む
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.WHITE)
                useController = false// 余白部分を白に設定
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Compose 側の背景も白に設定
    )
}
