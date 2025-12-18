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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository : PlaylistsRepository,
    private val tracksRepository : TracksRepository
) : ViewModel() {

//    val playlists: Flow<List<Playlist>> = flow {
//        val collectedPlaylists = mutableListOf<Playlist>()
//        playlistsRepository.getAllPlaylists().collect { playlist ->
//            collectedPlaylists.addAll(playlist)
//            emit(collectedPlaylists.toList())
//        }
//    }
   // val favoriteList: Flow<List<Track>> = databaseRepository.getFavoriteTracks()

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description)
        }
    }

    fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.insertTrackToPlaylist(track, playlistId)
        }
    }

    fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
        }
    }

    fun deleteTrackFromPlaylist(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteTrackFromPlaylist(track)
        }
    }

    fun deletePlaylistById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteTracksByPlaylistId(id)
            playlistsRepository.deletePlaylistById(id)
        }
    }

    fun getTracksByPlaylistId(playlistId: Long): Flow<List<Track>> = flow {
        emit(tracksRepository.getTracksByPlaylistId(playlistId))
    }.flowOn(Dispatchers.IO)

    fun getTrackById(trackId: Long): Flow<Track?> = flow {
        emit(tracksRepository.getTrackById(trackId))
    }.flowOn(Dispatchers.IO)

    fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistsRepository.getPlaylist(playlistId)
    }

    fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistsRepository.getAllPlaylists()
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        emit( tracksRepository.getFavoriteTracks())
    }.flowOn(Dispatchers.IO)

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