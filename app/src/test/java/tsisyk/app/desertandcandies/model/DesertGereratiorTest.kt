package tsisyk.app.desertandcandies.model

import org.junit.Before
import org.junit.Test
import junit.framework.Assert.assertEquals

class DesertGereratiorTest {

    private lateinit var desertGereratior: DesertGereratior

    @Before
    fun setup(){
        desertGereratior = DesertGereratior()
    }

    @Test
    fun testGenerationPointRankings(){
        val attributes = DesertAttributes (price = 3, calories = 3, taste = 3)
        val name = "name test"
        val expectedDesert = Desert( attributes, 90, name)
        assertEquals(expectedDesert, desertGereratior.generateDesert(attributes, name))
    }
}