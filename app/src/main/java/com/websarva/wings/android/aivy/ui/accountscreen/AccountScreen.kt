package com.websarva.wings.android.aivy.ui.accountscreen

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import com.websarva.wings.android.aivy.R

@Composable
fun AccountScreen() {
    var userName by remember { mutableStateOf("名前") }
    var showOtherCharacters by remember { mutableStateOf(false) }
    var favoriteCharacter by remember { mutableStateOf("キャラクター名") }
    var description by remember { mutableStateOf("キャラクターの説明文") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header(title = userName, onSettingsClick = { println("設定ボタンがクリックされました") })
        Spacer(modifier = Modifier.height(16.dp))
        IconSettingSection()
        Spacer(modifier = Modifier.height(16.dp))
        NameSettingSection(userName) { newName -> userName = newName }
        Spacer(modifier = Modifier.height(16.dp))
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
fun Header(title: String, onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = onSettingsClick) {
            Icon(Icons.Default.Settings, contentDescription = "設定")
        }
    }
}

@Composable
fun IconSettingSection() {
    val profileImage: Painter = getProfileImage()

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Gray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = profileImage,
            contentDescription = "プロフィール画像",
            modifier = Modifier
                .size(100.dp)
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

@Composable
fun getProfileImage(): Painter {
    return runCatching {
        painterResource(id = R.drawable.watermark)
    }.getOrElse {
        painterResource(id = R.drawable.ic_launcher_foreground)
    }
}

@Composable
fun NameSettingSection(name: String, onNameChange: (String) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    if (isEditing) {
        TextField(
            value = name,
            onValueChange = { onNameChange(it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true
        )
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { isEditing = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, fontSize = 18.sp, modifier = Modifier.weight(1f))
            Icon(Icons.Default.Edit, contentDescription = "名前を編集", tint = Color.Gray)
        }
    }
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
                    imageVector = Icons.Filled.ArrowForward, // AutoMirroredを削除
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
                    imageResId = character.first, // Pairから画像IDを取得
                    name = character.second      // Pairから名前を取得
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

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AccountScreenPreview() {
    AccountScreen()
}
