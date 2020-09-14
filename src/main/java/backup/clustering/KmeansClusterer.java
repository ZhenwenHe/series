//package backup.clustering;
//
//import cn.edu.cug.cs.gtl.ml.clustering.DefaultClusterer;
//import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
//import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
//import cn.edu.cug.cs.gtl.series.io.Series;
//import cn.edu.cug.cs.gtl.series.io.SeriesBuilder;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class KmeansClusterer<S extends Series, L extends String> extends DefaultClusterer<S,L> {
//
//    int k;
//    int maxIteration;
//    double[][] distanceMatrix;
//
//    public KmeansClusterer() {
//
//    }
//
//    @Override
//    public Iterable<L> predict(Iterable<S> iterable) {
//        return null;
//    }
//
//    public KmeansClusterer(DataSet<S, L> testSet, DistanceMetrics<S> distanceMetrics, int k, int maxIteration) {
//        super(testSet, distanceMetrics);
//        this.k = k;
//        this.maxIteration = maxIteration;
//        canculateDistanceMatrix();
//
//    }
//
//    /**
//     * 计算数据集中各时间序列的距离矩阵，方便重复使用
//     */
//    private void canculateDistanceMatrix(){
//        long dataLen = testSet.size();
//        this.distanceMatrix = new double[(int)dataLen][(int)dataLen];
//        for (int i = 0; i < dataLen; i++) {
//            for (int j = 0; j < dataLen; j++) {
//                distanceMatrix[i][j] = this.distanceMetrics.distance(this.testSet.getSample(i),this.testSet.getSample(j));
//            }
//        }
//    }
//
//    /**
//     * 聚类方法
//     * @param var1
//     * @return
//     */
//    @Override
//    public Iterable<L> executeClustering(Iterable<S> var1) {
//        List<L> clusterLabelList = new ArrayList();
//        List<S> central = genarateRandomCentral();
//        long dataLen = this.testSet.size();
//        int pos = 0;
//        double minDis = Double.MAX_VALUE;
//        L label = null;
//        while(maxIteration-- > 0){
//            clusterLabelList.clear();
//            for (int i = 0; i < dataLen; i++) {
//                minDis = Double.MAX_VALUE;
//                for (int j = 0; j < k; j++) {
//                    double tempDis = this.distanceMetrics.distance(this.testSet.getSample(i),central.get(j));
//                    if (tempDis < minDis) {
//                        minDis = tempDis;
//                        pos = j;
//                    }
//                }
//                label = (L) central.get(pos).getLabel();
//                clusterLabelList.add(label);
//            }
//            List<S> newCentral = null;
//            try{
//                newCentral = centralIteration(central,clusterLabelList);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            if(compareNewOldCentral(central,newCentral) <= 0.1)//样本中心点距离均值小于0.1时，聚类完成
//                break;
//        }
//
//        return clusterLabelList;
//
//    }
//
//    /**
//     * 生成随机样本中心点
//     * @return 样本在数据集中的序号
//     */
//    private List<S> genarateRandomCentral(){
//        ArrayList<S> res = new ArrayList<>();
//        int max =(int)  this.testSet.size();
//        Random random = new Random();
//        for (int i = 0; i < k; ) {
//            int tmp = random.nextInt(max);
//            if(res.contains(tmp)) continue;
//            else {
//                res.add(this.testSet.getSample(tmp));
//                i++;
//            }
//        }
//        return res;
//    }
//
//    /**
//     * 样本中心点迭代
//     * @param central 旧样本中心点
//     * @param clusterLabelList 簇信息
//     * @return
//     */
//    private List<S> centralIteration(List<S> central, List<L> clusterLabelList) throws IOException {
//        List<S> newCentral = new ArrayList<>();
//        long dataLen = this.testSet.size();
//        for (int i = 0; i < k; i++) {
//            String clusterLable = central.get(i).getLabel();
//
//            List<Integer> clusterIndex = new ArrayList<>();
//            for (int j = 0; j < dataLen; j++) {
//                if(clusterLabelList.get(j).equals(clusterLable)){
//                    clusterIndex.add(j);
//                }
//            }
//
//            long len = ((cn.edu.cug.cs.gtl.series.common.Series)this.testSet.getSample(0)).length();
//            double[] xData = new double[(int)len];
//            double[] yData = new double[(int)len];
//            for (int j = 0; j < clusterIndex.size(); j++) {
//                double[] tmpX = ((cn.edu.cug.cs.gtl.series.common.Series)this.testSet.getSample(clusterIndex.get(j))).getDataX();
//                double[] tmpY = ((cn.edu.cug.cs.gtl.series.common.Series)this.testSet.getSample(clusterIndex.get(j))).getDataY();
//                for (int l = 0; l < len; l++) {
//                    xData[l] += tmpX[l];
//                    yData[l] += tmpY[l];
//                }
//            }
//            for (int j = 0; j < len; j++) {
//                xData[j] = xData[j]/clusterIndex.size();
//                yData[j] = yData[j]/clusterIndex.size();
//            }
//            Series tmp = SeriesBuilder
//                    .newBuilder()
//                    .addFieldValues(yData)
//                    .addTimeValues(xData)
//                    .build();
//            newCentral.add((S) tmp);
//        }
//        return newCentral;
//    }
//
//    /**
//     * 计算样本中心点迭代之后，新旧中心点距离平均值
//     * @param oldCentral
//     * @param newCentral
//     * @return
//     */
//    private Double compareNewOldCentral(List<S> oldCentral, List<S> newCentral){
//        double dis = 0;
//        for (int i = 0; i < k; i++) {
//            dis += this.distanceMetrics.distance(oldCentral.get(i),newCentral.get(i));
//        }
//
//        return dis/k;
//    }
//
//}
