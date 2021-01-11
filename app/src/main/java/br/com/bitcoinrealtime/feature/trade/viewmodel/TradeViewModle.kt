package br.com.bitcoinrealtime.feature.trade.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.bitcoinrealtime.feature.base.BaseViewModel
import br.com.bitcoinrealtime.feature.trade.model.TradeItem
import br.com.bitcoinrealtime.feature.trade.state.TradeUiState
import br.com.bitcoinrealtime.repository.TradeRepository
import br.com.bitcoinrealtime.repository.data.ResultWebsockets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TradeViewModel(private val tradeRepository: TradeRepository): BaseViewModel() {

    private val _stateTrade =
        MutableStateFlow<TradeUiState>(TradeUiState.Start)
    val stateTrade: StateFlow<TradeUiState> get() = _stateTrade

    fun loadTradeBitcoin() {
        viewModelScope.launch(Dispatchers.IO) {
            tradeRepository.fetchTradeInfoRealTime().collect { resultRepository ->
                when(resultRepository) {
                    is ResultWebsockets.Success -> {
                        _stateTrade.value = TradeUiState.Success(TradeItem(resultRepository.data))
                    }
                    is ResultWebsockets.Failure -> {
                        _stateTrade.value = TradeUiState.ServerError
                    }
                    is ResultWebsockets.Connecting, ResultWebsockets.Subscribing -> {
                        _stateTrade.value = TradeUiState.Loading
                    }
                    else -> {}
                }

            }
        }
    }
}
