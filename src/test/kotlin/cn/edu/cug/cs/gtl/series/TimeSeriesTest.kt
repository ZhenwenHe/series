package cn.edu.cug.cs.gtl.series

import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import cn.edu.cug.cs.gtl.series.common.TimeSeries
import org.junit.Assert
import org.junit.Test
import java.io.*


class TimeSeriesTest {
    @Test
    fun copy() {
        val xs = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = TimeSeries.of(xs, ys)
        val s2 = TimeSeries.of(ys)
        try {
            val f = FileOutputStream(testFileName)
            s1.write(f)
            f.close()
            val f2 = FileInputStream(testFileName)
            val s3 = TimeSeries.of(f2)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
            Assert.assertArrayEquals(ys, s2.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun load() {
        val xs = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = TimeSeries.of(xs, ys)
        val s2 = TimeSeries.of(ys)
        try {
            val baos = ByteArrayOutputStream()
            s1.write(baos)
            val bytes = baos.toByteArray()
            baos.close()
            val bais = ByteArrayInputStream(bytes)
            val s3 = TimeSeries.of(bais)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
            Assert.assertArrayEquals(ys, s2.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun store() {
        val xs = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = TimeSeries.of(xs, ys)
        val s2 = TimeSeries.of(ys)
        try {
            val bytes = s1.storeToByteArray()
            val s3 = TimeSeries.of(bytes)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
            Assert.assertArrayEquals(ys, s2.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        var testFileName = (Config.getTestOutputDirectory()
                + File.separator + "test.timeseries")
    }
}
