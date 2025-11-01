package com.example.playlist_maker_android_trubitsindanil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.compose.material.icons.filled.LibraryMusic


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                MainActivityScreen()
            }
        }
    }

    
    @Composable
    fun MainActivityScreen() {
        val backgroundColor : Color = Color(0xFF3772E7);

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor),
        ) {
            MainActivityTopBar()

            MainActivityContent()
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
    fun MainActivityContent() {
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

            NavigationItem(
                title = stringResource(id = R.string.search),
                icon = Icons.Default.Search,
                onClick = {
                    val intent = Intent(context, SearchActivity::class.java)
                    context.startActivity(intent)
                }
            )
            NavigationItem(
                title =  stringResource(id = R.string.playlists),
                icon = Icons.Default.LibraryMusic,
                onClick = {}
            )
            NavigationItem(
                title =  stringResource(id = R.string.favorites),
                icon = Icons.Default.FavoriteBorder,
                onClick = {}
            )
            NavigationItem(
                title =  stringResource(id = R.string.settings),
                icon = Icons.Default.Settings,
                onClick = {
                    val intent = Intent(context, SettingsActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }

    @Composable
    fun NavigationItem(
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
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

            )
        }
    }


}






