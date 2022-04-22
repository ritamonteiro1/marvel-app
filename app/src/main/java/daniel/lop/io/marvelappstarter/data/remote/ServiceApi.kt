package daniel.lop.io.marvelappstarter.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun getCharacterList(
        @Query("nameStartWith") nameStartWith: String? = null
    )
}