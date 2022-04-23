package daniel.lop.io.marvelappstarter.ui.marvel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import daniel.lop.io.marvelappstarter.R
import daniel.lop.io.marvelappstarter.databinding.FragmentListCharacterBinding
import daniel.lop.io.marvelappstarter.ui.marvel.adapters.CharacterListAdapter
import daniel.lop.io.marvelappstarter.ui.marvel.state.ResourceState
import daniel.lop.io.marvelappstarter.ui.marvel.viewmodel.ListCharacterViewModel
import daniel.lop.io.marvelappstarter.utils.hide
import daniel.lop.io.marvelappstarter.utils.show
import daniel.lop.io.marvelappstarter.utils.toast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ListCharacterFragment : BaseFragment<FragmentListCharacterBinding, ListCharacterViewModel>() {
    override val viewModel: ListCharacterViewModel by viewModels()
    private val characterListAdapter by lazy { CharacterListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setOnClickListenerCharacterItem()
        setupObserver()
    }

    private fun setupObserver() = lifecycleScope.launch {
        viewModel.characterList.collect{ resourceState ->
            when(resourceState){
                is ResourceState.Success -> {
                    resourceState.data?.let { values ->
                        binding.progressCircular.hide()
                        characterListAdapter.characterModels = values.data.results.toList()
                    }

                }
                is ResourceState.Error -> {
                    binding.progressCircular.hide()
                    resourceState.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("ListCharacterFragment").e("Error -> $message")
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressCircular.show()
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListenerCharacterItem() {
        characterListAdapter.setOnClickListener { characterModel ->
            val action =
                ListCharacterFragmentDirections.actionListCharacterFragmentToDetailsCharacterFragment(
                    characterModel
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvCharacters.apply {
            adapter = characterListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListCharacterBinding =
        FragmentListCharacterBinding.inflate(inflater, container, false)
}