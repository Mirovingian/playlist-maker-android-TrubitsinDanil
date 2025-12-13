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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.data.Playlist
import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.ui.view.TrackListItem
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel




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
        color = Color.White // Белый фон как на скрине
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // 1. Кнопка "Назад" (Верхняя часть)
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

            // 2. Обложка и описание плейлиста (Заголовок)
            item {
                PlaylistHeader(playlist)
            }

            // 3. Список треков
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
        // Обложка плейлиста
        // В реальном проекте используйте AsyncImage (Coil/Glide)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Квадратная картинка
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray), // Заглушка цвета
            contentAlignment = Alignment.Center
        ) {
            // Имитация картинки-мема
            Image(
                // Вставьте сюда painterResource(R.drawable.your_image), если есть
                painter = rememberVectorPainter(Icons.Default.MusicNote),
                contentDescription = "Cover",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Название "Best songs 2021"
        Text(
            text = playlist?.name ?: "",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Год "2022"
        Text(
            text = playlist?.description ?: "",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Метаданные "300 минут • 98 треков"

        Text(
            text = playlist?.tracks?.size.toString() ?: "",
            fontSize = 16.sp,
            color = Color.Black
        )

        // Кнопка "Три точки" (Menu)
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier.padding(top = 4.dp) // Небольшой отступ

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

