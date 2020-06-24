///*
//package tsisyk.app.kittyrank.viewmodel
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.rules.TestRule
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.MockitoAnnotations
//import tsisyk.app.kittyrank.model.Desert
//import tsisyk.app.kittyrank.model.DesertAttributes
//import tsisyk.app.kittyrank.model.DesertGereratior
//import tsisyk.app.kittyrank.model.DesertRepository
//
//class DesertViewModelTest {
//
//    lateinit var desertViewModel: DesertViewModel
//
//    @get:Rule
//    var rule: TestRule = InstantTaskExecutorRule()
//
//    @Mock
//    lateinit var mockGenerator: DesertGereratior
//    @Mock
//    lateinit var mockRepository: DesertRepository
//
//    @Before
//    fun setup(){
//        MockitoAnnotations.initMocks(this)
//        desertViewModel = DesertViewModel(mockGenerator, mockRepository )
//    }
//
//    @Test
//    fun testSetupDesert(){
//        val attributes = DesertAttributes(3,3,3)
//        val stubDesert = Desert(attributes, 90, "test")
//        `when` (mockGenerator.generateDesert(attributes)).thenReturn(stubDesert)
//
//        desertViewModel.omoji = 3
//        desertViewModel.opinion = 3
//        desertViewModel.kitty_opinion = 3
//        desertViewModel.age = 3
//        desertViewModel.updateDesert()
//
//        assertEquals(stubDesert, desertViewModel.desert)
//    }
//
//    @Test
//    fun testCansaveDesertWithoutBlankName(){
//        desertViewModel.omoji = 3
//        desertViewModel.opinion = 3
//        desertViewModel.age = 3
//        desertViewModel.kitty_opinion = 3
//        desertViewModel.drawable = 1
//        desertViewModel.name.set("")
//
//        val canSaveDesert = desertViewModel.canSaveDesert()
//        assertEquals(false, canSaveDesert)
//    }
//
//    @Test
//    fun testCansaveDesertWithoutAvatar(){
//        desertViewModel.omoji = 3
//        desertViewModel.opinion = 3
//        desertViewModel.kitty_opinion = 3
//        desertViewModel.drawable = 0
//        desertViewModel.name.set("test")
//
//        val canSaveDesert = desertViewModel.canSaveDesert()
//        assertEquals(false, canSaveDesert)
//    }
//
//    @Test
//    fun testCansaveDesertWithoutPrice(){
//        desertViewModel.omoji = 3
//        desertViewModel.opinion = 0
//        desertViewModel.kitty_opinion = 3
//        desertViewModel.drawable = 1
//        desertViewModel.name.set("test")
//
//        val canSaveDesert = desertViewModel.canSaveDesert()
//        assertEquals(false, canSaveDesert)
//    }
//
//    @Test
//    fun testCansaveDesertWithoutTaste(){
//        desertViewModel.omoji = 0
//        desertViewModel.opinion = 30
//        desertViewModel.kitty_opinion = 3
//        desertViewModel.drawable = 1
//        desertViewModel.name.set("test")
//
//        val canSaveDesert = desertViewModel.canSaveDesert()
//        assertEquals(false, canSaveDesert)
//    }
//
//    @Test
//    fun testCansaveDesertWithoutCalories(){
//        desertViewModel.omoji = 20
//        desertViewModel.opinion = 30
//        desertViewModel.kitty_opinion = 0
//        desertViewModel.drawable = 1
//        desertViewModel.name.set("test")
//
//        val canSaveDesert = desertViewModel.canSaveDesert()
//        assertEquals(false, canSaveDesert)
//    }
//
//    @Test
//    fun testCansaveDesertWithoutAvatarAndBlankName(){
//        desertViewModel.omoji = 3
//        desertViewModel.opinion = 3
//        desertViewModel.kitty_opinion = 3
//        desertViewModel.drawable = 0
//        desertViewModel.name.set("")
//        val canSaveDesert = desertViewModel.canSaveDesert()
//        assertEquals(false, canSaveDesert)
//    }
//
//}*/
