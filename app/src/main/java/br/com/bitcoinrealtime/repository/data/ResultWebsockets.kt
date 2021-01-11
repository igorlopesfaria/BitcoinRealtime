package br.com.bitcoinrealtime.repository.data

sealed class ResultWebsockets<out R> {
    object Connecting : ResultWebsockets<Nothing>()
    object Subscribing : ResultWebsockets<Nothing>()
    data class Success<out T>(val data: T) : ResultWebsockets<T>()
    data class Failure(val error: Error) : ResultWebsockets<Nothing>()
    object ConnectionClosed : ResultWebsockets<Nothing>()

    fun <C> transform(transformation: (R) -> C): ResultWebsockets<C> {
        return when (this) {
            is Success -> Success(
                transformation(data)
            )
            is Failure -> Failure(
                error
            )
            is Subscribing -> Subscribing
            is Connecting -> Connecting
            is ConnectionClosed -> ConnectionClosed
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Exception[exception=$error]"
            is ConnectionClosed -> "ConnectionClosed"
            is Subscribing -> "Subscribing"
            Connecting -> "Loading"
        }
    }
}

val ResultWebsockets<*>.succeeded
    get() = this is ResultWebsockets.Success && data != null
