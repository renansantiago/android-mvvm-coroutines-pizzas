package com.pizzas.ui.base.listeners

import com.pizzas.data.dto.pizzas.Pizza

interface RecyclerItemListener {
    fun onItemSelected(pizza: Pizza, isChecked: Boolean)
}
