package com.example.rtmpstreamplayer

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView

class NetWork : AppCompatActivity() {

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 動的にPlayerViewを作成
        val playerView = PlayerView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK) // 背景を黒に設定
        }

        // ルートのFrameLayoutを作成してPlayerViewを追加
        val rootLayout = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            addView(playerView)
        }

        // レイアウトをセット
        setContentView(rootLayout)

        // RTMPデータソースファクトリを作成
        val dataSourceFactory = RtmpDataSource.Factory()

        // ExoPlayerのインスタンスを作成（データソースファクトリを使用）
        player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory))
            .build()

        // PlayerViewにPlayerをセット
        playerView.player = player

        // RTMPストリームのURL
        val videoUrl = "rtmp://0.tcp.jp.ngrok.io:17324/live/teststream"

        // メディアアイテムを作成
        val mediaItem = MediaItem.fromUri(videoUrl)

        // ExoPlayerにメディアアイテムをセット
        player?.setMediaItem(mediaItem)

        // 再生準備
        player?.prepare()
        player?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        // プレイヤーを解放
        player?.release()
        player = null
    }
}
