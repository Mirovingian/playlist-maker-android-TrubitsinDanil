package com.example.playlist_maker_android_trubitsindanil.data.impl

import com.example.playlist_maker_android_trubitsindanil.data.DatabaseMock
import com.example.playlist_maker_android_trubitsindanil.data.database.AppDatabase
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.toSearchHistoryEntity
import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(private val database: AppDatabase): SearchHistoryRepository {

    override suspend fun getHistory(): List<String> {
        return database.SearchHistoryDao().getHistory().map { it.toString() }
    }

    override suspend fun addToHistory(word: String) {
        database.SearchHistoryDao().insert(item = word.toSearchHistoryEntity())
    }
}