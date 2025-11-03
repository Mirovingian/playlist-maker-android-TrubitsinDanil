package com.example.playlist_maker_android_trubitsindanil

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.navigation.NavHostController
import com.example.playlist_maker_android_trubitsindanil.ui.theme.PlaylistmakerandroidTrubitsinDanilTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistHost(AppScreens.Main)
        }
    }
}


@Composable
fun MainActivityScreen(navController : NavHostController) {
    val backgroundColor : Color = Color(0xFF3772E7);

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
    ) {
        MainActivityTopBar()

        MainActivityContent({ navController.navigate(AppScreens.Search.name) }, { navController.navigate(AppScreens.Settings.name) })
    }

}


@Composable
fun MainActivityTopBar() {
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
fun MainActivityContent(onClickSearch : () -> Unit, onClickSettings: () -> Unit) {
    val context = LocalContext.current
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

        MainActivityNavigationItem(
            title = stringResource(id = R.string.search),
            icon = Icons.Default.Search,
            onClick = onClickSearch
        )
        MainActivityNavigationItem(
            title =  stringResource(id = R.string.playlists),
            icon = Icons.Default.LibraryMusic,
            onClick = {}
        )
        MainActivityNavigationItem(
            title =  stringResource(id = R.string.favorites),
            icon = Icons.Default.FavoriteBorder,
            onClick = {}
        )
        MainActivityNavigationItem(
            title =  stringResource(id = R.string.settings),
            icon = Icons.Default.Settings,
            onClick = onClickSettings
        )
    }
}

@Composable
fun MainActivityNavigationItem(
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









