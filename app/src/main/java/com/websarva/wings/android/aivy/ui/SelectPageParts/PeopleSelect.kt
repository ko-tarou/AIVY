package com.websarva.wings.android.aivy.ui.SelectPageParts

import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import com.google.ai.client.generativeai.type.content
import com.websarva.wings.android.aivy.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.aivy.ui.theme.DatailsColor


@Composable
fun PeopleSelect(){

    val context = LocalContext.current
    val girl = BitmapFactory.decodeResource(context.resources, R.drawable.girl)
    val lady = BitmapFactory.decodeResource(context.resources, R.drawable.lady)
    val lady2= BitmapFactory.decodeResource(context.resources, R.drawable.lady2)
    val boy = BitmapFactory.decodeResource(context.resources, R.drawable.boy)
    val gentle = BitmapFactory.decodeResource(context.resources, R.drawable.gentle)
    val gentle2 = BitmapFactory.decodeResource(context.resources, R.drawable.gentle2)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        var selectedTab by remember { mutableStateOf(0) }

        AnimatedToggleTabLayout(
            selectedTab = selectedTab,
            onTabSelected = { newTab -> selectedTab = newTab }
        )

        if(selectedTab == 0){
            Row {
                PeopleImage(image = girl.asImageBitmap())
                PeopleImage(image = lady.asImageBitmap())
                PeopleImage(image = lady2.asImageBitmap())
            }
        }else {
            Row {
                PeopleImage(image = boy.asImageBitmap())
                PeopleImage(image = gentle.asImageBitmap())
                PeopleImage(image = gentle2.asImageBitmap())
            }
        }
    }
}


@Composable
fun AnimatedToggleTabLayout(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabItems = listOf("女性", "男性")

    // タブの1つの幅を計算
    val tabWidthPx = remember { mutableStateOf(0f) } // Px単位で保持
    val density = LocalDensity.current // Density を取得

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // トグルの背景をアニメーションで移動
        val offsetX by animateDpAsState(
            targetValue = with(density) { (selectedTab * tabWidthPx.value).toDp() } // PxからDpに変換してアニメーション
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(with(density) { tabWidthPx.value.toDp() }) // タブ幅をPxからDpに変換
                .offset(x = offsetX) // アニメーションで移動
                .clip(RoundedCornerShape(20.dp)) // 丸みを追加
                .border(3.dp, DatailsColor, RoundedCornerShape(20.dp)) // 枠線
        )

        // タブのテキスト部分
        Row(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    // 全体の幅から各タブの幅を計算（Px単位）
                    tabWidthPx.value = coordinates.size.width.toFloat() / tabItems.size
                },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabItems.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f) // 均等配置
                        .clickable (
                            onClick = { onTabSelected(index)},
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp), // 内側の余白
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        color = if (index == selectedTab) DatailsColor else Color.Gray, // 選択時と非選択時の色
                        fontWeight = if (index == selectedTab) FontWeight.Bold else FontWeight.Normal // 選択時は太字
                    )
                }
            }
        }
    }
}
