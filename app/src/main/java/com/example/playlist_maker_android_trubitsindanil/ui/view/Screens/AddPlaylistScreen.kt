package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.ui.view.CommonTopBar
import com.example.playlist_maker_android_trubitsindanil.R
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel

@Composable
fun AddPlaylistScreen(
    playlistsViewModel : PlaylistsViewModel,
    onBackClick: () -> Unit = {},
) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isButtonEnabled = name.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CommonTopBar(onBackClick = onBackClick, text = stringResource(R.string.new_playlist))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .size(312.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF6F6F6))
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_gallery),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = Color.LightGray
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3772E7),
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color(0xFF3772E7),
                        cursorColor = Color(0xFF3772E7)
                    ),
                    shape = RoundedCornerShape(4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3772E7),
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color(0xFF3772E7),
                        cursorColor = Color(0xFF3772E7)
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
            }
        }


        Button(
            onClick = { playlistsViewModel.createNewPlayList(name, description) },
            enabled = isButtonEnabled,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
                .imePadding(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3772E7),
                disabledContainerColor = Color(0xFF9E9E9E),
                contentColor = Color.White,
                disabledContentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.create),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
