package com.websarva.wings.android.aivy.ui.SelectPageParts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.websarva.wings.android.aivy.ui.ViewPageParts.currentLanguage
import com.websarva.wings.android.aivy.ui.theme.DatailsColor
import com.websarva.wings.android.aivy.ui.theme.HeaderColor




@Composable
fun DetailsInput(navController: NavController) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .padding(16.dp)
    ){
        Column(
            modifier = Modifier
                .height(180.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(3.dp, DatailsColor, RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.5f)),
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = TextStyle(
                    color = DatailsColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                decorationBox = { innerTextField ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            )
        }

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
            Button(
                onClick = {
                    Firebase.database.reference.child("datails").setValue(text)
                    text = ""
                    navController.navigate("viewpage")
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .height(50.dp)
                    .border(3.dp, DatailsColor, RoundedCornerShape(20.dp))
                    .width(120.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.5f),
                    contentColor = DatailsColor,
                ),
                shape = RectangleShape,
                elevation = ButtonDefaults.buttonElevation(0.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = if(currentLanguage == "English") "Create" else "作成",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}