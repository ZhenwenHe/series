package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;

public class PaxDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int wordSize;
    protected TIOPlane tioPlane = null;

    public PaxDistanceMetrics(int wordSize, TIOPlane tioPlane) {
        this.wordSize = wordSize;
        this.tioPlane = tioPlane;
    }

    public PaxDistanceMetrics(int wordSize) {
        this.wordSize = wordSize;
        this.tioPlane = null;
    }

    @Override
    public double distance(T a, T b) {
        if (tioPlane == null)
            return DistanceUtils.pax((Series) a, (Series) b, this.wordSize);
        else
            return DistanceUtils.pax((Series) a, (Series) b, this.wordSize, tioPlane);

    }
}
