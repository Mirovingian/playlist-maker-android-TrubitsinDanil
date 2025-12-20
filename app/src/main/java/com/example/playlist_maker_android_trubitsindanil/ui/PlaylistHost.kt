package com.example.playlist_maker_android_trubitsindanil.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.AddPlaylistScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.FavoritesScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.Search.SearchScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.SettingsScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.SearchViewModel
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.MainScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.PlaylistScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.PlaylistsScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.TrackDetailsScreen
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel


@Composable
fun PlaylistHost(
    startDestination: AppScreens,
    searchViewModel: SearchViewModel,
    playlistsViewModel: PlaylistsViewModel
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
            SearchScreen(searchViewModel, { navController.navigate(AppScreens.Main.name)}, {trackId -> navController.navigate("${AppScreens.TrackDetails.name}/$trackId")})
        }

        composable(
            route = AppScreens.Settings.name,
        ) {
            SettingsScreen({navController.navigate(AppScreens.Main.name)})
        }

        composable(
            route = AppScreens.Playlists.name,
        ) {
            PlaylistsScreen(playlistsViewModel = playlistsViewModel, {navController.navigate(AppScreens.AddPlaylist.name)}, {playlistId -> navController.navigate("${AppScreens.Playlist.name}/$playlistId")}, {navController.navigate(AppScreens.Main.name)})
        }

        composable(
            route = "${AppScreens.Playlist.name}/{playlistId}",
            arguments = listOf(
                navArgument("playlistId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L

            PlaylistScreen(playlistsViewModel = playlistsViewModel, playlistId, {navController.navigate(AppScreens.Playlists.name)}, {trackId -> navController.navigate("${AppScreens.TrackDetails.name}/$trackId")})
        }

        composable(
            route = AppScreens.AddPlaylist.name,
        ) {
            AddPlaylistScreen(playlistsViewModel = playlistsViewModel, onBackClick = {navController.navigate(AppScreens.Playlists.name)})
        }

        composable(
            route = "${AppScreens.TrackDetails.name}/{trackId}",
            arguments = listOf(
                navArgument("trackId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getLong("trackId") ?: 0L

            TrackDetailsScreen(playlistsViewModel = playlistsViewModel, trackId = trackId, onBackClick = {navController.popBackStack()})
        }

        composable(
            route = AppScreens.FavoriteTracks.name,
        ) {
            FavoritesScreen(playlistsViewModel = playlistsViewModel, onBackClick = {navController.popBackStack()}, onTrackClick = {trackId -> navController.navigate("${AppScreens.TrackDetails.name}/$trackId")})
        }
    }
}


