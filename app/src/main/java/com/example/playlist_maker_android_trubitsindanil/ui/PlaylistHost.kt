package com.example.playlist_maker_android_trubitsindanil.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.SearchScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.SettingsScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.SearchViewModel
import kotlin.getValue
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.MainScreen


@Composable
fun PlaylistHost(
    startDestination: AppScreens,
    viewModel: SearchViewModel,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(
            route = startDestination.name
        ) {
            MainScreen(navController)
        }

        composable(
            route = AppScreens.Search.name,
        ) {
            SearchScreen(viewModel, { navController.navigate(AppScreens.Main.name)})
        }

        composable(
            route = AppScreens.Settings.name,
        ) {
            SettingsScreen({navController.navigate(AppScreens.Main.name)})
        }
    }
}


