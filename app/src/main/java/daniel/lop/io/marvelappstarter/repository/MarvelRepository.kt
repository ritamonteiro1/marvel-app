package daniel.lop.io.marvelappstarter.repository

import daniel.lop.io.marvelappstarter.data.cache.MarvelDao
import daniel.lop.io.marvelappstarter.data.remote.ServiceApi
import daniel.lop.io.marvelappstarter.data.model.character.CharacterModel
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
) {
    suspend fun getCharacterList(nameStartsWith: String? = null) = api.getCharacterList(nameStartsWith)
    suspend fun getComicList(characterId: Int) = api.getComicList(characterId)

    suspend fun insert(characterModel: CharacterModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel: CharacterModel) = dao.delete(characterModel)
}