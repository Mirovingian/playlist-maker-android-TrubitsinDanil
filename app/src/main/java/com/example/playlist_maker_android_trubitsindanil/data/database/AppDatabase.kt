package com.example.playlist_maker_android_trubitsindanil.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlist_maker_android_trubitsindanil.data.database.dao.PlaylistsDao
import com.example.playlist_maker_android_trubitsindanil.data.database.dao.TracksDao
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.PlaylistEntity
import com.example.playlist_maker_android_trubitsindanil.data.database.entity.TrackEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class
    ],
    version = 1, // Если приложение уже установлено, нужно удалить его или увеличить версию + миграция
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TracksDao(): TracksDao
    abstract fun PlaylistsDao(): PlaylistsDao
}