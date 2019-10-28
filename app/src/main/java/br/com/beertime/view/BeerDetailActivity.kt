package br.com.beertime.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.beertime.R
import br.com.beertime.databinding.ActivityBeerDetailBinding
import br.com.beertime.model.Beer
import br.com.beertime.utils.loadImageForDetail

class BeerDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityBeerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_detail)
        val beer = intent.extras?.get("beer") as Beer

        binding.labelBeerName.text = beer.name
        binding.labelBeerDescription.text = beer.description
        beer.imageUrl?.let { loadImageForDetail(it, binding.imgDetailBeer) }
    }
}
