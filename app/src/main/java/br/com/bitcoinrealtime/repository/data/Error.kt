package br.com.bitcoinrealtime.repository.data

sealed class Error {

    object WebsocketError : Error()

    object UnknownError : Error()

    object ItemNotFoundError : Error()

    object NetworkError : Error()

    data class ServerError<T>(val data: T) : Error()

    data class  DatabaseError<T>(val data: T) : Error()
}
