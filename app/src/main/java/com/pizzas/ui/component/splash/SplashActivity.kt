package com.pizzas.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.pizzas.SPLASH_DELAY
import com.pizzas.databinding.ActivitySplashBinding
import com.pizzas.ui.base.BaseActivity
import com.pizzas.ui.component.pizzas.FlavorsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun initViewBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {
    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, FlavorsActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}
