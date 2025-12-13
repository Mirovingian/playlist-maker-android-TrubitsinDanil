package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.playlist_maker_android_trubitsindanil.R
import com.example.playlist_maker_android_trubitsindanil.ui.AppScreens

@Composable
fun MainScreen(navController : NavHostController) {
    val backgroundColor : Color = Color(0xFF3772E7)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
    ) {
        MainScreenTopBar()

        MainScreenContent({ navController.navigate(AppScreens.Search.name) },
            { navController.navigate(AppScreens.Settings.name) },
            {navController.navigate(AppScreens.Playlists.name)},
            {navController.navigate(AppScreens.FavoriteTracks.name)})
    }

}


@Composable
fun MainScreenTopBar() {
    Box (
        modifier = Modifier
            .padding(bottom = 14.dp)
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.TopStart

    ){
        Text(
            modifier = Modifier.padding(top = 14.dp, start = 16.dp, bottom = 16.dp),
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                color = Color.White,
                fontSize = 22.sp,
            )
        )
    }
}

@Composable
fun MainScreenContent(onClickSearch : () -> Unit, onClickSettings: () -> Unit, onClickPlaylists: () -> Unit, onClickFavorites: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ))
            .background(Color.White)
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
    ) {

        MainScreenNavigationItem(
            title = stringResource(id = R.string.search),
            icon = Icons.Default.Search,
            onClick = onClickSearch
        )
        MainScreenNavigationItem(
            title =  stringResource(id = R.string.playlists),
            icon = Icons.Default.LibraryMusic,
            onClick = onClickPlaylists
        )
        MainScreenNavigationItem(
            title =  stringResource(id = R.string.favorites),
            icon = Icons.Default.FavoriteBorder,
            onClick = onClickFavorites
        )
        MainScreenNavigationItem(
            title =  stringResource(id = R.string.settings),
            icon = Icons.Default.Settings,
            onClick = onClickSettings
        )
    }
}

@Composable
fun MainScreenNavigationItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth().height(66.dp)
            .clickable(onClick = onClick)
            .padding(start = 12.dp, top = 20.dp, bottom = 20.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = TextStyle(
                fontSize = 22.sp,
            )
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

        )
    }
}
