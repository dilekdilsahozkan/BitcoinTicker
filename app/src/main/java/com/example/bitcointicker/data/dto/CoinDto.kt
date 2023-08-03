package com.example.bitcointicker.data.dto

data class CoinDto (
    var id: String? = null,
    var symbol: String? = null,
    var name: String? = null,
    val image: String? = null,
    var current_price: Double? = null,
    var price_change_24h: Double? = null,
    var selected: Boolean = false
)

