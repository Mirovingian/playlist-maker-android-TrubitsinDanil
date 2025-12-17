package com.example.playlist_maker_android_trubitsindanil.data

import android.util.Log
import com.example.playlist_maker_android_trubitsindanil.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_trubitsindanil.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_trubitsindanil.domain.api.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class DatabaseMock(private val networkClient: NetworkClient) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val historyList = mutableListOf<String>()
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val playlists = mutableListOf<Playlist>()
    private var tracks = mutableListOf<Track>()


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

    fun addTrackToFavorite(track : Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
    }

    suspend fun searchTracks(expression: String): List<Track> = withContext(Dispatchers.IO) {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        if (response.resultCode == 0 && response is TracksSearchResponse) {

            val incomingTracks = response.results.map { dto ->
                Track(
                    id = dto.id,
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTime = formatTrackTime(dto.trackTimeMillis),
                    image = dto.image ?: "",
                    favorite = false,
                    playlistId = 0
                )
            }

            // 1. Получаем список ID уже существующих треков
            val existingTrackIds = tracks.map { it.id }.toSet()

            // 2. Фильтруем входящие треки, оставляя только те, ID которых нет в existingTrackIds
            val uniqueNewTracks = incomingTracks.filter { it.id !in existingTrackIds }

            // 3. Добавляем уникальные треки в tracks
            tracks.addAll(uniqueNewTracks)

            // 4. Возвращаем все треки, полученные из сети (включая неуникальные, если нужно)
            // Если вы хотите возвращать только те, что добавили: return@withContext uniqueNewTracks
            // Если вы хотите возвращать все, что получили: return@withContext incomingTracks
            return@withContext incomingTracks
        } else {
            return@withContext emptyList()
        }
    }

    // Вспомогательный метод для конвертации мс в "мм:сс"
    private fun formatTrackTime(millis: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(millis)
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
        playlistId = 0
    ),


    Track(
        id = 2,
        trackName = "Here Comes The Sun (Remastered...",
        artistName = "The Beatles",
        trackTime = "2:55",
        image = "",
        favorite = false,
        playlistId = 0
    ),

    Track(
        id = 3,
        trackName = "No Reply",
        artistName = "The Beatles",
        trackTime = "5:41",
        image = "",
        favorite = false,
        playlistId = 0
    ),

    Track(
        id = 4,
        trackName = "Let It Be",
        artistName = "The Beatles",
        trackTime = "3:11",
        image = "",
        favorite = false,
        playlistId = 0
    ),

    Track(
        id = 5,
        trackName = "Come Together",
        artistName = "The Beatles",
        trackTime = "4:19",
        image = "",
        favorite = false,
        playlistId = 0
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


