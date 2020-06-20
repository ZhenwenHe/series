package cn.edu.cug.cs.gtl.series.clustering;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;

/**
 * 聚类算法
 */
public interface clusterer<S,L> {

    void setDistanceMetrics(DistanceMetrics<S> var1);
    DistanceMetrics<S> getDistanceMetrics();
    DataSet<S, L> getTestSet();
    void setTestSet(DataSet<S, L> var1);

    Iterable<L> executeClustering(Iterable<S> var1);
    double score(DataSet<S, L> var1, Iterable<L> var2);

    default double score() {
        Iterable<L> labels = this.executeClustering(this.getTestSet().getSamples());
        return this.score(this.getTestSet(), labels);
    }


}
