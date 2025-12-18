@file:JvmName("TrackDetailsKt")

package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.playlist_maker_android_trubitsindanil.R
import com.example.playlist_maker_android_trubitsindanil.data.Playlist
import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.ui.view.CommonTopBar
import com.example.playlist_maker_android_trubitsindanil.ui.view.PlaylistListItem
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    trackId: Long,
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit
) {
    // Состояния для BottomSheet
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // Сбор данных из ViewModel
    // initial = emptyList() предотвращает null pointer для списка плейлистов
    val playlists by playlistsViewModel.getAllPlaylists().collectAsState(initial = emptyList())
    // initial = null позволяет отследить состояние загрузки
    val trackState by playlistsViewModel.getTrackById(trackId).collectAsState(initial = null)

    //val favoriteTracks by playlistsViewModel.getFavoriteTracks().collectAsState(initial = emptyList())

//    if (favoriteTracks.isNotEmpty())
//        favoriteTracks.forEach { Log.d("MY", it.toString()) }
//    else
//        Log.d("MY", "no favorites")

    // Если трек еще не загрузился, показываем индикатор загрузки
    val track = trackState
    if (track == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var isFavorite by remember { mutableStateOf(track.favorite) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            CommonTopBar(onBackClick = onBackClick, text = "")

            Spacer(modifier = Modifier.height(16.dp))

            // Обложка трека
            if (track.image.isEmpty()) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(track.image)
                        .crossfade(true)
                        .error(android.R.drawable.ic_menu_report_image) // Иконка ошибки
                        .build(),
                    contentDescription = "Track Artwork",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Информация о треке
            Text(
                text = track.trackName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = track.artistName,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Кнопки управления (Добавить в плейлист, Избранное)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Кнопка добавления в плейлист
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFF0F0F0), // Светло-серый фон
                    modifier = Modifier
                        .size(52.dp)
                        .clickable { showBottomSheet = true }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddBox,
                        contentDescription = "Add to Playlist",
                        tint = Color.DarkGray,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                // Кнопка Избранное

                Surface(
                    shape = CircleShape,
                    color = Color(0xFFF0F0F0),
                    modifier = Modifier
                        .size(52.dp)
                        .clickable {
                            // Переключаем статус (если true -> false, если false -> true)
                            isFavorite = !isFavorite
                            playlistsViewModel.updateTrackFavoriteStatus(track, isFavorite)
                        }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        // Если в избранном - красный, иначе - серый
                        tint = if (isFavorite) Color.Red else Color.DarkGray,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Длительность
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.duration), color = Color.Gray)
                Text(text = track.trackTime)
            }
        }

        // Bottom Sheet (Список плейлистов)
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                        .heightIn(min = 450.dp)
                ) {
                    Text(
                        text = "Добавить в плейлист",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )

                    if (playlists.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Нет созданных плейлистов", color = Color.Gray)
                        }
                    } else {
                        LazyColumn {
                            items(playlists) { playlist ->
                                PlaylistListItem(
                                    playlist = playlist,
                                    onClick = { playlistId ->
                                        playlistsViewModel.insertTrackToPlaylist(track, playlistId)
                                        showBottomSheet = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
























//@file:JvmName("TrackDetailsKt")
//
//package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material.icons.outlined.AddBox
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.example.playlist_maker_android_trubitsindanil.data.Playlist
//import com.example.playlist_maker_android_trubitsindanil.data.Track
//import com.example.playlist_maker_android_trubitsindanil.ui.view.CommonTopBar
//import com.example.playlist_maker_android_trubitsindanil.ui.view.PlaylistListItem
//import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel
//import com.example.playlist_maker_android_trubitsindanil.R
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TrackDetailsScreen(
//    trackId : Long,
//    playlistsViewModel: PlaylistsViewModel,
//    onBackClick : () -> Unit
//) {
//
//    var showBottomSheet by remember { mutableStateOf(false) }
//    val sheetState = rememberModalBottomSheetState()
//
//    val playlists by playlistsViewModel.playlists.collectAsState(initial = emptyList())
//    // initial = null позволяет отследить состояние загрузки
//    val trackState by playlistsViewModel.getTrackById(trackId).collectAsState(initial = null)
//
//    // Если трек еще не загрузился, показываем индикатор загрузки
//    val track = trackState
//    if (track == null) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//        return
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(horizontal = 16.dp)
//        ) {
//            CommonTopBar(onBackClick = onBackClick, "")
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (track.image.isEmpty()) {
//                Image(
//                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )
//            }
//            else {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(track.image)
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )
//            }
//
//
//            Spacer(modifier = Modifier.height(24.dp))
//            Text(text = track.trackName , fontSize = 22.sp, fontWeight = FontWeight.Bold
//            )
//            Text(text = track.artistName, fontSize = 16.sp)
//            Spacer(modifier = Modifier.height(48.dp))
//
//
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Surface(
//                    shape = CircleShape,
//                    color = Color(0xFFD1D1D1),
//                    modifier = Modifier.size(52.dp).clickable { showBottomSheet = true }
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.AddBox,
//                        contentDescription = "Add",
//                        tint = Color.White,
//                        modifier = Modifier.padding(12.dp)
//                    )
//                }
//
//                Surface(shape = CircleShape, color = Color(0xFFD1D1D1), modifier = Modifier
//                    .size(52.dp)
//                    .clickable { playlistsViewModel.updateTrackFavoriteStatus(track, !isFavorite)}) {
//                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.White, modifier = Modifier.padding(12.dp))
//                }
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text(stringResource(R.string.duration), color = Color.Gray)
//                Text(text = track.trackTime)
//            }
//        }
//
//
//        if (showBottomSheet) {
//            ModalBottomSheet(
//                onDismissRequest = { showBottomSheet = false },
//                sheetState = sheetState,
//                containerColor = Color.White,
//                dragHandle = {
//                    BottomSheetDefaults.DragHandle(
//                        color = Color(0xFFCFCFCF),
//                        width = 50.dp
//                    )
//                }
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 32.dp)
//                        .heightIn(min = 450.dp)
//                ) {
//                    Text(
//                        text = "Добавить в плейлист",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
//                        textAlign = TextAlign.Center
//                    )
//
//                    LazyColumn {
//                        items(playlists) { playlist ->
//                            PlaylistListItem(playlist, {playlistId -> playlistsViewModel.insertTrackToPlaylist(track ?: Track.EMPTY, playlistId)})
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//
