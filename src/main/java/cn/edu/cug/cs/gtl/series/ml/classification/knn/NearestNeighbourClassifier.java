package cn.edu.cug.cs.gtl.series.ml.classification.knn;

import cn.edu.cug.cs.gtl.ml.dataset.Label;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;

public class NearestNeighbourClassifier extends cn.edu.cug.cs.gtl.ml.classification.knn.NearestNeighbourClassifier<Series, Label> {
    /**
     * Constructs a new Nearest Neighbor Classifier
     *
     * @param k              the number of neighbors to use
     * @param weighted       whether or not to weight the influence of neighbors by their distance
     * @param distanceMetric the method of computing distance between two vectors.
     */
    public NearestNeighbourClassifier(int k, boolean weighted, DistanceMetric<Series> distanceMetric) {
        super(k, weighted, distanceMetric);
    }
}
