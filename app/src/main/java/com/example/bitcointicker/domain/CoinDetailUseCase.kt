package com.example.bitcointicker.domain

import com.example.bitcointicker.data.dto.CoinDetailDto
import com.example.bitcointicker.data.repository.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinDetailUseCase @Inject constructor(private val bitcoinRepository: BitcoinRepository){

    fun getCoinDetail(id: String): Flow<BaseResult<CoinDetailDto>> {
        return flow {
            val detail = bitcoinRepository.getCoinDetail(id)
            if(detail.isSuccessful && detail.code() == 200){
                emit(
                    BaseResult.Success(detail.body() ?: CoinDetailDto())
                )
            }
        }
    }
}