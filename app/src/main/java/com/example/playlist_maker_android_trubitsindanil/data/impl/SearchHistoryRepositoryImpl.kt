package com.example.playlist_maker_android_trubitsindanil.data.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.playlist_maker_android_trubitsindanil.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow


class SearchHistoryRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SearchHistoryRepository {

    private val searchHistoryPreferences = SearchHistoryPreferences(dataStore)

    override fun getHistory(): Flow<List<String>> {
        return searchHistoryPreferences.getEntries()
    }

    override suspend fun addToHistory(word: String) {
        searchHistoryPreferences.addEntry(word)
    }
}