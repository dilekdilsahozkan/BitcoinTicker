package com.example.bitcointicker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bitcointicker.data.dto.CoinDetailDto
import com.example.bitcointicker.domain.BaseResult
import com.example.bitcointicker.domain.CoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val coinDetailUseCase: CoinDetailUseCase
) : BaseViewModel<CoinDetailDto>() {

    private val _coinDetailState: MutableStateFlow<ViewState<CoinDetailDto>> =
        MutableStateFlow(ViewState.Idle())
    val coinDetailState: StateFlow<ViewState<CoinDetailDto>> = _coinDetailState

    fun getCoins(id: String) {
        viewModelScope.launch {
            coinDetailUseCase.getCoinDetail(id)
                .onStart {
                    _coinDetailState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _coinDetailState.value = ViewState.Error(message = exception.message)
                }
                .collect { baseResult->
                    when(baseResult){
                        is BaseResult.Success ->
                            _coinDetailState.value =  ViewState.Success(baseResult.data)
                        is BaseResult.Error -> {
                            _coinDetailState.value = ViewState.Error()
                        }
                    }
                }
        }
    }
}