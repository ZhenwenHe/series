package cn.edu.cug.cs.gtl.series.clustering;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import java.util.Iterator;

public abstract class DefaultClusterer<S,L> implements clusterer<S,L> {
    DataSet<S, L> testSet = null;
    DistanceMetrics<S> distanceMetrics = null;

    protected DefaultClusterer() {
    }

    public DefaultClusterer( DataSet<S, L> testSet, DistanceMetrics<S> distanceMetrics) {
        this.testSet = testSet;
        this.distanceMetrics = distanceMetrics;
    }

    public void setDistanceMetrics(DistanceMetrics<S> distanceMetrics) {
        this.distanceMetrics = distanceMetrics;
    }
    public DistanceMetrics<S> getDistanceMetrics() {
        return this.distanceMetrics;
    }

    public DataSet<S, L> getTestSet() {
        return this.testSet;
    }
    public void setTestSet(DataSet<S, L> dataSet) {
        this.testSet = dataSet;
    }

    public abstract Iterable<L> executeClustering(Iterable<S> var1);

    public double score(DataSet<S, L> testSet, Iterable<L> predictedLabels) {
        //默认使用外部指标
        this.testSet = testSet;
        double probs = 0.0D;
        int count = 0;
        int i = 0;

        for(Iterator var7 = predictedLabels.iterator(); var7.hasNext(); ++i) {
            L p = (L) var7.next();
            if (this.testSet.getLabel(i).equals(p)) {
                ++count;
            }
        }

        probs = (double)count * 1.0D / (double)i;
        return probs;
    }





}
