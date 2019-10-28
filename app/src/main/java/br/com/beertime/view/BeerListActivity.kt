package br.com.beertime.view

import android.content.Intent
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
import br.com.beertime.model.NetworkResponse
import br.com.beertime.utils.createCompleteDialog
import br.com.beertime.utils.createPositiveDialog
import br.com.beertime.utils.isInternetAvailable
import br.com.beertime.viewmodel.BeerListViewModel


class BeerListActivity : AppCompatActivity() {

    lateinit var viewModel: BeerListViewModel
    lateinit var binding: ActivityBeerListBinding
    lateinit var adapter: BeerPageAdapter
    var hideProgress = true
    private val initProcessCode = 100
    private val runningProcessCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BeerListViewModel::class.java)
        if(isInternetAvailable(this)) {
            init()
        } else {
            createInternetDialog(initProcessCode)
        }
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_list)
        binding.viewModel = viewModel
        binding.listBeer.layoutManager = LinearLayoutManager(applicationContext)

        adapter = BeerPageAdapter {
            val intent = Intent(this, BeerDetailActivity::class.java)
            intent.putExtra("beer", it)
            startActivity(intent)
            /*createDialog(this, "Isto é um teste") {
                print("Deu certo!")
            }*/
        }

        viewModel.beerPagedListLiveData.observe(this, Observer{
            adapter.submitList(it)

            if(hideProgress) {
                binding.loadingArea.visibility = View.GONE
                binding.listBeer.visibility = View.VISIBLE
            }
        })

        viewModel.netWorkResponse.observe(this, Observer {
            adapter.setNetworkResponse(it)

            if(it.status === NetworkResponse.Status.ERROR) {
                if(isInternetAvailable(this)) {
                    /*createPositiveDialog(this, it.msg) {
                        viewModel.invalidate()
                    }*/
                    createErrorDialog(it.msg) {
                        viewModel.invalidate()
                    }
                } else {
                    createInternetDialog(runningProcessCode)
                }
            }
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
                createExitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        createExitDialog()
    }

    private fun createExitDialog() {
        createPositiveDialog(this, getString(R.string.exit_message)) {
            finish()
        }
    }

    private fun createErrorDialog(msg: String, clickPositive: () -> Unit) {
        createCompleteDialog(this,
            msg,
            getString(R.string.btn_reload),
            getString(R.string.btn_leave),
            clickPositive) {
                finish()
            }
    }

    private fun createInternetDialog(code: Int) {
        createCompleteDialog(this,
            getString(R.string.internet_off_message),
            getString(R.string.btn_solve),
            getString(R.string.btn_leave), {
                startActivityForResult(
                    Intent(android.provider.Settings.ACTION_SETTINGS), code)
            }, {
                finish()
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(isInternetAvailable(this)) {
            when (requestCode) {
                initProcessCode -> init()
                runningProcessCode -> viewModel.invalidate()
            }
        } else {
            createInternetDialog(requestCode)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
