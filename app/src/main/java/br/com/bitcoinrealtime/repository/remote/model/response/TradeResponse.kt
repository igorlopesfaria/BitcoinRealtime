package br.com.bitcoinrealtime.repository.remote.model.response

import com.google.gson.annotations.SerializedName


data class TradeResponse(
    @SerializedName(value = "data")
    val data: List<TradeItemResponse>,
    @SerializedName(value = "type")
    val type: String
)

data class TradeItemResponse(
    @SerializedName(value = "p")
    val price: Long,
    @SerializedName(value = "t")
    val time: Long,
    @SerializedName(value = "s")
    val symbol: String
)

//"data":[{"c":null,"p":222457,"s":"BINANCE:BTCBRL","t":1610205469610,"v":0.07274}],"type":"trade"