//package backup.clustering;
//
//import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
//import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
//
//public class FuzzyKmedoidClusterer<S,L> extends DefaultClusterer<S,L> {
//    int k;
//    int maxIteration;
//    double[][] distanceMatrix;
//
//    public FuzzyKmedoidClusterer() {
//
//    }
//
//    public FuzzyKmedoidClusterer(DataSet<S, L> testSet, DistanceMetrics<S> distanceMetrics, int k, int maxIteration) {
//        super(testSet, distanceMetrics);
//        this.k = k;
//        this.maxIteration = maxIteration;
//        canculateDistanceMatrix();
//    }
//
//
//    /**
//     * 计算数据集中各时间序列的距离矩阵，方便重复使用
//     */
//    private void canculateDistanceMatrix(){
//        long dataLen = this.testSet.size();
//        this.distanceMatrix = new double[(int)dataLen][(int)dataLen];
//        for (int i = 0; i < dataLen; i++) {
//            for (int j = 0; j < dataLen; j++) {
//                distanceMatrix[i][j] = this.distanceMetrics.distance(this.testSet.getSample(i),this.testSet.getSample(j));
//            }
//        }
//    }
//
//
//    @Override
//    public Iterable<L> executeClustering(Iterable<S> var1) {
//        return null;
//    }
//}
