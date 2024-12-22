package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.websarva.wings.android.aivy.R
import com.websarva.wings.android.aivy.ui.ViewPageParts.ViewHeader

@Composable
fun AccountPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ViewHeader(navController = navController)
        Profile()
    }
}

@Composable
fun Profile() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.LightGray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.watermark),
            contentDescription = "プロフィール画像",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "アイコンを追加",
            tint = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(30.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { println("画像選択処理") }
        )
    }
}
