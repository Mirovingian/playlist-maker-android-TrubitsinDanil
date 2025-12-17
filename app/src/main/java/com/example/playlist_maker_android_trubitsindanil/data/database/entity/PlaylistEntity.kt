package com.example.playlist_maker_android_trubitsindanil.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlist_maker_android_trubitsindanil.data.Playlist

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val image: String = ""
)

fun PlaylistEntity.toPlaylist(): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        tracks = emptyList()
    )
}


