//package cn.edu.cug.cs.gtl.series.app;
//
//import cn.edu.cug.cs.gtl.common.Pair;
//import cn.edu.cug.cs.gtl.io.File;
//import cn.edu.cug.cs.gtl.ml.classification.NNClassifier;
//import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
//import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
//import cn.edu.cug.cs.gtl.series.io.MultiSeries;
//import cn.edu.cug.cs.gtl.series.io.Series;
//import cn.edu.cug.cs.gtl.series.io.SeriesBuilder;
//import cn.edu.cug.cs.gtl.series.ml.distances.SaxDistanceMetric;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SaxNNClassification {
//    public static void main(String[] args) {
//        ExperimentalConfig config = new ExperimentalConfig();
//        int m = config.getDataFiles().size();
//        int n = config.getPaaSizeRange().second() - config.getPaaSizeRange().first();
//        List<Pair<String, double[]>> results = new ArrayList<>();
//        int k = 0;
//        config.setResultFileName("sax_nn.xls");
//        try {
//            for (Pair<String, String> p : config.getDataFiles()) {
//                String name = File.getFileNameWithoutSuffix(p.first());
//                name = name.substring(0, name.indexOf('_'));
//                MultiSeries train = SeriesBuilder.readTSV(p.first());
//                MultiSeries test = SeriesBuilder.readTSV(p.second());
//                TrainSet<Series, String> trainSet = train.toTrainSet();
//                TestSet<Series, String> testSet = test.toTestSet();
//                Pair<Integer, Integer> paaSizeRange = config.getPaaSizeRange();
//                double[] r = new double[n];
//                k = 0;
//                for (int i = paaSizeRange.first(); i < paaSizeRange.second(); ++i) {
//                    SaxDistanceMetric<Series> disFunc = new SaxDistanceMetric<>(i, 16);
//                    NNClassifier<Series, String> nnClassifier = new NNClassifier<>(trainSet, testSet, disFunc);
//                    r[k] = nnClassifier.score();
//                    k++;
//                }
//                results.add(new Pair<>(name, r));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        config.writeResults(results);
//    }
//
//}
