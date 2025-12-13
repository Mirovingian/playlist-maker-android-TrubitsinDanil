package com.example.playlist_maker_android_trubitsindanil.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_trubitsindanil.creator.Creator
import com.example.playlist_maker_android_trubitsindanil.data.impl.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.SearchState
import com.example.playlist_maker_android_trubitsindanil.data.Word
import com.example.playlist_maker_android_trubitsindanil.data.impl.TracksRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.TrackSearchInteractor
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepository : TracksRepository,
    private val searchHistoryRepository : SearchHistoryRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState  = _searchScreenState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(request)
                val list = tracksRepository.searchTracks(expression = request)
                _searchScreenState.update { SearchState.Success(list = list) }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message.toString()) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

    suspend fun getHistoryList() = searchHistoryRepository.getHistory()


    companion object {
        fun getViewModelFactory(tracksRepository : TracksRepository, searchHistoryRepository : SearchHistoryRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(tracksRepository , searchHistoryRepository) as T
                }
            }
    }
}