package cn.edu.cug.cs.gtl.series.ml.clustering.kmedoids;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class CLARAClusterer extends cn.edu.cug.cs.gtl.ml.clustering.kmedoids.CLARAClusterer<Series, Label>{
    public CLARAClusterer(DataSet<Series> dataSet, DistanceMetric<Series> distanceMetrics) {
        super(dataSet, distanceMetrics);
    }
}
