package com.pizzas.ui.component.pizzas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.pizzas.ITEM_KEY
import com.pizzas.R
import com.pizzas.data.dto.pizzas.Pizza
import com.pizzas.databinding.ActivityFlavorsBinding
import com.pizzas.ui.base.BaseActivity
import com.pizzas.ui.component.details.DetailsActivity
import com.pizzas.ui.component.pizzas.adapter.PizzaAdapter
import com.pizzas.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlavorsActivity : BaseActivity() {
    private lateinit var binding: ActivityFlavorsBinding

    private val viewModel: FlavorsViewModel by viewModels()
    private var pizzaAdapter: PizzaAdapter? = null

    override fun initViewBinding() {
        binding = ActivityFlavorsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.toolbar)
        val layoutManager = LinearLayoutManager(this)
        binding.rvFlavors.layoutManager = layoutManager
        binding.rvFlavors.setHasFixedSize(true)
        binding.btnFinish.setOnClickListener {
            viewModel.flavorsLiveData.value?.let {
                if (it.size == 0) {
                    Toast.makeText(this, getString(R.string.noEmpty), Snackbar.LENGTH_LONG).show()
                } else {
                    startActivity(Intent(this, DetailsActivity::class.java).apply {
                        putExtra(ITEM_KEY, Gson().toJson(it))
                    })
                }
            } ?: run {
                Toast.makeText(this, getString(R.string.noEmpty), Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.getPizzas()
    }

    private fun bindListData(pizzas: ArrayList<Pizza>) {
        pizzaAdapter = PizzaAdapter(viewModel, pizzas, arrayListOf())
        binding.rvFlavors.adapter = pizzaAdapter
    }

    private fun handlePizzasList(pizzas: ArrayList<Pizza>) {
        bindListData(pizzas)
    }

    private fun handleFlavorSelected(pizzas: ArrayList<Pizza>) {
        if (pizzas.isEmpty()) {
            binding.tvName.text = "-"
            binding.tvPrice.text = "-"
            binding.tvName2.text = "-"
            binding.tvPrice2.text = "-"
            binding.tvTotal.text = getString(R.string.total, 0.0)
        } else if (pizzas.size == 1) {
            binding.tvName2.text = "-"
            binding.tvPrice2.text = "-"
            val pizza = pizzas[0]
            binding.tvName.text = pizza.name
            binding.tvPrice.text = getString(R.string.money, pizza.price)
        } else if (pizzas.size == 2) {
            val pizza = pizzas[1]
            binding.tvName2.text = pizza.name
            binding.tvPrice2.text = getString(R.string.money, pizza.price)
        }
        binding.tvTotal.text = getString(R.string.total, viewModel.getTotal())
        pizzaAdapter?.refreshQtdFlavorsSelected(pizzas)
    }

    override fun observeViewModel() {
        observe(viewModel.pizzasLiveData, ::handlePizzasList)
        observe(viewModel.flavorsLiveData, ::handleFlavorSelected)
    }
}
