package com.example.playlist_maker_android_trubitsindanil.data.impl

import com.example.playlist_maker_android_trubitsindanil.data.Playlist
import com.example.playlist_maker_android_trubitsindanil.data.database.AppDatabase
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.PlaylistEntity
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.toPlaylist
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.toTrack
import com.example.playlist_maker_android_trubitsindanil.domain.api.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.PlaylistsDao().getPlaylistById(playlistId).map { entity ->
            if (entity == null) return@map null

            val trackEntities = database.TracksDao().getTracksByPlaylistId(entity.id)
            val tracks = trackEntities.map { it.toTrack() }

            entity.toPlaylist().copy(tracks = tracks)
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.PlaylistsDao().getPlaylists().map { entities ->
            entities.map { entity ->
                val trackEntities = database.TracksDao().getTracksByPlaylistId(entity.id)
                val tracks = trackEntities.map { it.toTrack() }

                entity.toPlaylist().copy(tracks = tracks)
            }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        val playlistEntity = PlaylistEntity(
            name = name,
            description = description,
            image = ""
        )
        database.PlaylistsDao().insertPlaylist(playlistEntity)
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.TracksDao().deleteTracksByPlaylistId(id)
        database.PlaylistsDao().deletePlaylistById(id)
    }
}