package com.example.bitcointicker.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.bitcointicker.data.dto.CoinDto
import com.example.bitcointicker.domain.BaseResult
import com.example.bitcointicker.domain.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase
) : BaseViewModel<List<CoinDto>>() {

    private val _searchState: MutableStateFlow<ViewState<List<CoinDto>>> =
        MutableStateFlow(ViewState.Idle())
    val searchState: StateFlow<ViewState<List<CoinDto>>> = _searchState

    fun getCoins() {
        viewModelScope.launch {
            searchUseCase.getCoins()
                .onStart {
                    _searchState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _searchState.value = ViewState.Error(message = exception.message)
                }
                .collect { baseResult->
                    when(baseResult){
                        is BaseResult.Success ->
                            _searchState.value =  ViewState.Success(baseResult.data)
                        is BaseResult.Error -> {
                            _searchState.value = ViewState.Error()
                        }
                    }
                }
        }
    }
}