package com.example.playlist_maker_android_trubitsindanil.data.impl

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import com.example.playlist_maker_android_trubitsindanil.data.DatabaseMock
import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.data.database.AppDatabase
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.toTrack
import com.example.playlist_maker_android_trubitsindanil.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_trubitsindanil.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_trubitsindanil.data.toEntity
import com.example.playlist_maker_android_trubitsindanil.domain.api.NetworkClient
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.collections.map
import kotlin.collections.toSet


class TracksRepositoryImpl(
    private val database: AppDatabase,
    private val networkClient: NetworkClient
) : TracksRepository {


    override suspend fun searchTracks(expression: String): List<Track> = withContext(Dispatchers.IO) {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        if (response.resultCode == 0 && response is TracksSearchResponse) {

            val foundedTracks = response.results.map { dto ->
                Track(
                    id = dto.id,
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTime = formatTrackTime(
                        dto.trackTimeMillis
                    ),
                    image = dto.image ?: "",
                    favorite = false,
                    playlistId = 0
                )
            }

            for (track in foundedTracks) {
                val existedTrack = database.TracksDao().getTrackById(track.id)
                if (existedTrack == null) {
                    database.TracksDao().insertTrack(track.toEntity())
                }
            }

            return@withContext foundedTracks
        } else {
            return@withContext emptyList()
        }
    }

    override suspend fun getTrackByNameAndArtist(track: Track): Track? {
        return database.TracksDao().getTrackByNameAndArtist(track.trackName, track.artistName)!!.toTrack()
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        database.TracksDao().insertTrack(track.copy(playlistId = playlistId).toEntity())
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.TracksDao().insertTrack(track.copy(playlistId = 0).toEntity())
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.TracksDao().insertTrack(track.copy(favorite = isFavorite).toEntity())
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        database.TracksDao().deleteTracksByPlaylistId(playlistId)
    }

    override suspend fun getFavoriteTracks(): List<Track> {
        return database.TracksDao().getFavoriteTracks()
            .map { it.toTrack() }
    }

    override suspend fun getTracksByPlaylistId(playlistId: Long): List<Track> {
        return database.TracksDao().getTracksByPlaylistId(playlistId)
            .map { it.toTrack() }
    }

    override suspend fun getTrackById(trackId: Long): Track? {
        return database.TracksDao().getTrackById(trackId)!!.toTrack()
    }
    private fun formatTrackTime(millis: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(millis)
    }
}
