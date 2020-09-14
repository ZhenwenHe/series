//package backup.clustering;
//
//import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
//import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class KmedoidsClusterer<S,L> extends DefaultClusterer<S,L> {
//    int k;
//    int maxIteration;
//    double[][] distanceMatrix;
//
//    public KmedoidsClusterer() {
//    }
//
//    public KmedoidsClusterer(DataSet<S, L> testSet, DistanceMetrics<S> distanceMetrics, int k, int maxIteration) {
//        super(testSet, distanceMetrics);
//        this.k = k;
//        this.maxIteration = maxIteration;
//        canculateDistanceMatrix();
//    }
//
//
//    /**
//     * 聚类方法
//     * @param var1 数据集
//     * @return 数据集中各数据的样本标签
//     */
//    @Override
//    public Iterable<L> executeClustering(Iterable<S> var1) {
//        List<L> clusterLabelList = new ArrayList();
//        List<Integer> central = genarateRandomCentral();
//        long dataLen = this.testSet.size();
//        int pos = 0;
//        double minDis = Double.MAX_VALUE;
//        L label = null;
//        while(maxIteration-- > 0){
//            clusterLabelList.clear();
//            for (int i = 0; i < dataLen; i++) {
//                minDis = Double.MAX_VALUE;
//                for (int j = 0; j < k; j++) {
//                    double tempDis = this.distanceMatrix[i][central.get(j)];
//                    if (tempDis < minDis) {
//                        minDis = tempDis;
//                        pos = central.get(j);
//                    }
//                }
//                label = this.testSet.getLabel(pos);
//                clusterLabelList.add(label);
//            }
//            List<Integer> newCentral = centralIteration(central,clusterLabelList);
//            if(compareNewOldCentral(central,newCentral) <= 0.1)//样本中心点距离均值小于0.1时，聚类完成
//                break;
//        }
//
//        return clusterLabelList;
//    }
//
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
//
//    /**
//     * 生成随机样本中心点
//     * @return 样本在数据集中的序号
//     */
//    private List<Integer> genarateRandomCentral(){
//        ArrayList<Integer> res = new ArrayList<>();
//        int max =(int)  this.testSet.size();
//        Random random = new Random();
//        for (int i = 0; i < k; ) {
//            int tmp = random.nextInt(max);
//            if(res.contains(tmp)) continue;
//            else {
//                res.add(tmp);
//                i++;
//            }
//        }
//        return res;
//    }
//
//
//    /**
//     * 样本中心点迭代
//     * @param central 旧样本中心点
//     * @param clusterLabelList 簇信息
//     * @return
//     */
//    private List<Integer> centralIteration(List<Integer> central,List<L> clusterLabelList){
//        List<Integer> newCentral = new ArrayList<>();
//        long dataLen = this.testSet.size();
//        for (int i = 0; i < k; i++) {
//            L clusterLable = this.testSet.getLabel(central.get(i));
//            List<Integer> clusterIndex = new ArrayList<>();
//            for (int j = 0; j < dataLen; j++) {
//                if(clusterLabelList.get(j).equals(clusterLable)){
//                    clusterIndex.add(j);
//                }
//            }
//            double sumDis = Double.MAX_VALUE;
//            int centralPos = central.get(i);
//            for (int j = 0; j < clusterIndex.size(); j++) {
//                double tmpDis = 0;
//                for (int l = 0; l < clusterIndex.size(); l++) {
//                    tmpDis += this.distanceMatrix[clusterIndex.get(j)][clusterIndex.get(l)] * this.distanceMatrix[clusterIndex.get(j)][clusterIndex.get(l)];
//                }
//                if(tmpDis < sumDis){
//                    sumDis = tmpDis;
//                    centralPos = clusterIndex.get(j);
//                }
//
//            }
//            newCentral.add(centralPos);
//        }
//        return newCentral;
//    }
//
//
//    /**
//     * 计算样本中心点迭代之后，新旧中心点距离平均值
//     * @param oldCentral
//     * @param newCentral
//     * @return
//     */
//    private Double compareNewOldCentral(List<Integer> oldCentral, List<Integer> newCentral){
//        double dis = 0;
//        for (int i = 0; i < k; i++) {
//            dis += this.distanceMatrix[oldCentral.get(i)][newCentral.get(i)];
//        }
//
//        return dis/k;
//    }
//
//}
