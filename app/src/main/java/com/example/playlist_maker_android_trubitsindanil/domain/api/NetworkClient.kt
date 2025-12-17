package com.example.playlist_maker_android_trubitsindanil.domain.api

import com.example.playlist_maker_android_trubitsindanil.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}