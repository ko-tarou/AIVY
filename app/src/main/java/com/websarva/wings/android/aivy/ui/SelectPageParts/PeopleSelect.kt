package com.websarva.wings.android.aivy.ui.SelectPageParts

import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import com.google.ai.client.generativeai.type.content
import com.websarva.wings.android.aivy.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.websarva.wings.android.aivy.ui.theme.DatailsColor

@Composable
fun PeopleSelect(){

    val context = LocalContext.current
    val girl = remember { BitmapFactory.decodeResource(context.resources, R.drawable.girl).asImageBitmap() }
    val lady = remember { BitmapFactory.decodeResource(context.resources, R.drawable.lady).asImageBitmap() }
    val lady2 = remember { BitmapFactory.decodeResource(context.resources, R.drawable.lady2).asImageBitmap() }
    val boy = remember { BitmapFactory.decodeResource(context.resources, R.drawable.boy).asImageBitmap() }
    val gentle = remember { BitmapFactory.decodeResource(context.resources, R.drawable.gentle).asImageBitmap() }
    val gentle2 = remember { BitmapFactory.decodeResource(context.resources, R.drawable.gentle2).asImageBitmap() }
    var selectpeople by remember { mutableStateOf(1) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        var selectedTab by remember { mutableStateOf(0) }

        AnimatedToggleTabLayout(
            selectedTab = selectedTab,
            onTabSelected = { newTab -> selectedTab = newTab }
        )

        Spacer(modifier = Modifier.padding(10.dp))

        if (selectedTab == 0) {
            Row {

                PeopleImage(
                    image = girl,
                    isSelected = selectpeople == 1,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                selectpeople = 1
                                Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    onClick = {
                        selectpeople = 1
                        Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                    },
                )
                PeopleImage(
                    image = lady,
                    isSelected = selectpeople == 2,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                selectpeople = 2
                                Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    onClick = {
                        selectpeople = 2
                        Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                    },
                )
                PeopleImage(
                    image = lady2,
                    isSelected = selectpeople == 3,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                selectpeople = 3
                                Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    onClick = {
                        selectpeople = 3
                        Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                    },
                )
            }
        } else {
            Row {
                PeopleImage(
                    image = boy,
                    isSelected = selectpeople == 4,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                selectpeople = 4
                                Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    onClick = {
                        selectpeople = 4
                        Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                    },
                )
                PeopleImage(
                    image = gentle,
                    isSelected = selectpeople == 5,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                selectpeople = 5
                                Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    onClick = {
                        selectpeople = 5
                        Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                    },
                )
                PeopleImage(
                    image = gentle2,
                    isSelected = selectpeople == 6,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                selectpeople = 6
                                Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    onClick = {
                        selectpeople = 6
                        Firebase.database.reference.child("selectpeople").setValue(selectpeople)
                    },
                )
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
//                .background(Color.White.copy(alpha = 0.5f)), // 背景色
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
                        color = if (index == selectedTab) DatailsColor else DatailsColor, // 選択時と非選択時の色
                        fontWeight = if (index == selectedTab) FontWeight.Bold else FontWeight.Normal // 選択時は太字
                    )
                }
            }
        }
    }
}
