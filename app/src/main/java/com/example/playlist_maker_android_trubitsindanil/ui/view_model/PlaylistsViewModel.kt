package com.example.playlist_maker_android_trubitsindanil.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_trubitsindanil.data.DatabaseMock
import com.example.playlist_maker_android_trubitsindanil.data.Playlist
import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.data.impl.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.impl.TracksRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository : PlaylistsRepository,
    private val tracksRepository : TracksRepository
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = flow {
        val collectedPlaylists = mutableListOf<Playlist>()
        playlistsRepository.getAllPlaylists().collect { playlist ->
            collectedPlaylists.addAll(playlist)
            emit(collectedPlaylists.toList())
        }
    }
   // val favoriteList: Flow<List<Track>> = databaseRepository.getFavoriteTracks()

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description)
        }
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksRepository.insertTrackToPlaylist(track, playlistId)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksRepository.deleteTrackFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track = track).firstOrNull()
    }

    fun getTracksByPlaylistId(playlistId: Long): List<Track> {
        return tracksRepository.getTracksByPlaylistId(playlistId)
    }

    fun getTrackById(trackId: Long) : Track? {
        return tracksRepository.getTrackById(trackId)
    }

    fun getPlaylist(playlistId : Long) : Flow<Playlist?> {
        return playlistsRepository.getPlaylist(playlistId)
    }

    fun addTrackToFavorite(track : Track) {
        tracksRepository.addTrackToFavorite(track)
    }

    companion object {
        fun getViewModelFactory(tracksRepository: TracksRepository, playlistsRepository: PlaylistsRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistsViewModel(playlistsRepository, tracksRepository) as T
                }
            }
    }
}