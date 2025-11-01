package com.example.playlist_maker_android_trubitsindanil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.ui.theme.PlaylistmakerandroidTrubitsinDanilTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlaylistmakerandroidTrubitsinDanilTheme {
                SettingsScreen({})
            }
        }
    }
}

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTopBar(onBackClick = onBackClick, stringResource(R.string.settings))

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier
            .fillMaxWidth(),
        ) {
            DarkThemeSettingItem()

            SettingsNavigationItem(
                title = stringResource(R.string.share_app),
                icon = Icons.Default.Share,
                onClick = {
                    val message = "Привет, Я пользуюсь Playlist Maker!"
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, message)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Поделиться через"))
                },
                isArrow = false,
            )

            SettingsNavigationItem(
                title = stringResource(R.string.write_to_support),
                icon = Icons.Default.SupportAgent,
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SENDTO)
                    shareIntent.data = Uri.parse("mailto:")
                    shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("aeshmuratov@sfedu.ru"))
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Сообщение разработчикам и разработчицам приложения Playlist Make")
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Спасибо разработчикам и разработчицам за крутое приложение!")
                    context.startActivity(shareIntent)
                },
                isArrow = false,
            )

            SettingsNavigationItem(
                title = stringResource(R.string.user_agreement),
                icon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {
                    val url = Uri.parse("https://yandex.ru/legal/practicum_offer")
                    val shareIntent = Intent(Intent.ACTION_VIEW, url)
                    context.startActivity(shareIntent)
                },
                isArrow = true,
            )

        }
    }
}

@Composable
fun DarkThemeSettingItem() {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .padding(start = 16.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.dark_theme),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = isDarkTheme,
            onCheckedChange = { isDarkTheme = it },

            colors = SwitchDefaults.colors(

            )
        )
    }
}


@Composable
fun SettingsNavigationItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isArrow : Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth().height(61.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
            )
        )

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = if (isArrow)  Modifier.size(16.dp) else Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

        )
    }
}


