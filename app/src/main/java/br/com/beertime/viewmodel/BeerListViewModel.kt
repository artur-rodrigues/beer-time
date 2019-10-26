package br.com.beertime.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.beertime.factory.RepositoryFactory
import br.com.beertime.model.Beer
import br.com.beertime.model.StatusRequest
import kotlinx.coroutines.*

/**
 * Created by Artur on 26/10/2019.
 */
class BeerListViewModel : ViewModel() {

    private val repository = RepositoryFactory.buildBeerRepository()
    val beerListLiveData = MutableLiveData<MutableList<Beer>>()
    val status = MutableLiveData<StatusRequest>()

    fun loadBeerList() {
        GlobalScope.launch(Dispatchers.Main) {
            status.value = StatusRequest.LOADING

            try {
                val response = repository.getBeerList().await()

                if(response.isSuccessful) {
                    beerListLiveData.value = response.body()
                    status.value = StatusRequest.SUCCESS
                } else {
                    status.value = StatusRequest.ERROR
                    // Levantar exceção
                }
            } catch (e: Exception) {
                status.value = StatusRequest.ERROR
                // Levantar exceção
            }
        }
    }
}