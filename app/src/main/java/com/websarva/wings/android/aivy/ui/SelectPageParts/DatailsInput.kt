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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.database.database
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
                .border(1.dp, DatailsColor)
                .background(Color.Black.copy(alpha = 0.15f))
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = TextStyle(
                    color = Color.DarkGray,
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
                    .border(1.dp, DatailsColor)
                    .height(50.dp)
                    .width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = 0.15f),
                    contentColor = Color.White,
                ),
                shape = RectangleShape,
                elevation = ButtonDefaults.buttonElevation(0.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "作成",
                    fontSize = 20.sp
                )
            }
        }
    }
}