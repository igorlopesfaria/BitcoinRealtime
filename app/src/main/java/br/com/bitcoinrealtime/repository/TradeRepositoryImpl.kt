package br.com.bitcoinrealtime.repository

import br.com.bitcoinrealtime.constants.CHAT_SERVER_URL
import br.com.bitcoinrealtime.repository.data.ResultWebsockets
import br.com.bitcoinrealtime.repository.remote.model.response.TradeItemResponse
import br.com.bitcoinrealtime.repository.remote.service.TradeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request

class TradeRepositoryImpl(
    private val tradeService: TradeService,
    private val client: OkHttpClient
) : TradeRepository {

    override suspend fun fetchTradeInfoRealTime(): Flow<ResultWebsockets<TradeItemResponse>> =
        flow {
            createWebsockets()
            tradeService.stateWebSocket.collect { resultWebsockets ->
                emit(resultWebsockets)
            }
        }

    private fun createWebsockets() {
        client.newWebSocket(
            request = Request.Builder().url(CHAT_SERVER_URL).build(),
            listener = tradeService
        )
        client.dispatcher.executorService.shutdown()
    }


}