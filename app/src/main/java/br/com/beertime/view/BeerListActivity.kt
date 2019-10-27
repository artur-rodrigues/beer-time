package br.com.beertime.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.beertime.R
import br.com.beertime.adapter.BeerPageAdapter
import br.com.beertime.databinding.ActivityBeerListBinding
import br.com.beertime.viewmodel.BeerListViewModel


class BeerListActivity : AppCompatActivity() {

    lateinit var viewModel: BeerListViewModel
    lateinit var binding: ActivityBeerListBinding
    lateinit var adapter: BeerPageAdapter
    var hideProgress = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BeerListViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_list)
        binding.viewModel = viewModel
        binding.listBeer.layoutManager = LinearLayoutManager(applicationContext)

        adapter = BeerPageAdapter {
            print(it.name)
        }

        viewModel.beerLiveData.observe(this, Observer{
            adapter.submitList(it)

            if(hideProgress) {
                binding.loadingArea.visibility = View.GONE
                binding.listBeer.visibility = View.VISIBLE
            }
        })

        viewModel.netWorkResponse.observe(this, Observer {
            adapter.setNetworkResponse(it)
        })

        val divider = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(applicationContext, R.drawable.recycler_view_divider)?.let {
            divider.setDrawable(it)
        }

        binding.listBeer.addItemDecoration(divider)
        binding.listBeer.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Dialog para sair
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        // Dialog para sair
        super.onBackPressed()
    }
}
