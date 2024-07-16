package com.kaleiczyk.network.transferGo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CurrencyDTO {
    @SerialName("pln")
    PLN,

    @SerialName("eur")
    EUR,

    @SerialName("uah")
    UAH,

    @SerialName("gbr")
    GBR,
}
