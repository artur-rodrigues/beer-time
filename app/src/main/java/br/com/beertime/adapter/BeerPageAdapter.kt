package br.com.beertime.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.beertime.R
import br.com.beertime.databinding.ItemBinding
import br.com.beertime.databinding.ProgressBinding
import br.com.beertime.model.Beer
import br.com.beertime.model.NetworkResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Artur on 27/10/2019.
 */
class BeerPageAdapter(val click: (beer: Beer) -> Unit) : PagedListAdapter<Beer, RecyclerView.ViewHolder>(Beer.DIFF_CALLBACK) {

    private val progressType = 0
    private val itemType = 1
    private var networkResponse: NetworkResponse = NetworkResponse.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            progressType -> {
                createProgressViewHolder(inflater, parent)
            }
            itemType -> {
                val itemBinding = ItemBinding.inflate(inflater, parent, false)
                BeeritemViewHoler(itemBinding)
            }
            else -> {
                createProgressViewHolder(inflater, parent)
            }
        }
    }

    private fun createProgressViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProgressItemViewHolder {
        val progressBinding = ProgressBinding.inflate(inflater, parent, false)
        return ProgressItemViewHolder(progressBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BeeritemViewHoler) {
            getItem(position)?.let { holder.bindView(it) }
        } else if (holder is ProgressItemViewHolder) {
            holder.bindView(networkResponse)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            progressType
        } else {
            itemType
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkResponse !== NetworkResponse.SUCCESS
    }

    fun setNetworkResponse(newNetworkResponse: NetworkResponse) {
        val previousState = this.networkResponse
        val previousExtraRow = hasExtraRow()
        this.networkResponse = newNetworkResponse
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkResponse) {
            notifyItemChanged(itemCount - 1)
        }
    }

    inner class ProgressItemViewHolder(private val binding: ProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(networkState: NetworkResponse?) {
            if (networkState != null && networkState.status === NetworkResponse.Status.LOADING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            if (networkState != null && networkState.status === NetworkResponse.Status.ERROR) {
                binding.labelInfo.visibility = View.VISIBLE
                binding.labelInfo.text = networkState.msg
            } else {
                binding.labelInfo.visibility = View.GONE
            }
        }
    }

    inner class BeeritemViewHoler(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(beer: Beer) {
            binding.labelBeerName.text = beer.name
            if(beer.imageUrl != null) {
                Picasso.get()
                    .load(beer.imageUrl)
                    .resize(60, 60)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.imgListBeer)
            } else {
                binding.imgListBeer.setImageResource(R.mipmap.ic_launcher_round)
            }

            binding.cardView.setOnClickListener {
                click(beer)
            }
        }
    }
}