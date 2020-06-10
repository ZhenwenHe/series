package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.series.common.Series;

public class DTWDistanceMetrics<T> implements DistanceMetrics<T> {
    public DTWDistanceMetrics() {
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof Series && b instanceof Series) {
            return DistanceUtils.dtw((Series) a, (Series) b);
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
