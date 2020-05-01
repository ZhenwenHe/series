package cn.edu.cug.cs.gtl.series

import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import cn.edu.cug.cs.gtl.ml.classification.NNClassifier
import cn.edu.cug.cs.gtl.series.common.MultiSeries
import cn.edu.cug.cs.gtl.series.common.TimeSeries
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane
import cn.edu.cug.cs.gtl.series.distances.HaxDistanceMetrics
import org.junit.Test


class ClassificationTest {
    @Test
    fun predict() {
        val trainFilePath =
            Config.getTestInputDirectory() + File.separator + "UCRArchive_2018" + File.separator + "Beef" + File.separator + "Beef_TRAIN.tsv"
        val testFilePath =
            Config.getTestInputDirectory() + File.separator + "UCRArchive_2018" + File.separator + "Beef" + File.separator + "Beef_TEST.tsv"
        try {
            val train = MultiSeries.readTSV(trainFilePath)
            val test = MultiSeries.readTSV(testFilePath)
            val tioPlane =
                TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()))
            val disFunc = HaxDistanceMetrics<TimeSeries?>(10, tioPlane)
            val trainSet = train.toTrainSet();
            val testSet = test.toTestSet();
            val nn = NNClassifier(trainSet , testSet, disFunc);
            println(nn.score())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
