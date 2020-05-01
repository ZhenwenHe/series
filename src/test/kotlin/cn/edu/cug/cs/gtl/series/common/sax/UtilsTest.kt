package cn.edu.cug.cs.gtl.series.common.sax

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class UtilsTest {
    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    fun seriesToString() {
        val series = doubleArrayOf(-1.0, -2.0, -1.0, 0.0, 2.0, 1.0, 1.0, 0.0)
        try {
            val cc = Utils.seriesToString(
                series,
                3,
                normalA.getCuts(3),
                0.001
            )
            Assert.assertEquals(String(cc), "acc")
            Assert.assertTrue(
                String(
                    Utils.seriesToString(
                        series,
                        8,
                        normalA.getCuts(3),
                        0.001
                    )
                ) == "aaabcccb"
            )
        } catch (e: SAXException) {
            Assert.fail("exception shall not be thrown!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        var normalA = NormalAlphabet()
    }
}