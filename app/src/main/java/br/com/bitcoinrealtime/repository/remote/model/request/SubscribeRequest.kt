package br.com.bitcoinrealtime.repository.remote.model.request

data class SubscribeRequest(
    val type: String = "subscribe",
    val symbol: String
)
