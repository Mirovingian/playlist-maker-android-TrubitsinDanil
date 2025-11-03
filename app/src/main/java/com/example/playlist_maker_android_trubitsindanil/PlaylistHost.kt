package com.example.playlist_maker_android_trubitsindanil

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playlist_maker_android_trubitsindanil.ui.activity.MainActivityScreen
import com.example.playlist_maker_android_trubitsindanil.ui.activity.SearchScreen
import com.example.playlist_maker_android_trubitsindanil.ui.activity.SettingsScreen


@Composable
fun PlaylistHost(
    startDestination: AppScreens,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(
            route = startDestination.name
        ) {
            MainActivityScreen(navController)
        }

        composable(
            route = AppScreens.Search.name,
        ) {
            SearchScreen({ navController.navigate(AppScreens.Main.name)}, {})
        }

        composable(
            route = AppScreens.Settings.name,
        ) {
            SettingsScreen({navController.navigate(AppScreens.Main.name)})
        }
    }
}


