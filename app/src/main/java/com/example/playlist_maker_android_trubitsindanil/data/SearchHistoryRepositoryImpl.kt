package com.example.playlist_maker_android_trubitsindanil.data

import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(private val scope: CoroutineScope): SearchHistoryRepository {
    private val database = DatabaseMock(scope = scope)

    override suspend fun getHistory(): List<String> {
        return database.getHistory()
    }

    override fun addToHistory(word: String) {
        database.addToHistory(word = word)
    }
}