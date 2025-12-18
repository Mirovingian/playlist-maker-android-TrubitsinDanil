package com.example.playlist_maker_android_trubitsindanil.ui.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.R



@Composable
fun TrackListItem(track: Track, onClick : (Long) -> Unit, onLongClick : (Long) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(track.id) },
                onLongClick = { onLongClick(track.id) }
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Мини-обложка трека
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (track.image.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.padding(8.dp)
                )
            }
            else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(track.image) // URL, откуда нужно загрузить изображение
                        .crossfade(true) // Опционально: эффект плавного перехода
                        .build(),
                    contentDescription = "Track Artwork",
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Crop // Как обрезать/масштабировать изображение
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Информация о треке
        Column(
            modifier = Modifier.weight(1f) // Занимает всё доступное место
        ) {
            Text(
                text = track.trackName,
                fontSize = 16.sp,
                maxLines = 1,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${track.artistName} • ${track.trackTime}",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        // Стрелочка справа
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Go to track",
            tint = Color.LightGray
        )
    }
}