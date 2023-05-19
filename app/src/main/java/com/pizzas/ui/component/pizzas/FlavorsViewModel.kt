package com.pizzas.ui.component.pizzas

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pizzas.data.DataRepositorySource
import com.pizzas.data.dto.pizzas.Pizza
import com.pizzas.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlavorsViewModel @Inject constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val pizzasLiveDataPrivate = MutableLiveData<ArrayList<Pizza>>()
    val pizzasLiveData: LiveData<ArrayList<Pizza>> get() = pizzasLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val flavorsLiveDataPrivate = MutableLiveData<ArrayList<Pizza>>()
    val flavorsLiveData: LiveData<ArrayList<Pizza>> get() = flavorsLiveDataPrivate

    fun selectFlavor(pizza: Pizza, isChecked: Boolean) {
        flavorsLiveDataPrivate.value?.let {
            if (isChecked) {
                flavorsLiveDataPrivate.value?.plus(pizza)?.let {
                    flavorsLiveDataPrivate.value = ArrayList(it)
                }
            } else {
                flavorsLiveDataPrivate.value?.minus(pizza)?.let {
                    flavorsLiveDataPrivate.value = ArrayList(it)
                }
            }
        } ?: run {
            flavorsLiveDataPrivate.value = arrayListOf(pizza)
        }
    }

    fun getTotal(): Double {
        var total = 0.0
        flavorsLiveDataPrivate.value?.forEach { it.price?.let { price -> total += price } }
        return total
    }

    fun getPizzas() {
        viewModelScope.launch {
            dataRepository.fetchAllPizzas().collect {
                pizzasLiveDataPrivate.value = it
            }
        }
    }
}
