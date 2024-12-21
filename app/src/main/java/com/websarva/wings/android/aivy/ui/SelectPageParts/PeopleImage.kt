package com.websarva.wings.android.aivy.ui.SelectPageParts

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.websarva.wings.android.aivy.ui.theme.SelectColor

@Composable
fun PeopleImage(
    image: ImageBitmap,
    isSelected: Boolean, // 選択状態
    modifier: Modifier = Modifier,
    baseSize: Dp = 100.dp, // 基本サイズ
    selectedSize: Dp = 120.dp, // 選択時のサイズ
    longPressedSize: Dp = 120.dp, // 長押し時のサイズ
    shadowElevation: Dp = 5.dp, // 影の高さ
    borderColor: Color = Color.DarkGray, // 枠線の色
    backgroundColor: Color = Color.White, // 背景色
    onClick: () -> Unit // タップされたときの処理
) {
    // 長押し状態を保持
    val isLongPressed = remember { mutableStateOf(false) }

    // アニメーション付きサイズ変更
    val size by animateDpAsState(
        targetValue = when {
//            isLongPressed.value -> longPressedSize // 長押し状態
            isSelected -> selectedSize // 選択状態
            else -> baseSize // 通常状態
        },
        animationSpec = tween(durationMillis = 300) // アニメーション速度
    )

    // 枠線の幅（選択時のみ表示）
    val borderWidth by animateDpAsState(
        targetValue = if (isSelected) 3.5.dp else 0.dp
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .padding(10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() }, // タップ処理
                    onLongPress = { isLongPressed.value = true }, // 長押し時
                    onPress = {
                        // 指を離したら長押しを解除
                        tryAwaitRelease()
                        isLongPressed.value = false
                    }
                )
            }
    ) {
        Image(
            bitmap = image,
            contentDescription = "Displayed Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .shadow(
                    elevation = shadowElevation,
                    shape = CircleShape,
                    ambientColor = Color.DarkGray,
                    spotColor = Color.Black
                )
                .clip(CircleShape)
                .border(borderWidth, borderColor, shape = CircleShape) // 枠線を表示
                .background(backgroundColor)
        )
    }
}
