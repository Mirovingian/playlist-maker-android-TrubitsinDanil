package com.example.playlist_maker_android_trubitsindanil.data

import com.example.playlist_maker_android_trubitsindanil.data.database.entity.PlaylistEntity

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    var tracks: List<Track>
)

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        image = ""
    )
}