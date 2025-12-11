package com.example.playlist_maker_android_trubitsindanil.data.network

import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.domain.api.NetworkClient
import com.example.playlist_maker_android_trubitsindanil.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_trubitsindanil.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import kotlinx.coroutines.delay

class TracksRepositoryImpl() : TracksRepository {
    override suspend fun getAllTracks(): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks.filter { it.trackName.lowercase().contains(expression.lowercase()) }
    }
}

val listTracks = listOf(
    Track(
        id = 1,
        trackName = "Vladivostok 2000",
        artistName = "Мумий Троль",
        trackTime = "2:38",
        image = "",
        favorite = false,
        playlistId = 0
    ),


    Track(
        id = 10,
        trackName = "Black Bumer",
        artistName = "Серега",
        trackTime = "4:01",
        image = "",
        favorite = false,
        playlistId = 0
    )
)