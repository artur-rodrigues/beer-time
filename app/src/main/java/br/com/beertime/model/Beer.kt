package br.com.beertime.model

import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import com.fasterxml.jackson.annotation.JsonAlias
import java.io.Serializable

/**
 * Created by Artur on 26/10/2019.
 */
class Beer : Serializable {
    var id: Long = 0
    lateinit var name: String
    @JsonAlias("image_url")
    var imageUrl: String? = null
    lateinit var description: String

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Beer> = object : DiffUtil.ItemCallback<Beer>() {

            override fun areItemsTheSame(@NonNull oldItem: Beer, @NonNull newItem: Beer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(@NonNull oldItem: Beer, @NonNull newItem: Beer): Boolean {
                return newItem == oldItem
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        val beer: Beer = other as Beer

        if(beer.id != id) {
            return false
        }

        if(beer.name != name) {
            return false
        }

        if(beer.description != description) {
            return false
        }

        if(beer.imageUrl != imageUrl) {
            return false
        }

        return true
    }
}