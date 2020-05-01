package cn.edu.cug.cs.gtl.series

import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import cn.edu.cug.cs.gtl.series.common.Series
import org.junit.Assert
import org.junit.Test
import java.io.*

class SeriesTest {
    @Test
    fun copy() {
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = Series.of(ys)
        try {
            val f = FileOutputStream(testFileName)
            s1.write(f)
            f.close()
            val f2 = FileInputStream(testFileName)
            val s3 = Series.of(f2)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun load() {
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = Series.of(ys)
        try {
            val baos = ByteArrayOutputStream()
            s1.write(baos)
            val bytes = baos.toByteArray()
            baos.close()
            val bais = ByteArrayInputStream(bytes)
            val s3 = Series.of(bais)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun store() {
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = Series.of(ys)
        try {
            val bytes = s1.storeToByteArray()
            val s3 = Series.of(bytes)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        var testFileName = (Config.getTestOutputDirectory()
                + File.separator + "test.series")
    }
}