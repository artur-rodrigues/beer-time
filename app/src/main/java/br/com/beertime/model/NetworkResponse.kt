package br.com.beertime.model

/**
 * Created by Artur on 27/10/2019.
 */
class NetworkResponse
    private constructor(
        val status: Status,
        val msg: String
    ) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        val LOADING = NetworkResponse(Status.LOADING, "Loading...")
        val SUCCESS = NetworkResponse(Status.SUCCESS, "Success")

        fun responseErro(msg: String) = NetworkResponse(Status.ERROR, msg)
    }
}