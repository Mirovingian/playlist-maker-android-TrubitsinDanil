package com.example.playlist_maker_android_trubitsindanil.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DatabaseMock(
    private val scope: CoroutineScope,
) {
    private val historyList = mutableListOf<String>()
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val playlists = mutableListOf<Playlist>()
    private var tracks = mutableListOf<Track>()

    init {
        tracks = listTracks //TEST

        playlists.add(Playlist(id = 3, name = "MyPlaylist2", description = "COOOOOL!!!", emptyList()))
    }

    fun getHistory(): List<String> {
        return historyList.toList()
    }

    fun addToHistory(word: String) {
        historyList.add(word)
        notifyHistoryChanged()
    }

    private fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500) // Имитируем задержку загрузки из базы данных
        val filteredPlaylists = mutableListOf<Playlist>()
        playlists.forEach { playlist ->
            val playlistTracks = tracks.filter { track ->
                track.playlistId == playlist.id
            }
            filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
        }

        emit(filteredPlaylists.toList())
        delay(100)
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        val playlistTracks = tracks.filter { track ->
            track.playlistId == id
        }
        val playlist = playlists.find {it.id == id}

        emit(playlist?.copy(tracks = playlistTracks))
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.removeIf { it.id == trackId }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300) // Имитируем задержку
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
    }

    fun searchTracks(expression: String): List<Track> {
        return tracks.filter { it.trackName.contains(expression, true) }
    }

    fun getTracksByPlaylistId(playListId: Long): List<Track> {
        return tracks.filter { track -> track.playlistId == playListId }
    }

    fun getTrackById(trackId: Long): Track? {
        return tracks.find { track -> track.id == trackId }
    }


}




val listTracks = mutableListOf(
    Track(
        id = 1,
        trackName = "Yesterday (Remastered 2009)",
        artistName = "The Beatles",
        trackTime = "2:55",
        image = "",
        favorite = false,
        playlistId = 3
    ),


    Track(
        id = 2,
        trackName = "Here Comes The Sun (Remastered...",
        artistName = "The Beatles",
        trackTime = "2:55",
        image = "",
        favorite = false,
        playlistId = 44
    ),

    Track(
        id = 3,
        trackName = "No Reply",
        artistName = "The Beatles",
        trackTime = "5:41",
        image = "",
        favorite = false,
        playlistId = 44
    ),

    Track(
        id = 4,
        trackName = "Let It Be",
        artistName = "The Beatles",
        trackTime = "3:11",
        image = "",
        favorite = false,
        playlistId = 44
    ),

    Track(
        id = 5,
        trackName = "Come Together",
        artistName = "The Beatles",
        trackTime = "4:19",
        image = "",
        favorite = false,
        playlistId = 44
    ),

    Track(
        id = 6,
        trackName = "Something",
        artistName = "The Beatles",
        trackTime = "3:09",
        image = "",
        favorite = false,
        playlistId = 0
    ),


)


