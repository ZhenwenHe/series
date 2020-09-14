package cn.edu.cug.cs.gtl.series.ml.clustering.kmeans;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class ElkanKMeansClusterer extends cn.edu.cug.cs.gtl.ml.clustering.kmeans.ElkanKMeansClusterer<Series, Label>{

    public ElkanKMeansClusterer(DataSet<Series> dataSet, DistanceMetric<Series> distanceMetrics) {
        super(dataSet, distanceMetrics);
    }
}
