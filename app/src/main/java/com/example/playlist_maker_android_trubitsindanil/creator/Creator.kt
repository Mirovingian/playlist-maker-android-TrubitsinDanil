package com.example.playlist_maker_android_trubitsindanil.creator

import com.example.playlist_maker_android_trubitsindanil.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_trubitsindanil.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.domain.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}