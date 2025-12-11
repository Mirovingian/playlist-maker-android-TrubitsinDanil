package com.example.playlist_maker_android_trubitsindanil.creator

import com.example.playlist_maker_android_trubitsindanil.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_trubitsindanil.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.domain.api.TrackSearchInteractor
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import com.example.playlist_maker_android_trubitsindanil.domain.impl.TrackSearchInteractorImpl

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl()
    }

    fun getTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}