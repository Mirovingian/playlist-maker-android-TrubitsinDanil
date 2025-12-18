package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.ui.view.PlaylistListItem
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel
import com.example.playlist_maker_android_trubitsindanil.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.playlist_maker_android_trubitsindanil.data.Playlist
import com.example.playlist_maker_android_trubitsindanil.ui.view.CommonTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateToPlaylist: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    val playlists by playlistsViewModel.getAllPlaylists().collectAsState(emptyList())

    var showBottomSheet by remember { mutableStateOf(false) }
    var sourcePlaylistToMerge by remember { mutableStateOf<Playlist?>(null) }
    val sheetState = rememberModalBottomSheetState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            CommonTopBar(onBackClick = navigateBack, stringResource(R.string.playlists))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp),
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(playlists.size) { index ->
                        PlaylistListItem(playlist = playlists[index],
                            onClick = {
                                navigateToPlaylist(playlists[index].id)
                            },
                            onLongClick = {
                                sourcePlaylistToMerge = playlists[index]
                                showBottomSheet = true
                            }
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd),
            onClick = { addNewPlaylist() },
            containerColor = Color.LightGray,
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )

        }


        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                dragHandle = {
                    BottomSheetDefaults.DragHandle(
                        color = Color(0xFFCFCFCF),
                        width = 50.dp
                    )
                }
            ) {
                ModalBottomSheetContent(
                    playlists = playlists,
                    sourcePlaylistToMerge = sourcePlaylistToMerge ?: Playlist.EMPTY,
                    context = LocalContext.current,
                    playlistsViewModel = playlistsViewModel,
                    onCloseClick = { showBottomSheet = false }
                )

            }
        }
    }
}

@Composable
fun ModalBottomSheetContent(
    playlists : List<Playlist>,
    sourcePlaylistToMerge : Playlist,
    context : Context,
    playlistsViewModel: PlaylistsViewModel,
    onCloseClick : () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .heightIn(min = 450.dp)
    ) {
        Text(
            text = stringResource(R.string.merge_playlists),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        val availableTargets = playlists.filter { it.id != sourcePlaylistToMerge.id }

        if (availableTargets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.no_other_playlists), color = Color.Gray)
            }
        } else {
            LazyColumn {
                items(availableTargets.size) { index ->
                    val targetPlaylist = availableTargets[index]
                    val message = stringResource(R.string.merged_with)
                    PlaylistListItem(
                        playlist = targetPlaylist,
                        onClick = {
                            playlistsViewModel.mergePlaylists(
                                sourcePlaylist = sourcePlaylistToMerge,
                                targetPlaylist = targetPlaylist
                            )

                            Toast.makeText(
                                context,
                                "${sourcePlaylistToMerge.name} $message ${targetPlaylist.name}",
                                Toast.LENGTH_SHORT
                            ).show()

                            onCloseClick()
                        }
                    )
                }
            }
        }
    }
}