package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;

public class EuclideanDistanceMetrics<T> implements DistanceMetrics<T> {
    public EuclideanDistanceMetrics() {
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof Series && b instanceof Series) {
            return DistanceUtils.euclidean((Series) a, (Series) b);
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
