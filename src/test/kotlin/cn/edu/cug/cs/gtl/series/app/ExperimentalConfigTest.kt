package cn.edu.cug.cs.gtl.series.app

import cn.edu.cug.cs.gtl.common.Pair
import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*


class ExperimentalConfigTest {
    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    fun generalTest() {
        var config: ExperimentalConfig? = null
        //        config=new ExperimentalConfig(null);
//        Assert.assertEquals(96,config.getDataFiles().size());
//        Assert.assertEquals(5,config.getPaaSizeRange().first().intValue());
//        Assert.assertEquals(20,config.getPaaSizeRange().second().intValue());
//        Assert.assertEquals(3,config.getAlphabetRange().first().intValue());
//        Assert.assertEquals(16,config.getAlphabetRange().second().intValue());
//
//        config=new ExperimentalConfig("series.db");
//        Assert.assertEquals(96,config.getDataFiles().size());
//        Assert.assertEquals(5,config.getPaaSizeRange().first().intValue());
//        Assert.assertEquals(20,config.getPaaSizeRange().second().intValue());
//        Assert.assertEquals(3,config.getAlphabetRange().first().intValue());
//        Assert.assertEquals(16,config.getAlphabetRange().second().intValue());
        config =
            ExperimentalConfig(Config.getDataDirectory() + File.separatorChar + "log" + File.separatorChar + "series.db")
        Assert.assertEquals(96, config.dataFiles.size.toLong())
        Assert.assertEquals(5, config.paaSizeRange.first().toInt().toLong())
        Assert.assertEquals(21, config.paaSizeRange.second().toInt().toLong())
        Assert.assertEquals(3, config.alphabetRange.first().toInt().toLong())
        Assert.assertEquals(17, config.alphabetRange.second().toInt().toLong())
    }

    @Test
    fun writeResults() {
        val config = ExperimentalConfig("series.db")
        val results: MutableList<Pair<String, DoubleArray>> =
            ArrayList()
        for (i in 0..9) {
            val d = DoubleArray(5)
            for (j in 0..4) {
                d[j] = ((j + 10) * 3).toDouble()
            }
            val p =
                Pair(i.toString(), d)
            results.add(p)
        }
        config.writeResults(results)
    }
}