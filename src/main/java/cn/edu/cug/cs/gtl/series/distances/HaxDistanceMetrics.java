package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;


public class HaxDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int wordSize;
    protected TIOPlane tioPlane;

    public HaxDistanceMetrics(int wordSize, TIOPlane tioPlane) {
        this.wordSize = wordSize;
        this.tioPlane = tioPlane;
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof Series && b instanceof Series) {
            return DistanceUtils.hax((Series) a, (Series) b, this.wordSize, tioPlane);
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
