package com.pizzas.ui.component.pizzas.adapter

import androidx.recyclerview.widget.RecyclerView
import com.pizzas.R
import com.pizzas.data.dto.pizzas.Pizza
import com.pizzas.databinding.ItemFlavorBinding
import com.pizzas.ui.base.listeners.RecyclerItemListener

class PizzaViewHolder(private val itemBinding: ItemFlavorBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(pizza: Pizza, recyclerItemListener: RecyclerItemListener, pizzasSelected: ArrayList<Pizza>) {
        itemBinding.tvName.text = pizza.name
        itemBinding.tvPrice.text = itemView.context?.getString(R.string.money, pizza.price.toString())

        itemBinding.clItem.setOnClickListener {
            if (pizzasSelected.size < 2 || pizzasSelected.any { it.name == pizza.name }) {
                itemBinding.cbFlavor.isChecked = !itemBinding.cbFlavor.isChecked
                recyclerItemListener.onItemSelected(pizza, itemBinding.cbFlavor.isChecked)
            }
        }
    }
}

