package com.example.playlist_maker_android_trubitsindanil.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Delete
    suspend fun deleteTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist")
    suspend fun getTrackByNameAndArtist(name: String, artist: String): TrackEntity?

    @Query("SELECT * FROM tracks WHERE id = :id")
    suspend fun getTrackById(id: Long): TrackEntity?

    @Query("SELECT * FROM tracks WHERE favorite = 1 ORDER BY id DESC")
    suspend fun getFavoriteTracks(): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE playlistId = :playlistId")
    suspend fun getTracksByPlaylistId(playlistId: Long): List<TrackEntity>

    @Query("DELETE FROM tracks WHERE playlistId = :playlistId")
    suspend fun deleteTracksByPlaylistId(playlistId: Long)
}