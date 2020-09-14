package cn.edu.cug.cs.gtl.series.ml.clustering.kmedoids;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class PAMClusterer extends cn.edu.cug.cs.gtl.ml.clustering.kmedoids.PAMClusterer<Series, Label>{

    public PAMClusterer(DataSet<Series> dataSet, DistanceMetric<Series> distanceMetrics) {
        super(dataSet, distanceMetrics);
    }
}
