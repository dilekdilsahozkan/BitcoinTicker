package com.example.bitcointicker.data.dto

import java.io.Serializable

data class CoinDetailDto (
    var hashing_algorithm: String? = null,
    var description: Desc? = null
)

data class Desc(
    val en: String? = null
) : Serializable
