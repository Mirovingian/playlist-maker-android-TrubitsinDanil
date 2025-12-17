package com.example.playlist_maker_android_trubitsindanil.creator

import android.content.Context
import androidx.room.Room
import com.example.playlist_maker_android_trubitsindanil.data.DatabaseMock
import com.example.playlist_maker_android_trubitsindanil.data.impl.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.impl.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.impl.TracksRepositoryImpl
import com.example.playlist_maker_android_trubitsindanil.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_trubitsindanil.domain.api.NetworkClient
import com.example.playlist_maker_android_trubitsindanil.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.SearchHistoryRepository
import com.example.playlist_maker_android_trubitsindanil.domain.api.TrackSearchInteractor
import com.example.playlist_maker_android_trubitsindanil.domain.api.TracksRepository
import com.example.playlist_maker_android_trubitsindanil.domain.impl.TrackSearchInteractorImpl
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.playlist_maker_android_trubitsindanil.data.api.ITunesApiService
import com.example.playlist_maker_android_trubitsindanil.data.database.AppDatabase


object Creator {
    private const val BASE_URL = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesApiService::class.java)
    private val networkClient: NetworkClient = RetrofitNetworkClient(iTunesService)

    private val database: DatabaseMock = DatabaseMock(networkClient)

//    private lateinit var database: AppDatabase
//
//    fun initDatabase(context: Context) {
//        database = Room.databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "playlist-maker-db"
//        ).build()
//    }

    private val tracksRepositoryImpl = TracksRepositoryImpl(database)
    private val playlistsRepositoryImpl = PlaylistsRepositoryImpl(database)
    private val searchHistoryRepositoryImpl = SearchHistoryRepositoryImpl(database)
    fun getTracksRepository(): TracksRepository {
        return tracksRepositoryImpl
    }

    fun getPlaylistsRepository() : PlaylistsRepository {
        return playlistsRepositoryImpl
    }

    fun getSearchHistoryRepository() : SearchHistoryRepository {
        return searchHistoryRepositoryImpl
    }


}