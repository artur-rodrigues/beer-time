package br.com.beertime.infra.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.DataSource.Factory
import br.com.beertime.infra.datasource.BeerDataSource
import br.com.beertime.model.Beer

/**
 * Created by Artur on 27/10/2019.
 */
class BeerDataFactory : Factory<Long, Beer>() {

    val dataSourceLiveData = MutableLiveData<BeerDataSource>()
    private lateinit var beerDataSource: BeerDataSource

    override fun create(): DataSource<Long, Beer> {
        beerDataSource = BeerDataSource()
        dataSourceLiveData.postValue(beerDataSource)
        return beerDataSource
    }
}