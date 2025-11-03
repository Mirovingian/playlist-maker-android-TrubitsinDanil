package com.example.playlist_maker_android_trubitsindanil.ui.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.CommonTopBar
import com.example.playlist_maker_android_trubitsindanil.R


@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTopBar(onBackClick = onBackClick, stringResource(R.string.search))

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            SearchInput(onSearchClick)
        }
    }
}



@Composable
fun SearchInput(onSearchClick : () -> Unit) {
    var searchText by rememberSaveable { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = TextStyle(
                    fontSize = 21.sp,
                    color = Color(0xFFAEAFB4)
                ),
            )
        },
        leadingIcon = {
            IconButton(onSearchClick)
            {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                )
            }
        },
        trailingIcon = {
            if (!searchText.isEmpty())
            {
                IconButton({searchText = ""}) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                    )
                }
            }
        },

        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE6E8EB),
            unfocusedContainerColor = Color(0xFFE6E8EB),
            disabledContainerColor = Color(0xFFE6E8EB),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true
    )
}







