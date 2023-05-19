package com.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pizzas.data.dto.pizzas.Pizza
import java.io.File

class TestModelsGenerator {
    private var pizzas = arrayListOf<Pizza>()

    init {
        val jsonString = getJson("pizzasTest.json")
        pizzas = ArrayList(Gson().fromJson<List<Pizza>>(jsonString, object : TypeToken<List<Pizza>>() {}.type))
        print("this is $pizzas")
    }

    fun generateList(): ArrayList<Pizza> {
        return pizzas
    }

    fun generateWithEmptyList(): ArrayList<Pizza> {
        return arrayListOf()
    }

    fun generateItemModel(): Pizza {
        return pizzas[0]
    }

    fun getStubSearchTitle(): String {
        return pizzas[0].name ?: ""
    }

    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
