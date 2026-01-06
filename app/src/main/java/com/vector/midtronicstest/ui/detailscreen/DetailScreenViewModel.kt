package com.vector.midtronicstest.ui.detailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vector.midtronicstest.data.model.Country
import com.vector.midtronicstest.data.remote.ApiService
import com.vector.midtronicstest.data.repository.OnlineRepository
import com.vector.midtronicstest.ui.Detail
import com.vector.midtronicstest.ui.homescreen.HomeUiActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val onlineRepository: OnlineRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = savedStateHandle.toRoute<Detail>()

    private val _state = MutableStateFlow(DetailScreenUiState())

    val state = _state.asStateFlow()



    init {
        loadCountryData()
    }

    private fun loadCountryData() {
        viewModelScope.launch {
            val result = onlineRepository.getSpecificCountry(navArgs.name)

            if(result.isFailure) {
                //emitir error
            } else {
                val data = result.getOrNull()
                _state.update { currentState ->
                    currentState.copy(
                        country = data
                    )
                }
            }

        }
    }
}

data class DetailScreenUiState(
    val country: Country? = null
)
sealed interface DetailScreenEvent{
    data object Error: DetailScreenEvent
    data object Empty: DetailScreenEvent
}