package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.data.Playlist
import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.ui.view.TrackListItem
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel
import com.example.playlist_maker_android_trubitsindanil.R




@Composable
fun PlaylistScreen(
    playlistsViewModel : PlaylistsViewModel,
    playlistId : Long,
    onBackClick : () -> Unit,
    onTrackClick : (Long) -> Unit
) {
    val playlistFlow = remember(playlistId) {
        playlistsViewModel.getPlaylist(playlistId)
    }

    val playlist: Playlist? by playlistFlow.collectAsState(initial = null)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }

            item {
                PlaylistHeader(playlist)
            }

            items(playlist?.tracks ?: emptyList<Track>()) { track ->
                TrackListItem(track = track, onClick = onTrackClick)
            }
        }
    }
}

@Composable
fun PlaylistHeader(playlist: Playlist?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = rememberVectorPainter(Icons.Default.MusicNote),
                contentDescription = "Cover",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = playlist?.name ?: "",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))


        Text(
            text = playlist?.description ?: "",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        val allTimeInSec = playlist?.tracks?.sumOf {track -> parseTrackTimeToSeconds(track.trackTime)} ?: 0
        Text(
            text = "${allTimeInSec / 60} ${stringResource(R.string.minutes)} • ${playlist?.tracks?.size.toString() ?: ""} ${stringResource(R.string.tracks)}",
            fontSize = 16.sp,
            color = Color.Black
        )


        IconButton(
            onClick = { },
            modifier = Modifier.padding(top = 4.dp)

        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}


fun parseTrackTimeToSeconds(trackTimeString: String): Int {
    return try {
        val parts = trackTimeString.split(":")
        if (parts.size == 2) {
            val minutes = parts[0].toInt()
            val seconds = parts[1].toInt()
            minutes * 60 + seconds
        } else {
            0
        }
    } catch (e: Exception) {
        0
    }
}
