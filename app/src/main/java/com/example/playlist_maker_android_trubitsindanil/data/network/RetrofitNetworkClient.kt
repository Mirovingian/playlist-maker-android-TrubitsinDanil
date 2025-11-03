package com.example.playlist_maker_android_trubitsindanil.data.network

import com.example.playlist_maker_android_trubitsindanil.*
import com.example.playlist_maker_android_trubitsindanil.creator.Storage
import com.example.playlist_maker_android_trubitsindanil.data.*
import com.example.playlist_maker_android_trubitsindanil.data.dto.*
import com.example.playlist_maker_android_trubitsindanil.domain.NetworkClient


class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(request: Any): TracksSearchResponse {
        val searchList = storage.search((request as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}