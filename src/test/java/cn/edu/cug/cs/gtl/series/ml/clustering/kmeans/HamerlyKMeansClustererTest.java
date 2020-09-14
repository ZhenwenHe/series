package cn.edu.cug.cs.gtl.series.ml.clustering.kmeans;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.ml.dataset.Sample;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;
import cn.edu.cug.cs.gtl.series.ml.MultiSeries;
import cn.edu.cug.cs.gtl.series.ml.Series;
import cn.edu.cug.cs.gtl.series.ml.clustering.kmeans.HamerlyKMeansClusterer;
import cn.edu.cug.cs.gtl.series.ml.distances.HaxDistanceMetric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HamerlyKMeansClustererTest {
    String testFileName;
    String trainFilePath;
    String testFilePath;
    @Before
    public void setUp() throws Exception {
        Config.setConfigFile("series.properties");
        testFileName = (Config.getTestOutputDirectory()
                + File.separator + "test.series");

        trainFilePath =
                Config.getTestInputDirectory() + File.separator + "UCRArchive_2018" + File.separator + "Beef" + File.separator + "Beef_TRAIN.tsv";
        testFilePath =
                Config.getTestInputDirectory() + File.separator + "UCRArchive_2018" + File.separator + "Beef" + File.separator + "Beef_TEST.tsv";

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void predict() {
        try {
            MultiSeries train = MultiSeries.fromTSVFile(trainFilePath);
            MultiSeries test = MultiSeries.fromTSVFile(trainFilePath);
            TIOPlane tioPlane =
                    TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()));
            HaxDistanceMetric<Series> disFunc = new HaxDistanceMetric<> (10, tioPlane);
            TrainSet<Series> trainSet = train.toTrainSet();
            TestSet<Series> testSet = test.toTestSet();
            HamerlyKMeansClusterer nn = new HamerlyKMeansClusterer(trainSet,disFunc);
            nn.setTestSet(testSet);
            nn.fit(trainSet);
            List<List<Sample<Series>>> clusters = nn.getClusters();
            int c=0;
            for(List<Sample<Series>> ls: clusters){
                System.out.println("cluster:"+String.valueOf(c));
                for(Sample<Series> s: ls){
                    System.out.println(s.getCategoricalLabel(0));
                }
                c++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}