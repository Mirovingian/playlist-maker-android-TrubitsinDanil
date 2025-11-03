package com.example.playlist_maker_android_trubitsindanil.domain.api

import com.example.playlist_maker_android_trubitsindanil.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}