package com.pizzas.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pizzas.data.dto.pizzas.Pizza
import java.io.IOException
import javax.inject.Inject

class LocalData @Inject constructor(val context: Context) {

    fun fetchAllLocalPizzas(): ArrayList<Pizza> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("files/pizzas.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        val listType = object : TypeToken<List<Pizza>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}

