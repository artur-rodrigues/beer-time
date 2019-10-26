package br.com.beertime.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.beertime.model.Beer

/**
 * Created by Artur on 26/10/2019.
 */
class BeerListViewModel : ViewModel() {

    val beerListLiveData = MutableLiveData<MutableList<Beer>>()
}