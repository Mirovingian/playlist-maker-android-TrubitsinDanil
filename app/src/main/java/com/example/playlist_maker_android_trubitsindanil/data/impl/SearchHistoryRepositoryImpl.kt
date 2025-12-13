package com.example.playlist_maker_android_trubitsindanil.data.impl

import com.example.playlist_maker_android_trubitsindanil.data.DatabaseMock
import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(private val database: DatabaseMock): SearchHistoryRepository {

    override suspend fun getHistory(): List<String> {
        return database.getHistory()
    }

    override fun addToHistory(word: String) {
        database.addToHistory(word = word)
    }
}