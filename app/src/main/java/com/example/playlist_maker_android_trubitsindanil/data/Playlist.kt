package com.example.playlist_maker_android_trubitsindanil.data

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    var tracks: List<Track>
)