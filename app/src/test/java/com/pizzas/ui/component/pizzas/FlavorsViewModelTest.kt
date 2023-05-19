package com.pizzas.ui.component.pizzas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pizzas.data.DataRepository
import com.pizzas.data.dto.pizzas.Pizza
import com.util.InstantExecutorExtension
import com.util.MainCoroutineRule
import com.util.TestModelsGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class FlavorsViewModelTest {
    // Subject under test
    private lateinit var flavorsViewModel: FlavorsViewModel

    // Use a fake UseCase to be injected into the viewModel
    private val dataRepository: DataRepository = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()

    @Before
    fun setUp() {
        // Create class under test
        // Initializing the repository with no tasks
    }

    @Test
    fun testFetchPizzasList() {
        // Let's do an answer for the liveData
        val list = testModelsGenerator.generateList()

        //1- Mock calls
        coEvery { dataRepository.fetchAllPizzas() } returns flow {
            emit(list)
        }

        //2-Call
        flavorsViewModel = FlavorsViewModel(dataRepository)
        flavorsViewModel.getPizzas()
        //active observer for livedata
        flavorsViewModel.pizzasLiveData.observeForever { }

        //3-verify
        val isEmptyList = flavorsViewModel.pizzasLiveData.value.isNullOrEmpty()
        assertEquals(list, flavorsViewModel.pizzasLiveData.value)
        assertEquals(false, isEmptyList)
    }

    @Test
    fun testSelectFlavor() {
        // Let's do an answer for the liveData
        val list = testModelsGenerator.generateList()

        //1- Mock calls
        flavorsViewModel = FlavorsViewModel(dataRepository)
        flavorsViewModel.pizzasLiveDataPrivate.value = list

        //2-Call
        flavorsViewModel.selectFlavor(Pizza("Mozzarella", 10.0), true)
        //active observer for livedata
        flavorsViewModel.pizzasLiveData.observeForever { }

        //3-verify
        val flavors = flavorsViewModel.flavorsLiveData.value ?: arrayListOf()
        assertEquals(1, flavors.size)
    }

    @Test
    fun testGetTotal() {
        // Let's do an answer for the liveData
        val list = testModelsGenerator.generateList()

        //1- Mock calls
        flavorsViewModel = FlavorsViewModel(dataRepository)
        flavorsViewModel.pizzasLiveDataPrivate.value = list

        //2-Call
        flavorsViewModel.selectFlavor(Pizza("Mozzarella", 10.0), true)
        flavorsViewModel.selectFlavor(Pizza("Pepperoni", 12.0), true)
        //active observer for livedata
        flavorsViewModel.pizzasLiveData.observeForever { }

        //3-verify
        val total = flavorsViewModel.getTotal()
        assertEquals(22.0, total, 0.0)
    }
}
