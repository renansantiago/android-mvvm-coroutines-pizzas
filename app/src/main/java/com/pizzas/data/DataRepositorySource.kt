package com.pizzas.data

import com.pizzas.data.dto.pizzas.Pizza
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun fetchAllPizzas(): Flow<ArrayList<Pizza>>
}
