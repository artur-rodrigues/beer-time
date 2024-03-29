package br.com.beertime.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.beertime.infra.factory.BeerDataFactory
import br.com.beertime.model.Beer
import br.com.beertime.model.NetworkResponse
import java.util.concurrent.Executors

/**
 * Created by Artur on 26/10/2019.
 */
class BeerListViewModel : ViewModel() {

    lateinit var networkResponse: LiveData<NetworkResponse>
    lateinit var beerPagedListLiveData: LiveData<PagedList<Beer>>
    lateinit var beerDataFactory: BeerDataFactory
    init {
        init()
    }

    private fun init() {
        beerDataFactory = BeerDataFactory()
        networkResponse = Transformations
            .switchMap(beerDataFactory.dataSourceLiveData) {
            it.networkState
        }

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(25)
                .setPrefetchDistance(2)
                .setPageSize(50).build()

        beerPagedListLiveData = LivePagedListBuilder(beerDataFactory, pagedListConfig)
            .setFetchExecutor(Executors.newFixedThreadPool(5))
            .build()
    }

    fun invalidate() {
        if(::beerDataFactory.isInitialized) {
            beerDataFactory.invalidate()
        }
    }
}