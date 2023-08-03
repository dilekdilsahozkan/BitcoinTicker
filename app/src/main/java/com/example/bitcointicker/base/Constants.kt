package com.example.bitcointicker.base

object Constants {
    val BASE_URL= "https://api.coingecko.com/api/v3/"

    private const val DAILY_TITLE = "Daily price change"
    private const val MONTHLY_TITLE = "Monthly price change"
    private const val FOURTEEN_DAY_TITLE = "14-Day price change"
    private const val MAX_TITLE = "Max price change"


    val timeIntervalList = listOf<TimeInterval>(
        TimeInterval("1", DAILY_TITLE, true),
        TimeInterval("14", FOURTEEN_DAY_TITLE, false),
        TimeInterval("30", MONTHLY_TITLE, false),
        TimeInterval("max", MAX_TITLE, false),
    )
}

data class TimeInterval(
    val timeZone: String,
    val title: String,
    var isSelected: Boolean
)
