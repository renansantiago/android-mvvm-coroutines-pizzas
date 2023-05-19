package com.pizzas.ui.component.details

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pizzas.ITEM_KEY
import com.pizzas.R
import com.pizzas.data.dto.pizzas.Pizza
import com.pizzas.databinding.ActivityDetailsBinding
import com.pizzas.ui.base.BaseActivity
import com.pizzas.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: ActivityDetailsBinding

    override fun initViewBinding() {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnHome.setOnClickListener {
            onBackPressed()
        }
        val list = intent.getStringExtra(ITEM_KEY) ?: arrayListOf<Pizza>().toString()
        val pizzas = Gson().fromJson<ArrayList<Pizza>>(list, object : TypeToken<List<Pizza>>() {}.type)
        viewModel.initIntentData(pizzas)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Toast.makeText(this, getString(R.string.success), Snackbar.LENGTH_LONG).show()
    }

    override fun observeViewModel() {
        observe(viewModel.pizzasData, ::initializeView)
    }

    private fun initializeView(pizzas: ArrayList<Pizza>) {
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
            val pizza = pizzas[0]
            val pizza2 = pizzas[1]
            binding.tvName.text = pizza.name
            binding.tvPrice.text = getString(R.string.money, pizza.price)
            binding.tvName2.text = pizza2.name
            binding.tvPrice2.text = getString(R.string.money, pizza2.price)
        }
        binding.tvTotal.text = getString(R.string.total, viewModel.getTotal())
    }
}
