package com.example.playlist_maker_android_trubitsindanil.domain.api

import com.example.playlist_maker_android_trubitsindanil.data.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

    suspend fun getTrackByNameAndArtist(track: Track): Track?

    suspend fun getFavoriteTracks(): List<Track>

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(track: Track)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    suspend fun deleteTracksByPlaylistId(playlistId: Long)

    suspend fun getTracksByPlaylistId(playlistId: Long) : List<Track>
    suspend fun getTrackById(trackId: Long) : Track?
}
