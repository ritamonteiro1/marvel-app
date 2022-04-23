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
class ListCharacterViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository
) : ViewModel() {

    private val _characterList =
        MutableStateFlow<ResourceState<CharacterResult>>(ResourceState.Loading())
    val characterList: StateFlow<ResourceState<CharacterResult>> = _characterList

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = marvelRepository.getCharacterList()
            _characterList.value = handleResponse(response)

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _characterList.value =
                    ResourceState.Error("Erro de conexão com a internet")
                else -> _characterList.value = ResourceState.Error("Falha de conversão de dados")
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