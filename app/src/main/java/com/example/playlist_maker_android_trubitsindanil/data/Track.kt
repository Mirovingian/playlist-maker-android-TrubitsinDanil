package com.example.playlist_maker_android_trubitsindanil.data

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    var favorite: Boolean,
    var playlistId: Long
) {
    companion object {
        val EMPTY = Track(
            id = 0L,
            trackName = "",
            artistName = "",
            trackTime = "0:00",
            image = "",
            favorite = false,
            playlistId = 0L
        )
    }
}

