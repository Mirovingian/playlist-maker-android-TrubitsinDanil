package com.example.playlist_maker_android_trubitsindanil.domain.api

import com.example.playlist_maker_android_trubitsindanil.data.Track

interface TrackSearchInteractor {
    suspend fun searchTracks(expression: String): List<Track>
}