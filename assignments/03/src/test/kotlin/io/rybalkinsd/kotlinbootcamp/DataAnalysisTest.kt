package io.rybalkinsd.kotlinbootcamp

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.math.floor

class DataAnalysisTest {

    @Test
    fun `check avg age`() {
        assertTrue(avgAge.isNotEmpty())
        assertEquals(avgAge.size, 3)
    }

    @Test
    fun `check avg age value`() {
        assertEquals(floor(avgAge.getValue(DataSource.FACEBOOK)).toInt(), 52)
        assertEquals(floor(avgAge.getValue(DataSource.VK)).toInt(), 33)
        assertEquals(floor(avgAge.getValue(DataSource.LINKEDIN)).toInt(), 50)
    }

    @Test
    fun `check grouped profiles`() {
        assertTrue(groupedProfiles.isNotEmpty())
        assertEquals(groupedProfiles.size, allProfiles.size)
    }

    @Test
    fun `check grouped profiles values`() {
        assertTrue(groupedProfiles.getValue(0L).containsAll(groupedProfiles.getValue(8)))
        assertTrue(groupedProfiles.getValue(0L).containsAll(groupedProfiles.getValue(9)))
        for (i: Long in 1L..7L) {
            assertEquals("Invalid list size to user with id = %d".format(i), groupedProfiles.getValue(i).size, 1)
        }
    }
}
