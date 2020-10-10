package cn.edu.cug.cs.gtl.series.app.cmd;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;
import cn.edu.cug.cs.gtl.series.ml.MultiSeries;
import cn.edu.cug.cs.gtl.series.ml.Series;
import cn.edu.cug.cs.gtl.series.ml.classification.bayes.NaiveBayesClassifier;
import cn.edu.cug.cs.gtl.series.ml.classification.knn.NearestNeighbourClassifier;
import cn.edu.cug.cs.gtl.series.ml.distances.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class NaiveBayesApp {
    public static final Logger LOGGER = LoggerFactory.getLogger(NaiveBayesApp.class); //slf4j

    List<Pair<String, String>> dataFiles=null;//所有有效的数据集文件对，每对包含一个训练文件和一个测试文件
    Pair<Integer, Integer> paaSizeRange=null;
    Pair<Integer, Integer> alphabetRange=null;


    public NaiveBayesApp(List<Pair<String, String>> dataFiles, Pair<Integer, Integer> paaSizeRange, Pair<Integer, Integer> alphabetRange) {
        this.dataFiles = dataFiles;
        this.paaSizeRange = paaSizeRange;
        this.alphabetRange = alphabetRange;
    }



    public void HAX(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TIOPlane tioPlane =
                        TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()));
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                for(int paaSize=this.paaSizeRange.first();paaSize<this.paaSizeRange.second();++paaSize){
                    HaxDistanceMetric<Series> disFunc = new HaxDistanceMetric<> (paaSize, tioPlane);
                    NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                    String name = p.first().substring(p.first().lastIndexOf(File.separator)+1);
                    name=name.substring(0,name.indexOf('_'));
                    LOGGER.info(name+ " NaiveBayes HAX "+ String.valueOf(paaSize)+" 16 "+String.valueOf(nn.score()));
                    System.gc();
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ZCAX(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TIOPlane tioPlane =
                        TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()));
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                for(int paaSize=this.paaSizeRange.first();paaSize<this.paaSizeRange.second();++paaSize){
                    ZCaxDistanceMetric<Series> disFunc = new ZCaxDistanceMetric<> (paaSize, tioPlane);
                    NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                    String name = p.first().substring(p.first().lastIndexOf(File.separator)+1);
                    name=name.substring(0,name.indexOf('_'));
                    LOGGER.info(name+ " NaiveBayes ZCAX "+ String.valueOf(paaSize)+" 16 "+String.valueOf(nn.score()));
                    System.gc();
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PAX(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TIOPlane tioPlane =
                        TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()));
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                for(int paaSize=this.paaSizeRange.first();paaSize<this.paaSizeRange.second();++paaSize){
                    PaxDistanceMetric<Series> disFunc = new PaxDistanceMetric<>(paaSize, tioPlane);
                    NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                    String name = p.first().substring(p.first().lastIndexOf(File.separator)+1);
                    name=name.substring(0,name.indexOf('_'));
                    LOGGER.info(name+ " NaiveBayes PAX "+ String.valueOf(paaSize)+" 16 "+String.valueOf(nn.score()));
                    System.gc();
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HCAX(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TIOPlane tioPlane =
                        TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()));
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                for(int paaSize=this.paaSizeRange.first();paaSize<this.paaSizeRange.second();++paaSize){
                    HCaxDistanceMetric<Series> disFunc = new HCaxDistanceMetric<>(paaSize, tioPlane);
                    NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                    String name = p.first().substring(p.first().lastIndexOf(File.separator)+1);
                    name=name.substring(0,name.indexOf('_'));
                    LOGGER.info(name+ " NaiveBayes HCAX "+ String.valueOf(paaSize)+" 16 "+String.valueOf(nn.score()));
                    System.gc();
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SAX(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TIOPlane tioPlane =
                        TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()));
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                for(int paaSize=this.paaSizeRange.first();paaSize<this.paaSizeRange.second();++paaSize){
                    for(int alpha =this.alphabetRange.first();alpha<this.alphabetRange.second();++alpha) {
                        SaxDistanceMetric<Series> disFunc = new SaxDistanceMetric<>(paaSize,  alpha);
                        NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                        String name = p.first().substring(p.first().lastIndexOf(File.separator) + 1);
                        name = name.substring(0, name.indexOf('_'));
                        LOGGER.info(name + " NaiveBayes SAX " + String.valueOf(paaSize) + " " + String.valueOf(alpha)+ " " + String.valueOf(nn.score()));
                        System.gc();
                    }
                    System.gc();
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ESAX(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());

                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                for(int paaSize=this.paaSizeRange.first();paaSize<this.paaSizeRange.second();++paaSize){
                    for(int alpha =this.alphabetRange.first();alpha<this.alphabetRange.second();++alpha) {
                        ESaxDistanceMetric<Series> disFunc = new ESaxDistanceMetric<>(paaSize,  alpha);
                        NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                        nn.setTestSet(testSet);
                        nn.setTrainSet(trainSet);
                        String name = p.first().substring(p.first().lastIndexOf(File.separator) + 1);
                        name = name.substring(0, name.indexOf('_'));
                        LOGGER.info(name + " NaiveBayes ESAX " + String.valueOf(paaSize) + " " + String.valueOf(alpha)+ " " + String.valueOf(nn.score()));
                        System.gc();
                    }
                    System.gc();
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SAXTD(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                for(int paaSize=this.paaSizeRange.first();paaSize<this.paaSizeRange.second();++paaSize){
                    for(int alpha =this.alphabetRange.first();alpha<this.alphabetRange.second();++alpha) {
                        SaxTDDistanceMetric<Series> disFunc = new SaxTDDistanceMetric<>(paaSize,  alpha);
                        NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                        nn.setTestSet(testSet);
                        nn.setTrainSet(trainSet);
                        String name = p.first().substring(p.first().lastIndexOf(File.separator) + 1);
                        name = name.substring(0, name.indexOf('_'));
                        LOGGER.info(name + " NaiveBayes SAXTD " + String.valueOf(paaSize) + " " + String.valueOf(alpha)+ " " + String.valueOf(nn.score()));
                        System.gc();
                    }
                    System.gc();
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ED(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                EuclideanDistanceMetric<Series> disFunc = new EuclideanDistanceMetric<>();
                NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                nn.setTestSet(testSet);
                nn.setTrainSet(trainSet);
                String name = p.first().substring(p.first().lastIndexOf(File.separator) + 1);
                name = name.substring(0, name.indexOf('_'));
                LOGGER.info(name + " NaiveBayes ED " + String.valueOf(0) + " " + String.valueOf(0)+ " " + String.valueOf(nn.score()));
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DTW(){
        try {
            for(Pair<String,String> p : this.dataFiles){
                MultiSeries train = MultiSeries.fromTSVFile(p.first());
                MultiSeries test = MultiSeries.fromTSVFile(p.second());
                TrainSet<Series> trainSet = train.toTrainSet();
                TestSet<Series> testSet = test.toTestSet();

                DTWDistanceMetric<Series> disFunc = new DTWDistanceMetric<>();
                NaiveBayesClassifier nn = new NaiveBayesClassifier(trainSet,testSet,disFunc);
                nn.setTestSet(testSet);
                nn.setTrainSet(trainSet);
                String name = p.first().substring(p.first().lastIndexOf(File.separator) + 1);
                name = name.substring(0, name.indexOf('_'));
                LOGGER.info(name + " NaiveBayes DTW " + String.valueOf(0) + " " + String.valueOf(0)+ " " + String.valueOf(nn.score()));
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
