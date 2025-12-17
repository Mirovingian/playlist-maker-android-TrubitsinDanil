//package com.example.playlist_maker_android_trubitsindanil.creator
//
//import com.example.playlist_maker_android_trubitsindanil.data.dto.TrackDto
//
//class Storage {
//    private val listTracks = listOf(
//        TrackDto(
//            trackName = "Yesterday (Remastered 2009)",
//            artistName = "The Beatles",
//            trackTimeMillis = 175000
//        ),
//        TrackDto(
//            trackName = "Here Comes The Sun",
//            artistName = "The Beatles",
//            trackTimeMillis = 241000
//        ),
//        TrackDto(
//            trackName = "Не смотри назад",
//            artistName = "Ария",
//            trackTimeMillis = 312000 // 5:12
//        ),
//        TrackDto(
//            trackName = "Звезда по имени Солнце",
//            artistName = "Кино",
//            trackTimeMillis = 225000
//        ),
//        TrackDto(
//            trackName = "Лондон",
//            artistName = "Аквариум",
//            trackTimeMillis = 272000
//        ),
//        TrackDto(
//            trackName = "На заре",
//            artistName = "Альянс",
//            trackTimeMillis = 230000
//        ),
//        TrackDto(
//            trackName = "Перемен",
//            artistName = "Кино",
//            trackTimeMillis = 296000
//        ),
//        TrackDto(
//            trackName = "Розовый фламинго",
//            artistName = "Сплин",
//            trackTimeMillis = 195000
//        ),
//        TrackDto(
//            trackName = "Танцевать",
//            artistName = "Мельница",
//            trackTimeMillis = 222000
//        ),
//        TrackDto(
//            trackName = "Чёрный бумер",
//            artistName = "Серега",
//            trackTimeMillis = 241000
//        )
//    )
//
//    fun search(request: String): List<TrackDto> {
//        val result = listTracks.filter {
//            it.trackName
//                .lowercase()
//                .contains(request.lowercase())
//        }
//        return result
//    }
//}