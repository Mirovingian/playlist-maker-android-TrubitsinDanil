package com.example.playlist_maker_android_trubitsindanil.domain

import com.example.playlist_maker_android_trubitsindanil.data.network.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}