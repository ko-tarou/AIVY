package com.websarva.wings.android.aivy.network

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun ExoPlayerView(streamUrl: String) {
    // ExoPlayerインスタンスをComposeのライフサイクルで管理
    val player = ExoPlayer.Builder(LocalContext.current).build().apply {
        val dataSourceFactory = RtmpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(streamUrl)
        setMediaSource(DefaultMediaSourceFactory(dataSourceFactory).createMediaSource(mediaItem))
        prepare()
        playWhenReady = true // 自動再生
    }

    // DisposableEffectでリリース処理を管理
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    // AndroidViewでPlayerViewをComposeに組み込む
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(Color.Black.toArgb()) // 背景色を設定
                this.player = player
            }
        },
        modifier = Modifier
            .fillMaxSize()
    )
}
