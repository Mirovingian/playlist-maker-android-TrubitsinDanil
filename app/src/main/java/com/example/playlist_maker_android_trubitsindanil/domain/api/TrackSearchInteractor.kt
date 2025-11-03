package com.example.playlist_maker_android_trubitsindanil.domain.api

import com.example.playlist_maker_android_trubitsindanil.domain.models.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String): List<Track>
}