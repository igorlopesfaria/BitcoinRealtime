package br.com.bitcoinrealtime.feature.trade.model

import android.content.Context
import android.os.Parcelable
import br.com.bitcoinrealtime.extension.dateFormat
import br.com.bitcoinrealtime.extension.formatMoney
import br.com.bitcoinrealtime.repository.remote.model.response.TradeItemResponse
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

@Parcelize
class TradeItem(
    private val price: Long,
    private val time: Long,
    var symbol: TradeSymbolType
): Parcelable {
    constructor(tradeItemResponse: TradeItemResponse) : this(
        price = tradeItemResponse.price,
        time = tradeItemResponse.time,
        symbol = TradeSymbolType.from(tradeItemResponse.symbol)
    )
    fun priceFormattedWithSymbol(): String =
        price.formatMoney()

    fun timeDateFormatted(): String = dateFormat.format(time)
}

enum class TradeSymbolType(val type: String) {
    BTCBRL("BINANCE:BTCBRL"),
    BTCUSDT("BINANCE:BTCUSDT");

    companion object {
        fun from(type: String): TradeSymbolType = values().find { it.type == type }!!
    }
}


//"data":[{"c":null,"p":222457,"s":"BINANCE:BTCBRL","t":1610205469610,"v":0.07274}],"type":"trade"