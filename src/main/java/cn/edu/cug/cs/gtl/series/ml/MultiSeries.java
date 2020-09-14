package cn.edu.cug.cs.gtl.series.ml;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.ml.dataset.CategoricalInfo;
import cn.edu.cug.cs.gtl.ml.dataset.Sample;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.io.SeriesBuilder;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiSeries implements cn.edu.cug.cs.gtl.series.common.MultiSeries {
    List<Series> seriesList=new ArrayList<>();

    public MultiSeries(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    /**
     * @return
     */
    @Override
    public int length() {
        if(seriesList.isEmpty())
            return 0;
        else
            return seriesList.get(0).length();
    }

    /**
     * @param i
     * @return
     */
    @Override
    public Series getSeries(int i) {
        return seriesList.get(i);
    }

    /**
     * @return the count of series in this object
     */
    @Override
    public long count() {
        return seriesList.size();
    }

    /**
     * @param i
     * @return
     */
    @Override
    public String getDefaultLabel(int i) {
        return getSeries(i).getDefaultLabel();
    }

    /**
     * get all labels
     *
     * @return
     */
    @Override
    public List<String> getDefaultLabels() {
        ArrayList<String> arrayList=new ArrayList<>();
        for(Series s: seriesList){
            arrayList.add(s.getDefaultLabel());
        }
        return arrayList;
    }

    /**
     * deep clone
     * @return
     */
    @Override
    public Object clone() {
        int s = seriesList.size();
        List<Series> sl=new ArrayList<>(s);
        for(int i=0;i<s;++i){
            sl.set(i,(Series) seriesList.get(i).clone());
        }
        return new MultiSeries(sl);
    }

    /**
     * @return
     */
    @Override
    public double max() {
        double m = -Double.MAX_VALUE;
        for(Series s : seriesList)
            m = Math.max(m,s.max());
        return m;
    }

    /**
     * @return
     */
    @Override
    public double min() {
        double m = Double.MAX_VALUE;
        for(Series s : seriesList)
            m = Math.min(m,s.min());
        return m;
    }

    /**
     * to time series list
     *
     * @return
     */
    @Override
    public List<Series> toList() {
        return seriesList;
    }

    /**
     * to time series array
     *
     * @return
     */
    @Override
    public Series[] toArray() {
        return seriesList.toArray(new Series[0]);
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        int s = dataInput.readInt();
        seriesList = new ArrayList<>(s);
        for(int i=0;i<s;++i){
            cn.edu.cug.cs.gtl.series.ml.Series series=new cn.edu.cug.cs.gtl.series.ml.Series(0);
            series.load(dataInput);
            seriesList.set(i,series);
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dataOutput) throws IOException {
        int s = seriesList.size();
        dataOutput.writeInt(s);
        for(Series i: seriesList)
            i.store(dataOutput);
        return true;
    }

    /**
     * 从TSV文件中读取数据构建MultiSeries对象
     * @param fileName
     * @return
     * @throws IOException
     */
    public static MultiSeries fromTSVFile(String fileName) throws IOException {
        List<Pair<String,double[]>> r = SeriesBuilder.readTSVFile(fileName);
        List<Series> ss=new ArrayList<>();
        for(Pair<String,double[]> p: r){
            cn.edu.cug.cs.gtl.series.ml.Series s = new cn.edu.cug.cs.gtl.series.ml.Series(null,p.second(),p.first());
            ss.add(s);
        }
        return new MultiSeries(ss);
    }

    /**
     * @return
     */
    public TrainSet<cn.edu.cug.cs.gtl.series.ml.Series> toTrainSet() {
        if(count()<=0) return null;
        List<String> labels= getDefaultLabels().stream().distinct().collect(Collectors.toList());
        CategoricalInfo [] categoricalInfos=new CategoricalInfo[1];
        categoricalInfos[0]=new CategoricalInfo("label",labels);
        int numericLength = getSeries(0).length();
        TrainSet<cn.edu.cug.cs.gtl.series.ml.Series> trainSet=
                new TrainSet<>(numericLength,categoricalInfos, cn.edu.cug.cs.gtl.series.ml.Series.class);

        for(Series s : this.seriesList){
            int [] categoricalValues = new int[1];
            categoricalValues[0]=categoricalInfos[0].indexOfOption(s.getDefaultLabel());
            Sample<cn.edu.cug.cs.gtl.series.ml.Series> seriesSample =
                    new Sample<>((cn.edu.cug.cs.gtl.series.ml.Series)s,categoricalValues,categoricalInfos);
            trainSet.addSample(seriesSample);
        }
        return trainSet;
    }

    /**
     * @return
     */
    public TestSet<cn.edu.cug.cs.gtl.series.ml.Series> toTestSet() {
        if(count()<=0) return null;
        List<String> labels= getDefaultLabels().stream().distinct().collect(Collectors.toList());
        CategoricalInfo [] categoricalInfos=new CategoricalInfo[1];
        categoricalInfos[0]=new CategoricalInfo("label",labels);
        int numericLength = getSeries(0).length();
        TestSet<cn.edu.cug.cs.gtl.series.ml.Series> testSet=
                new TestSet<>(numericLength,categoricalInfos, cn.edu.cug.cs.gtl.series.ml.Series.class);

        for(Series s : this.seriesList){

            int [] categoricalValues = new int[1];
            categoricalValues[0]=categoricalInfos[0].indexOfOption(s.getDefaultLabel());

            Sample<cn.edu.cug.cs.gtl.series.ml.Series> seriesSample =
                    new Sample<>((cn.edu.cug.cs.gtl.series.ml.Series)s,categoricalValues,categoricalInfos);
            testSet.addSample(seriesSample);
        }
        return testSet;
    }
}
