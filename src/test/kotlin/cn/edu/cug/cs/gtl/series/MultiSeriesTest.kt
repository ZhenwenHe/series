package cn.edu.cug.cs.gtl.series

import cn.edu.cug.cs.gtl.common.Pair
import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import cn.edu.cug.cs.gtl.series.common.MultiSeries
import cn.edu.cug.cs.gtl.series.common.SeriesBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MultiSeriesTest {
    var testFileName:String =""
    @Before
    fun setUp() {
        Config.setConfigFile("series.properties")
        testFileName = (Config.getTestOutputDirectory()
                + File.separator + "test.series")
    }

    @Test
    fun readTSV() {
    }

    @Test
    fun of() {
        val xs = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = arrayOf(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
            doubleArrayOf(2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0),
            doubleArrayOf(3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0),
            doubleArrayOf(4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0),
            doubleArrayOf(5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0)
        )
        val ms = SeriesBuilder.build("test","value",xs,ys, Pair("label","1"))
        try {
            val bytes = ms.storeToByteArray()
            val ms2 = SeriesBuilder.parseMultiSeriesFrom(bytes)
            val s2 = ms2.getSeries(0)
            Assert.assertArrayEquals(s2.values, ys[0], 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun of1() {
        val xs = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = arrayOf(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
            doubleArrayOf(2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0),
            doubleArrayOf(3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0),
            doubleArrayOf(4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0),
            doubleArrayOf(5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0)
        )
        val ms = SeriesBuilder.build("test","value",xs,ys, Pair("label","1"))
        try {
            val f = FileOutputStream(testFileName)
            ms.write(f)
            f.close()
            val f2 = FileInputStream(testFileName)
            val ms2 = SeriesBuilder.parseMultiSeriesFrom(f2)
            val s2 = ms2.getSeries(0)
            Assert.assertArrayEquals(s2.values, ys[0], 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}