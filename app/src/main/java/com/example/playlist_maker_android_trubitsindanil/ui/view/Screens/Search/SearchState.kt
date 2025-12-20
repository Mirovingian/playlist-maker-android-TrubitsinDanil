package com.example.playlist_maker_android_trubitsindanil.ui.view.Screens.Search

import com.example.playlist_maker_android_trubitsindanil.data.Track

sealed class SearchState {
    object Initial: SearchState()
    object Searching: SearchState()
    data class Success(val list: List<Track>): SearchState()
    data class Fail(val error: String): SearchState()
}