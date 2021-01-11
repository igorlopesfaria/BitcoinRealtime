package br.com.bitcoinrealtime.feature.trade.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.bitcoinrealtime.koin.appModule
import br.com.bitcoinrealtime.databinding.TradeActivityBinding
import br.com.bitcoinrealtime.feature.trade.model.TradeItem
import br.com.bitcoinrealtime.feature.trade.state.TradeUiState
import br.com.bitcoinrealtime.feature.trade.viewmodel.TradeViewModel
import kotlinx.coroutines.flow.collect
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.androidx.viewmodel.ext.android.viewModel

class TradeActivity : AppCompatActivity() {
    private lateinit var binding: TradeActivityBinding
    private val viewModel:TradeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(appModule)
        binding = TradeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFlow()
        viewModel.loadTradeBitcoin()
    }

    private fun setupFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateTrade.collect { uiState ->
                when (uiState) {
                    is TradeUiState.Success -> updateData(uiState.tradeItem)
                    is TradeUiState.Start -> showLoading()
                    is TradeUiState.ServerError -> showError()
                    is TradeUiState.Loading -> showLoading()
                }
            }
        }
    }

    private fun updateData(tradeItem: TradeItem) {
        binding.output.text = tradeItem.priceFormattedWithSymbol()
    }

    private fun showError() {
        binding.output.text = "Error"
    }

    private fun showLoading() {
        binding.output.text = "Loading"
    }

    override fun onDestroy() {
        unloadKoinModules(appModule)
        super.onDestroy()
    }

}