package daniel.lop.io.marvelappstarter.ui.marvel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.lop.io.marvelappstarter.data.model.character.CharacterResult
import daniel.lop.io.marvelappstarter.repository.MarvelRepository
import daniel.lop.io.marvelappstarter.ui.marvel.state.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class SearchCharacterViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository

) : ViewModel() {
    private val _searchCharacter =
        MutableStateFlow<ResourceState<CharacterResult>>(ResourceState.Empty())
    val searchCharacter: StateFlow<ResourceState<CharacterResult>> = _searchCharacter

    fun fetch(nameStartsWith: String) = viewModelScope.launch {
        safeFetch(nameStartsWith)
    }

    private suspend fun safeFetch(nameStartsWith: String) {
        _searchCharacter.value = ResourceState.Loading()
        try {
            val response = marvelRepository.getCharacterList(nameStartsWith)
            _searchCharacter.value = handleResponse(response)

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _searchCharacter.value = ResourceState.Error("Erro na rede")
                else -> _searchCharacter.value = ResourceState.Error("Erro de conversão")
            }
        }
    }

    private fun handleResponse(response: Response<CharacterResult>): ResourceState<CharacterResult> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}