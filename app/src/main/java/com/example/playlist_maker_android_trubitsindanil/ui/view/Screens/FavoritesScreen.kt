package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android_trubitsindanil.ui.view.CommonTopBar
import com.example.playlist_maker_android_trubitsindanil.ui.view.TrackListItem
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel
import com.example.playlist_maker_android_trubitsindanil.R
@Composable
fun FavoritesScreen(
    playlistsViewModel : PlaylistsViewModel,
    onBackClick : () -> Unit,
    onTrackClick : (Long) -> Unit
) {

    val favoriteTracks by playlistsViewModel.getFavoriteTracks().collectAsState(initial = emptyList())


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                CommonTopBar(onBackClick = onBackClick, stringResource(R.string.favorite_tracks))
            }
            if (favoriteTracks.isNotEmpty()) {
                items(favoriteTracks ) { track ->
                    TrackListItem(track = track, onClick = onTrackClick, onLongClick = {
                        playlistsViewModel.updateTrackFavoriteStatus(track, false)
                    })
                }
            }
            else {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(top = 154.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.tracks_was_not_found),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(bottom = 16.dp)
                            )
                            Text(
                                text = stringResource(R.string.your_mediatec_is_empty),
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}