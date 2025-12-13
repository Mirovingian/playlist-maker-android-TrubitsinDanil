package com.example.playlist_maker_android_trubitsindanil.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.playlist_maker_android_trubitsindanil.creator.Creator
import com.example.playlist_maker_android_trubitsindanil.ui.AppScreens
import com.example.playlist_maker_android_trubitsindanil.ui.PlaylistHost
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.PlaylistsViewModel
import com.example.playlist_maker_android_trubitsindanil.ui.view_model.SearchViewModel

class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel>{
        SearchViewModel.getViewModelFactory(Creator.getTracksRepository(), Creator.getSearchHistoryRepository())
    }

    private val playlistsViewModel by viewModels<PlaylistsViewModel>{
        PlaylistsViewModel.getViewModelFactory(Creator.getTracksRepository(), Creator.getPlaylistsRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistHost(AppScreens.Main, searchViewModel, playlistsViewModel)
        }
    }
}




