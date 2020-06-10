package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;

public class SaxDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int alphabet;
    protected int wordSize;

    public SaxDistanceMetrics(int w, int alphabet) {
        this.alphabet = alphabet;
        this.wordSize = w;
    }

    @Override
    public double distance(T a, T b) {
        return DistanceUtils.sax( (Series)a,  (Series)b, this.wordSize, this.alphabet);
    }
}
