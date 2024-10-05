package com.example.beerapp.model

data class BeerResponse(
    val price: String?,
    val name: String,
    val rating: Rating?,
    val image: String?,
    val id: Int?
)
data class Rating(
    val average: Double?,
    val reviews: Int?
)