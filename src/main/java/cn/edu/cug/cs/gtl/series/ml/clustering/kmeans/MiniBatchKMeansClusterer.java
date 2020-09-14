package cn.edu.cug.cs.gtl.series.ml.clustering.kmeans;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class MiniBatchKMeansClusterer extends cn.edu.cug.cs.gtl.ml.clustering.kmeans.MiniBatchKMeansClusterer<Series, Label> {
    /**
     * @param dataSet
     * @param distanceMetrics the distance metric to use
     * @param batchSize       the mini-batch size
     * @param iterations      the number of mini batches to perform
     */
    public MiniBatchKMeansClusterer(DataSet<Series> dataSet, DistanceMetric<Series> distanceMetrics, int batchSize, int iterations) {
        super(dataSet, distanceMetrics, batchSize, iterations);
    }
}
