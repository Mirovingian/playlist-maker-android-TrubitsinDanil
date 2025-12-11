package com.example.playlist_maker_android_trubitsindanil.domain.impl

import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_trubitsindanil.data.SearchState
import com.example.playlist_maker_android_trubitsindanil.data.Track
import com.example.playlist_maker_android_trubitsindanil.domain.api.TrackSearchInteractor
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class TrackSearchInteractorImpl(private val repository: TracksRepository) : TrackSearchInteractor {
    override suspend fun searchTracks(expression: String): List<Track> {
            return repository.searchTracks(expression)
    }
}