package com.example.playlist_maker_android_trubitsindanil.creator


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.playlist_maker_android_trubitsindanil.data.api.ITunesApiService
import com.example.playlist_maker_android_trubitsindanil.data.database.AppDatabase
import com.example.playlist_maker_android_trubitsindanil.data.impl.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.impl.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.impl.TracksRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_trubitsindanil.domain.api.NetworkClient
import com.example.playlist_maker_android_trubitsindanil.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "search_history_preferences")

object Creator {
    private const val BASE_URL = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesApiService::class.java)
    private val networkClient: NetworkClient = RetrofitNetworkClient(iTunesService)

    private lateinit var database: AppDatabase
    private lateinit var dataStore: DataStore<Preferences>

    fun initDatabase(context: Context) {
        if (!this::database.isInitialized) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "playlist-maker-db"
            ).build()
        }

        if (!this::dataStore.isInitialized) {
            dataStore = context.dataStore
        }
    }

    private val tracksRepositoryImpl by lazy {
        TracksRepositoryImpl(database, networkClient)
    }

    private val playlistsRepositoryImpl by lazy {
        PlaylistsRepositoryImpl(database)
    }

    private val searchHistoryRepositoryImpl by lazy {
        SearchHistoryRepositoryImpl(dataStore)
    }

    fun getTracksRepository(): TracksRepository {
        return tracksRepositoryImpl
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        return playlistsRepositoryImpl
    }

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        return searchHistoryRepositoryImpl
    }
}