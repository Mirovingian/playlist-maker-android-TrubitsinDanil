package com.example.playlist_maker_android_trubitsindanil.data.network

import com.example.playlist_maker_android_trubitsindanil.*
import com.example.playlist_maker_android_trubitsindanil.data.*
import com.example.playlist_maker_android_trubitsindanil.data.dto.*


class RetrofitNetworkClient : NetworkClient {
    override fun doRequest(dto: Any): BaseResponse {
        return TracksSearchResponse(listOf())
    }
}

