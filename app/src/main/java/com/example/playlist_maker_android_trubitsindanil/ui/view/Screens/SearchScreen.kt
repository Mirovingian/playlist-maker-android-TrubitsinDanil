package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.R
import com.example.playlist_maker_android_trubitsindanil.data.SearchState
import com.example.playlist_maker_android_trubitsindanil.ui.view.CommonTopBar
import com.example.playlist_maker_android_trubitsindanil.ui.view.HistoryRequests
import com.example.playlist_maker_android_trubitsindanil.ui.view.TrackListItem
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.SearchViewModel




@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onBackClick : () -> Unit,
    onTrackClick : (Long) -> Unit
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    var text by remember { mutableStateOf("") }

    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val historyFlow by searchViewModel.getHistoryList().collectAsState(initial = emptyList())


    LaunchedEffect(text) {
        searchViewModel.updateQuery(text)
    }

    CommonTopBar(onBackClick = onBackClick, stringResource(R.string.search))

    Spacer(modifier = Modifier.height(24.dp))

    Column(
        modifier = Modifier
            .padding(top = 48.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
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
                Icon(
//                    modifier = Modifier.clickable {
//                        searchViewModel.performSearch(text)
//                    },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                )
            },
            trailingIcon = {
                if (!text.isEmpty())
                {
                    IconButton({text = ""; searchViewModel.clearSearch()}) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
                .height(52.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
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

        Spacer(modifier = Modifier.height(16.dp))
        if (isFocused && text.isEmpty() && historyFlow.isNotEmpty()) {
            HistoryRequests(
                historyList = historyFlow,
                onClick = { word ->
                    text = word
                }
            )
        }

        when (screenState) {
            is SearchState.Initial -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.input_string_for_search))
                }
            }

            is SearchState.Searching -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                //focusManager.clearFocus()
                val tracks = (screenState as SearchState.Success).list
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (tracks.isEmpty()) {

                        item {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(stringResource(R.string.tracks_were_not_found))
                            }
                        }
                    }

                    items(tracks.size) { index ->
                        TrackListItem(track = tracks[index], onClick = onTrackClick)
                    }
                }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("${stringResource(R.string.error)}: $error", color = Color.Red)
                }
            }
        }
    }
}