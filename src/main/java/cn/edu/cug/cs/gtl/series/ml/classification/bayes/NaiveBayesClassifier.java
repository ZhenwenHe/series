package cn.edu.cug.cs.gtl.series.ml.classification.bayes;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class NaiveBayesClassifier extends cn.edu.cug.cs.gtl.ml.classification.bayes.NaiveBayesClassifier<Series, Label> {
    public NaiveBayesClassifier(DataSet<Series> trainSet, DataSet<Series> testSet, DistanceMetric<Series> distanceMetrics) {
        super(trainSet, testSet, distanceMetrics);
    }
}
