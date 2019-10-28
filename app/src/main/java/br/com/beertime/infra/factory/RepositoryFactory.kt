package br.com.beertime.infra.factory

import br.com.beertime.infra.repository.BeerRepository
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by Artur on 26/10/2019.
 */
object RepositoryFactory {

    private fun buildRetrofit(): Retrofit {
        val mapper = ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return Retrofit.Builder()
            .baseUrl("https://api.punkapi.com/v2/")
            .client(OkHttpClient.Builder().build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .build()
    }

    fun buildBeerRepository(): BeerRepository = buildRetrofit().create(BeerRepository::class.java)
}