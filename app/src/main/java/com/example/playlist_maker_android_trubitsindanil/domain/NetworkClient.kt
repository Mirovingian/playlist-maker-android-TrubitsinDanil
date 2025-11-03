package com.example.playlist_maker_android_trubitsindanil.domain

import com.example.playlist_maker_android_trubitsindanil.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}