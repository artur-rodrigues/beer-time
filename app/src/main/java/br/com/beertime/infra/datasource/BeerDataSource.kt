package br.com.beertime.infra.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.beertime.infra.factory.RepositoryFactory
import br.com.beertime.model.Beer
import br.com.beertime.model.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Artur on 27/10/2019.
 */
class BeerDataSource : PageKeyedDataSource<Long, Beer>() {

    private val repository = RepositoryFactory.buildBeerRepository()
    val networkState = MutableLiveData<NetworkResponse>()
    private val errorMsg = "Ocorreu um problema ineperado"

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Beer>
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            networkState.postValue(NetworkResponse.LOADING)

            try {
                val response = repository.getPagedBeerList(1).await()

                if(response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onResult(response.body()!!, null, 2)
                        networkState.postValue(NetworkResponse.SUCCESS)
                    } else {
                        networkState.postValue(NetworkResponse.responseErro(errorMsg))
                    }
                } else {
//                    networkState.value = br.com.beertime.model.NetworkResponse.Status.ERROR
                    networkState.postValue(NetworkResponse.responseErro(errorMsg))
                    // Levantar exceção
                }
            } catch (e: Exception) {
//                networkState.value = br.com.beertime.model.NetworkResponse.Status.ERROR
                networkState.postValue(NetworkResponse.responseErro(errorMsg))
                // Levantar exceção
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Beer>) {
        GlobalScope.launch(Dispatchers.Main) {
            networkState.postValue(NetworkResponse.LOADING)

            try {
                val response = repository.getPagedBeerList(params.key).await()

                if(response.isSuccessful) {
                    if (response.body() != null) {
                        val nextKey = if(response.body()!!.size < 25) null else params.key + 1
                        callback.onResult(response.body()!!, nextKey)
                        networkState.postValue(NetworkResponse.SUCCESS)
                    } else {
                        networkState.postValue(NetworkResponse.responseErro(errorMsg))
                    }
                } else {
                    networkState.postValue(NetworkResponse.responseErro(errorMsg))
                }
            } catch (e: Exception) {
                networkState.postValue(NetworkResponse.responseErro(errorMsg))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Beer>) {

    }

}