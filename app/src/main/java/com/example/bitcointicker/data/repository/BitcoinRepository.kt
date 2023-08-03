package com.example.bitcointicker.data.repository

import com.example.bitcointicker.data.dto.CoinDetailDto
import com.example.bitcointicker.data.dto.CoinDto
import retrofit2.Response

interface BitcoinRepository {
    suspend fun getCoins(): Response<List<CoinDto>>
    suspend fun getCoinDetail(id: String): Response<CoinDetailDto>
}




