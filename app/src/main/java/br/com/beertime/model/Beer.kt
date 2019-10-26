package br.com.beertime.model

import com.fasterxml.jackson.annotation.JsonAlias

/**
 * Created by Artur on 26/10/2019.
 */
class Beer {
    lateinit var name: String
    @JsonAlias("image_url")
    lateinit var imageUrl: String
    lateinit var description: String
}