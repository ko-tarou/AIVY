package com.websarva.wings.android.aivy.ui.ViewPageParts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.websarva.wings.android.aivy.network.ExoPlayerView

@Composable
fun Video() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ExoPlayerView("rtmp://192.168.0.7/live/test")
    }
}