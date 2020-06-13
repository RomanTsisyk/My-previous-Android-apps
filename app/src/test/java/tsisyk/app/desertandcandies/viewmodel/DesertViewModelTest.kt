package tsisyk.app.desertandcandies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import tsisyk.app.desertandcandies.model.Desert
import tsisyk.app.desertandcandies.model.DesertAttributes
import tsisyk.app.desertandcandies.model.DesertGereratior

class DesertViewModelTest {

    lateinit var desertViewModel: DesertViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGenerator: DesertGereratior

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        desertViewModel = DesertViewModel(mockGenerator)
    }

    @Test
    fun testSetupDesert(){
        val attributes = DesertAttributes(3,3,3)
        val stubDesert = Desert(attributes, 90, "test")
        `when` (mockGenerator.generateDesert(attributes)).thenReturn(stubDesert)

        desertViewModel.taste = 3
        desertViewModel.price = 3
        desertViewModel.calories = 3

        desertViewModel.updateDesert()


        assertEquals(stubDesert, desertViewModel.desert)
    }

}