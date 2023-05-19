package com.pizzas.data.dto.pizzas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pizza(
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("price") @Expose var price: Double? = null,
)
