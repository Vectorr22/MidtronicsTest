package com.vector.midtronicstest.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vector.midtronicstest.data.model.CountryUi
import com.vector.midtronicstest.data.repository.OnlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val onlineRepository: OnlineRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())

    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<HomeUiActions>()

    val events = _events.asSharedFlow()

    init {
        loadCountriesData()
    }

    private fun loadCountriesData() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val result = onlineRepository.getAllCountriesUi()

            if(result.isFailure) {
                _events.emit(HomeUiActions.Error)
            } else {
                val data = result.getOrNull()
                if(data == null) {
                    _events.emit(HomeUiActions.EmptyData)
                } else {
                    _state.update { currentState ->
                        currentState.copy(
                            listOfCountries = data,
                            isLoading = false
                        )
                    }
                }
            }
        }


    }
}


data class HomeScreenState(
    val listOfCountries: List<CountryUi> = listOf(),
    val isLoading: Boolean = false
)

sealed interface HomeUiActions{
    data object Error: HomeUiActions
    data object EmptyData: HomeUiActions
}