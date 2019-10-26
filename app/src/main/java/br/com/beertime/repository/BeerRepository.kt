package br.com.beertime.repository

import br.com.beertime.model.Beer
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Artur on 26/10/2019.
 */
interface BeerRepository {

    @GET("beers")
    fun getBeerList(): Deferred<Response<MutableList<Beer>>>
}