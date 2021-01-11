package br.com.bitcoinrealtime.feature.trade.state

import br.com.bitcoinrealtime.feature.trade.model.TradeItem


sealed class TradeUiState() {
    object Start : TradeUiState()
    object Loading : TradeUiState()
    data class Success(val tradeItem: TradeItem): TradeUiState()
    object ServerError: TradeUiState()
}
