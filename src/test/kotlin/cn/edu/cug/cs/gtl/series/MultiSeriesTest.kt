package cn.edu.cug.cs.gtl.series

import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import cn.edu.cug.cs.gtl.series.common.MultiSeries
import org.junit.Assert
import org.junit.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MultiSeriesTest {
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
        val ms = MultiSeries.of(xs, ys)
        try {
            val bytes = ms.storeToByteArray()
            val ms2 = MultiSeries.of(bytes)
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
        val ms = MultiSeries.of(xs, ys)
        try {
            val f = FileOutputStream(Config.getTestOutputDirectory() + File.separator + "test.series")
            ms.write(f)
            f.close()
            val f2 = FileInputStream(Config.getTestOutputDirectory() + File.separator + "test.series")
            val ms2 = MultiSeries.of(f2)
            val s2 = ms2.getSeries(0)
            Assert.assertArrayEquals(s2.values, ys[0], 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}