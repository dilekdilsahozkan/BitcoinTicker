package com.example.bitcointicker.data.repository

import com.example.bitcointicker.data.api.BitcoinApi
import com.example.bitcointicker.data.dto.CoinDetailDto
import com.example.bitcointicker.data.dto.CoinDto
import retrofit2.Response
import javax.inject.Inject

class BitcoinRepositoryImpl @Inject constructor(private val service: BitcoinApi): BitcoinRepository{
    override suspend fun getCoins(): Response<List<CoinDto>> = service.getCoins()
    override suspend fun getCoinDetail(id: String): Response<CoinDetailDto> = service.getCoinDetail(id)
}