package com.example.playlist_maker_android_trubitsindanil.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlist_maker_android_trubitsindanil.data.Track

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    val favorite: Boolean = false,
    val playlistId: Long
)

fun TrackEntity.toTrack(): Track{
    return Track(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        favorite = this.favorite,
        image = this.image,
        playlistId = this.playlistId
    )
}