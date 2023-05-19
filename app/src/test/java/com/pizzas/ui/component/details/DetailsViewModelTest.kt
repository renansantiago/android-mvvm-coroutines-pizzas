package com.pizzas.ui.component.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pizzas.data.DataRepository
import com.util.InstantExecutorExtension
import com.util.MainCoroutineRule
import com.util.TestModelsGenerator
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class DetailsViewModelTest {
    // Subject under test
    private lateinit var detailsViewModel: DetailsViewModel

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

    @Test
    fun testInitIntentData() {
        //1- Mock Data
        val list = testModelsGenerator.generateList()
        //2-Call
        detailsViewModel = DetailsViewModel(dataRepository)
        detailsViewModel.initIntentData(list)
        //active observer for livedata
        detailsViewModel.pizzasData.observeForever { }

        //3-verify
        val data = detailsViewModel.pizzasData.value
        assertEquals(list, data)
    }
}
