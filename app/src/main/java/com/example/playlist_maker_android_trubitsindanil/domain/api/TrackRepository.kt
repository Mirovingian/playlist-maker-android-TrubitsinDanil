package com.example.playlist_maker_android_trubitsindanil.domain.api

import com.example.playlist_maker_android_trubitsindanil.data.Track

interface TracksRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>
}