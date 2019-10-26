package br.com.beertime.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.com.beertime.R
import br.com.beertime.databinding.ActivityBeerListBinding
import br.com.beertime.model.StatusRequest
import br.com.beertime.viewmodel.BeerListViewModel
import kotlinx.android.synthetic.main.activity_beer_list.*

class BeerListActivity : AppCompatActivity() {

    lateinit var viewModel: BeerListViewModel
    lateinit var binding: ActivityBeerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BeerListViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_list)
        binding.viewModel = viewModel

        viewModel.status.observe(this, Observer {
            if(it != null) {
                when(it) {
                    StatusRequest.ERROR -> {
                        textView.visibility = View.GONE
                        textView2.visibility = View.GONE
                        textView3.visibility = View.VISIBLE
                    }
                    StatusRequest.LOADING -> {
                        textView.visibility = View.VISIBLE
                        textView2.visibility = View.GONE
                        textView3.visibility = View.GONE
                    }
                    StatusRequest.SUCCESS -> {
                        textView.visibility = View.GONE
                        textView2.visibility = View.VISIBLE
                        textView3.visibility = View.GONE
                    }
                }
            }
        })
        viewModel.loadBeerList()
    }
}
