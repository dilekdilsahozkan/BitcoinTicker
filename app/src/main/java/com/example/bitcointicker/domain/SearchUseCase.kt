package com.example.bitcointicker.domain

import com.example.bitcointicker.data.dto.CoinDto
import com.example.bitcointicker.data.repository.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val bitcoinRepository: BitcoinRepository) {

    fun getCoins(): Flow<BaseResult<List<CoinDto>>> {
        return flow {
            val search = bitcoinRepository.getCoins()
            if(search.isSuccessful && search.code() == 200){
                emit(
                    BaseResult.Success(search.body() ?: listOf())
                )
            }
        }
    }
}