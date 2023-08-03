package com.example.bitcointicker.data.api

import com.example.bitcointicker.data.dto.CoinDetailDto
import com.example.bitcointicker.data.dto.CoinDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BitcoinApi {

    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") includePlatform: String = "usd",
        @Query("price_change_percentage") changePercent: String = "1h"
    ): Response<List<CoinDto>>

    @GET("coins/{id}")
    suspend fun getCoinDetail(@Path("id") id : String): Response<CoinDetailDto>
}