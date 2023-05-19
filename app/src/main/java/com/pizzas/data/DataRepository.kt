package com.pizzas.data

import com.pizzas.data.dto.pizzas.Pizza
import com.pizzas.data.local.LocalData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {

    override suspend fun fetchAllPizzas(): Flow<ArrayList<Pizza>> {
        return flow {
            emit(localRepository.fetchAllLocalPizzas())
        }.flowOn(ioDispatcher)
    }
}
