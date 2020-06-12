package cn.edu.cug.cs.gtl.series

import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import cn.edu.cug.cs.gtl.ml.classification.NNClassifier
import cn.edu.cug.cs.gtl.series.common.MultiSeries
import cn.edu.cug.cs.gtl.series.common.Series
import cn.edu.cug.cs.gtl.series.common.SeriesBuilder
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane
import cn.edu.cug.cs.gtl.series.distances.HaxDistanceMetrics
import org.junit.Before
import org.junit.Test


class ClassificationTest {
    var testFileName:String =""
    @Before
    fun setUp() {
        Config.setConfigFile("series.properties")
        testFileName = (Config.getTestOutputDirectory()
                + File.separator + "test.series")
    }

    @Test
    fun predict() {
        val trainFilePath =
            Config.getTestInputDirectory() + File.separator + "UCRArchive_2018" + File.separator + "Beef" + File.separator + "Beef_TRAIN.tsv"
        val testFilePath =
            Config.getTestInputDirectory() + File.separator + "UCRArchive_2018" + File.separator + "Beef" + File.separator + "Beef_TEST.tsv"
        try {
            val train = SeriesBuilder.readTSV(trainFilePath)
            val test = SeriesBuilder.readTSV(testFilePath)
            val tioPlane =
                TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()))
            val disFunc = HaxDistanceMetrics<Series?>(10, tioPlane)
            val trainSet = train.toTrainSet();
            val testSet = test.toTestSet();
            val nn = NNClassifier(trainSet , testSet, disFunc);
            println(nn.score())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
