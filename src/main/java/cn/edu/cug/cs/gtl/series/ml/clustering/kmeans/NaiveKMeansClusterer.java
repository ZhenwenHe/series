package cn.edu.cug.cs.gtl.series.ml.clustering.kmeans;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class NaiveKMeansClusterer extends cn.edu.cug.cs.gtl.ml.clustering.kmeans.NaiveKMeansClusterer<Series, Label>{

    public NaiveKMeansClusterer(DataSet<Series> dataSet, DistanceMetric<Series> distanceMetrics) {
        super(dataSet, distanceMetrics);
    }
}
