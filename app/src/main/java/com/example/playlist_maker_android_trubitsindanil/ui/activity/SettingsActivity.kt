package com.example.playlist_maker_android_trubitsindanil.ui.activity

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_trubitsindanil.CommonTopBar
import com.example.playlist_maker_android_trubitsindanil.R


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

            val textShare = stringResource(R.string.choose_playlist_maker)
            SettingsNavigationItem(
                title = stringResource(R.string.share_app),
                icon = Icons.Default.Share,
                onClick = {
                    val message = textShare
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, message)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "share"))
                },
                isArrow = false,
            )

            val textMailOfUser = R.string.mail_of_user
            val textMessageToDevelopers = R.string.message_to_developers
            val textThanks = R.string.thanks
            SettingsNavigationItem(
                title = stringResource(R.string.write_to_support),
                icon = Icons.Default.SupportAgent,
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SENDTO)
                    shareIntent.data = Uri.parse("mailto:")
                    shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(textMailOfUser))
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, textMessageToDevelopers)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, textThanks)
                    context.startActivity(shareIntent)
                },
                isArrow = false,
            )

            val textUri = stringResource(R.string.uri)
            SettingsNavigationItem(
                title = stringResource(R.string.user_agreement),
                icon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                onClick = {
                    val url = Uri.parse(textUri)
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
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent,
                checkedTrackColor = Color(0xFFE6E8EB),
                uncheckedTrackColor = Color(0xFFE6E8EB),
                checkedThumbColor = Color(0xFFAEAFB4),
                uncheckedThumbColor = Color(0xFFAEAFB4)
            ),
            thumbContent = { Box(modifier = Modifier.size(18.dp)) }
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


