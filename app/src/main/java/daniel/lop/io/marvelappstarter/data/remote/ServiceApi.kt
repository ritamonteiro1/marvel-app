package daniel.lop.io.marvelappstarter.data.remote

import daniel.lop.io.marvelappstarter.data.model.character.CharacterResult
import daniel.lop.io.marvelappstarter.data.model.comic.ComicResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun getCharacterList(
        @Query("nameStartWith") nameStartWith: String? = null
    ): Response<CharacterResult>

    @GET("characters/{characterId}/comics")
    suspend fun getComicList(
        @Path(value = "characterId", encoded = true) characterId: Int
    ): Response<ComicResult>

}