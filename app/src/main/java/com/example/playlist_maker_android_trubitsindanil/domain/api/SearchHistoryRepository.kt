package com.example.playlist_maker_android_trubitsindanil.domain.api

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getHistory(): Flow<List<String>>

    suspend fun addToHistory(word: String)
}