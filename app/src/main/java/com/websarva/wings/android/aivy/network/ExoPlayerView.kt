package com.websarva.wings.android.aivy.network

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun ExoPlayerView(streamUrl: String) {
    val context = LocalContext.current

    // ExoPlayerインスタンスをComposeのライフサイクルで管理
    val player = ExoPlayer.Builder(context).build().apply {
        val dataSourceFactory = RtmpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(streamUrl)
        setMediaSource(DefaultMediaSourceFactory(dataSourceFactory).createMediaSource(mediaItem))
        prepare()
        playWhenReady = true // 自動再生
    }

    // 映像が来ていないときに白背景を設定する
    var isVideoAvailable = remember { mutableStateOf(false) }

    // Playerのリスナーを設定して状態を監視
    player.addListener(object : Player.Listener {
        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()
            isVideoAvailable.value = true // 映像がレンダリングされたときに状態を更新
        }
    })

    // DisposableEffectでリリース処理を管理
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    // AndroidViewでPlayerViewをComposeに組み込む
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 映像が来るまで白い背景を表示
        if (!isVideoAvailable.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
        }

        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    this.player = player
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

