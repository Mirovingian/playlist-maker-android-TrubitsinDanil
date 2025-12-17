package com.example.playlist_maker_android_trubitsindanil.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey
    val text: String,
    val timestamp: Long = System.currentTimeMillis() // Чтобы сортировать по времени добавления
) {
    override fun toString(): String {
        return text
    }
}

fun String.toSearchHistoryEntity(): SearchHistoryEntity {
    return SearchHistoryEntity(
        text = this,
        timestamp = System.currentTimeMillis()
    )
}

