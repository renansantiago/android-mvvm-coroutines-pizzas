package com.pizzas.ui.component.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pizzas.data.DataRepositorySource
import com.pizzas.data.dto.pizzas.Pizza
import com.pizzas.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class DetailsViewModel @Inject constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pizzasPrivate = MutableLiveData<ArrayList<Pizza>>()
    val pizzasData: LiveData<ArrayList<Pizza>> get() = pizzasPrivate

    fun initIntentData(pizzas: ArrayList<Pizza>) {
        pizzasPrivate.value = pizzas
    }

    fun getTotal(): Double {
        var total = 0.0
        pizzasPrivate.value?.forEach { it.price?.let { price -> total += price } }
        return total
    }
}
