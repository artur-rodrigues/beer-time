package br.com.beertime.infra.repository

import br.com.beertime.model.Beer
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Artur on 26/10/2019.
 */
interface BeerRepository {

    @GET("beers")
    fun getBeerList(): Deferred<Response<MutableList<Beer>>>

    @GET("beers")
    fun getPagedBeerList(@Query("page") page: Long): Deferred<Response<MutableList<Beer>>>
}