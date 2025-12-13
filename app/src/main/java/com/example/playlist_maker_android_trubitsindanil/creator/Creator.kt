package com.example.playlist_maker_android_trubitsindanil.creator

import com.example.playlist_maker_android_trubitsindanil.data.DatabaseMock
import com.example.playlist_maker_android_trubitsindanil.data.impl.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.impl.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.impl.TracksRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.TrackSearchInteractor
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import com.example.playlist_maker_android_trubitsindanil.domain.impl.TrackSearchInteractorImpl
import kotlinx.coroutines.CoroutineScope


object Creator {
    private val database : DatabaseMock = DatabaseMock()
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(database)
    }

    fun getPlaylistsRepository() : PlaylistsRepository {
        return PlaylistsRepositoryImpl(database)
    }

    fun getSearchHistoryRepository() : SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(database)
    }


}