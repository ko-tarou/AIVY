package com.websarva.wings.android.aivy.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.websarva.wings.android.aivy.MainActivity
import com.websarva.wings.android.aivy.R
import com.websarva.wings.android.aivy.ui.components.OpenPageScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    private var isInitializationComplete = false
    private var isVideoComplete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = getColor(android.R.color.white)
        window.navigationBarColor = getColor(android.R.color.white)

        // スプラッシュ画面の表示
        setContent {
            OpenPageScreen(
                videoPath = "android.resource://$packageName/${R.raw.aivy_white}",
                onVideoEnd = { onVideoComplete() },
                onTap = { navigateToMainActivity() } // 動画終了を待たずにスキップ可能
            )
        }

        // 初期化処理を開始
        performInitialization()
    }

    private fun performInitialization() {
        lifecycleScope.launch {
            // シミュレーションとして2秒の遅延を追加（実際はリソースの読み込みを実行）
            delay(2000) // 必要な初期化処理をここで実行
            isInitializationComplete = true
            checkAndNavigate()
        }
    }

    private fun onVideoComplete() {
        isVideoComplete = true
        checkAndNavigate()
    }

    private fun checkAndNavigate() {
        if (isInitializationComplete && isVideoComplete) {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

}
