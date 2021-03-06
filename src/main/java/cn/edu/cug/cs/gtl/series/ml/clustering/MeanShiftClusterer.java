package cn.edu.cug.cs.gtl.series.ml.clustering;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class MeanShiftClusterer extends cn.edu.cug.cs.gtl.ml.clustering.MeanShiftClusterer<Series, Label>{
    public MeanShiftClusterer(DataSet<Series> dataSet, DistanceMetric<Series> distanceMetrics) {
        super(dataSet, distanceMetrics);
    }
}
