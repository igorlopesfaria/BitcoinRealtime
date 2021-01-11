package br.com.bitcoinrealtime.koin

import br.com.bitcoinrealtime.feature.trade.viewmodel.TradeViewModel
import br.com.bitcoinrealtime.repository.TradeRepository
import br.com.bitcoinrealtime.repository.TradeRepositoryImpl
import br.com.bitcoinrealtime.repository.remote.service.TradeService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { createOkttp() }
    single { createTradeService() }
    single<TradeRepository> {
        TradeRepositoryImpl(get(), get())
    }
    viewModel { provideTradeViewModel(get()) }
}

fun provideTradeViewModel(repository: TradeRepository): TradeViewModel =
    TradeViewModel(repository)

fun createTradeService() = TradeService()

private fun createOkttp() =
    OkHttpClient.Builder()
        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()



private const val DEFAULT_TIMEOUT: Long = 10