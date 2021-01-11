package br.com.bitcoinrealtime.repository

import br.com.bitcoinrealtime.repository.data.ResultWebsockets
import br.com.bitcoinrealtime.repository.remote.model.response.TradeItemResponse
import kotlinx.coroutines.flow.Flow

interface TradeRepository{
    suspend fun fetchTradeInfoRealTime(): Flow<ResultWebsockets<TradeItemResponse>>
}