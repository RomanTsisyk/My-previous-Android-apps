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
import tsisyk.app.desertandcandies.model.DesertRepository

class DesertViewModelTest {

    lateinit var desertViewModel: DesertViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGenerator: DesertGereratior
    @Mock
    lateinit var mockRepository: DesertRepository

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        desertViewModel = DesertViewModel(mockGenerator, mockRepository )
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

    @Test
    fun testCansaveDesertWithoutBlankName(){
        desertViewModel.taste = 3
        desertViewModel.price = 3
        desertViewModel.calories = 3
        desertViewModel.drawable = 1
        desertViewModel.name = ""

        val canSaveDesert = desertViewModel.canSaveDesert()
        assertEquals(false, canSaveDesert)
    }

    @Test
    fun testCansaveDesertWithoutAvatar(){
        desertViewModel.taste = 3
        desertViewModel.price = 3
        desertViewModel.calories = 3
        desertViewModel.drawable = 0
        desertViewModel.name = "test"

        val canSaveDesert = desertViewModel.canSaveDesert()
        assertEquals(false, canSaveDesert)
    }

    @Test
    fun testCansaveDesertWithoutPrice(){
        desertViewModel.taste = 3
        desertViewModel.price = 0
        desertViewModel.calories = 3
        desertViewModel.drawable = 1
        desertViewModel.name = "test"

        val canSaveDesert = desertViewModel.canSaveDesert()
        assertEquals(false, canSaveDesert)
    }

    @Test
    fun testCansaveDesertWithoutTaste(){
        desertViewModel.taste = 0
        desertViewModel.price = 30
        desertViewModel.calories = 3
        desertViewModel.drawable = 1
        desertViewModel.name = "test"

        val canSaveDesert = desertViewModel.canSaveDesert()
        assertEquals(false, canSaveDesert)
    }

    @Test
    fun testCansaveDesertWithoutCalories(){
        desertViewModel.taste = 20
        desertViewModel.price = 30
        desertViewModel.calories = 0
        desertViewModel.drawable = 1
        desertViewModel.name = "test"

        val canSaveDesert = desertViewModel.canSaveDesert()
        assertEquals(false, canSaveDesert)
    }

    @Test
    fun testCansaveDesertWithoutAvatarAndBlankName(){
        desertViewModel.taste = 3
        desertViewModel.price = 3
        desertViewModel.calories = 3
        desertViewModel.drawable = 0
        desertViewModel.name = ""
        val canSaveDesert = desertViewModel.canSaveDesert()
        assertEquals(false, canSaveDesert)
    }

}