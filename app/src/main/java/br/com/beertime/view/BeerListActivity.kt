package br.com.beertime.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.com.beertime.R
import br.com.beertime.databinding.ActivityBeerListBinding
import br.com.beertime.viewmodel.BeerListViewModel

class BeerListActivity : AppCompatActivity() {

    lateinit var viewModel: BeerListViewModel
    lateinit var binding: ActivityBeerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BeerListViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_list)
        binding.viewModel = viewModel
    }
}
