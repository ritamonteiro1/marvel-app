package daniel.lop.io.marvelappstarter.repository

import daniel.lop.io.marvelappstarter.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi,
) {
    suspend fun getCharacterList(nameStartsWith: String? = null) =
        api.getCharacterList(nameStartsWith)

    suspend fun getComicList(characterId: Int) = api.getComicList(characterId)
}