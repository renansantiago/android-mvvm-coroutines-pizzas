package com.pizzas.ui.component.pizzas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pizzas.data.dto.pizzas.Pizza
import com.pizzas.databinding.ItemFlavorBinding
import com.pizzas.ui.base.listeners.RecyclerItemListener
import com.pizzas.ui.component.pizzas.FlavorsViewModel

class PizzaAdapter(private val flavorsViewModel: FlavorsViewModel, private val pizzas: ArrayList<Pizza>, var pizzasSelected: ArrayList<Pizza>) : RecyclerView.Adapter<PizzaViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(pizza: Pizza, isChecked: Boolean) {
            flavorsViewModel.selectFlavor(pizza, isChecked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val itemBinding = ItemFlavorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PizzaViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        holder.bind(pizzas[position], onItemClickListener, pizzasSelected)
    }

    override fun getItemCount(): Int {
        return pizzas.size
    }

    fun refreshQtdFlavorsSelected(pizzas: ArrayList<Pizza>) {
        pizzasSelected = pizzas
        //TODO - HANDLE INDIVIDUAL ITEMS
        notifyDataSetChanged()
    }
}

