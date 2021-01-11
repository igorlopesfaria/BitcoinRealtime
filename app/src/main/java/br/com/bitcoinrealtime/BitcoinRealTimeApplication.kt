package br.com.bitcoinrealtime

import android.app.Application
import br.com.bitcoinrealtime.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BitcoinRealTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BitcoinRealTimeApplication)
            koin.loadModules(
                listOf(
                    appModule
                )
            )
            koin.createRootScope()
        }
    }
}