package br.com.bitcoinrealtime.repository.remote.service

import android.util.Log
import br.com.bitcoinrealtime.repository.data.Error
import br.com.bitcoinrealtime.repository.data.ResultWebsockets
import br.com.bitcoinrealtime.repository.remote.model.response.TradeItemResponse
import br.com.bitcoinrealtime.repository.remote.model.response.TradeResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class TradeService: WebSocketListener() {
    private val _stateWebSocket =
        MutableStateFlow<ResultWebsockets<TradeItemResponse>>(ResultWebsockets.Connecting)
    val stateWebSocket: StateFlow<ResultWebsockets<TradeItemResponse>> get() = _stateWebSocket

    override fun onOpen(webSocket: WebSocket, response: Response) {
        _stateWebSocket.value = ResultWebsockets.Subscribing
        webSocket.send("{\"type\":\"subscribe\",\"symbol\":\"BINANCE:BTCBRL\"}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.i("Websocket onMessage", text)

        val tradeResponse: TradeResponse = Gson().fromJson(text, TradeResponse::class.java)


        _stateWebSocket.value = ResultWebsockets.Success(tradeResponse.data[0])
    }
    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        _stateWebSocket.value = ResultWebsockets.ConnectionClosed
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        _stateWebSocket.value = ResultWebsockets.Failure(Error.WebsocketError)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

}