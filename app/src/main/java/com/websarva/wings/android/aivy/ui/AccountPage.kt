package com.websarva.wings.android.aivy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.websarva.wings.android.aivy.R
import com.websarva.wings.android.aivy.ui.ViewPageParts.ViewHeader



@Composable
fun AccountPage(
    navController:NavController
) {
    var userName by remember { mutableStateOf("") }
    var showOtherCharacters by remember { mutableStateOf(false) }
    var favoriteCharacter by remember { mutableStateOf("キャラクター名") }
    var description by remember { mutableStateOf("キャラクターの説明文") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp), // 全体を下に移動
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ViewHeader(navController = navController)
            ProfileSection(
                userName = userName,
                onUserNameChange = { userName = it },
                onSettingsClick = { println("設定ボタンがクリックされました") }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (showOtherCharacters) {
            OtherCharactersSection { showOtherCharacters = false }
        } else {
            FavoriteCharacterSection(
                favoriteCharacter = favoriteCharacter,
                description = description,
                onSwitchToOthers = { showOtherCharacters = true }
            )
        }
    }
}

@Composable
fun ProfileSection(
    userName: String,
    onUserNameChange: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp) // 全体の上下移動を調整
    ) {
        // アイコンセクション
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

        Spacer(modifier = Modifier.height(16.dp)) // アイコンと名前フィールドの間のスペース

        // 名前入力フィールド
        NameInputField(userName, onUserNameChange)

        // 設定ボタン
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "設定")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInputField(userName: String, onUserNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = userName,
        onValueChange = onUserNameChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (userName.isNotEmpty()) Color.LightGray.copy(alpha = 0.2f) else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            ),
        placeholder = { Text("名前を入力") }, // プレースホルダー
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun FavoriteCharacterSection(
    favoriteCharacter: String,
    description: String,
    onSwitchToOthers: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "キャラクター",
                modifier = Modifier.size(48.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = favoriteCharacter, fontWeight = FontWeight.Bold)
                Text(text = "♡ お気に入り", color = Color.Red)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onSwitchToOthers) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "その他キャラクター"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = description, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun OtherCharactersSection(onBack: () -> Unit) {
    val characters: List<Pair<Int, String>> = List(9) { index ->
        Pair(R.drawable.watermark, "キャラ ${index + 1}")
    }

    Column {
        Text(
            "その他のキャラクター",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(items = characters) { character ->
                CharacterItem(
                    imageResId = character.first,
                    name = character.second
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            )
        ) {
            Text("戻る", color = Color.White)
        }
    }
}

@Composable
fun CharacterItem(imageResId: Int, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .size(90.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = name,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
