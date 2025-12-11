package com.example.playlist_maker_android_trubitsindanil.domain.api

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    suspend fun getHistory(): List<String>

    fun addToHistory(word: String)
}