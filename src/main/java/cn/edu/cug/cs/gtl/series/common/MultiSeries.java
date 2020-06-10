package cn.edu.cug.cs.gtl.series.common;


import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.protos.TSMultiSeries;
import cn.edu.cug.cs.gtl.protos.TSSeries;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 代表多值时序数据，包含一个时间轴X和多个（至少一个）Y轴序列，每个Y轴上的序列具有一个标签
 */
public class MultiSeries  implements Storable{
    private static final long serialVersionUID = 225842638554087888L;

    protected TSMultiSeries tsMultiSeries=null;

    /**
     *
     */
    MultiSeries() {

    }


    /**
     * @return
     */

    public long length() {
        if(tsMultiSeries==null) return 0;
        if(tsMultiSeries.getSeriesCount()==0) return 0;
        return this.tsMultiSeries.getSeries(0).getFieldValueCount();
    }

    /**
     * @param i
     * @return
     */
    public Series getSeries(int i) {
        TSSeries s = tsMultiSeries.getSeries(i);
        return new Series(s);
    }

    /**
     * @return the count of series in this object
     */

    public long count() {
        if(tsMultiSeries==null) return 0;
        return tsMultiSeries.getSeriesCount();
    }

    /**
     * @param i
     * @return
     */
    public String getLabel(int i) {
        if(tsMultiSeries==null) return "";
        return tsMultiSeries.getSeries(i).getTagMap().get("label");
    }

    /**
     * get all labels
     * @return
     */
    public List<String> getLabels() {
        List<TSSeries> list= tsMultiSeries.getSeriesList();
        ArrayList<String> asz= new ArrayList<>();
        for(TSSeries s: list){
            asz.add(s.getTagMap().get("label"));
        }
        return asz;
    }



    /**
     * @return
     */
    @Override
    public Object clone() {
        MultiSeries ms = new MultiSeries();
        ms.tsMultiSeries=this.tsMultiSeries.toBuilder().build();
        return ms;
    }

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        int s = in.readInt();
        if (s > 0) {
            byte[] bs = new byte[s];
            in.readFully(bs);
            tsMultiSeries=TSMultiSeries.parseFrom(bs);
        }
        return true;
    }

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param out ，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        int s = 0; //this.data.length;
        if(tsMultiSeries==null) {
            out.writeInt(s);
            return true;
        }
        else{
            byte[] bs = tsMultiSeries.toByteArray();
            s=bs.length;
            out.writeInt(s);
            out.write(bs);
            return true;
        }
    }

    /**
     * @return
     */
    public TrainSet<Series, String> toTrainSet() {
        List<Series> ss = toList();
        List<String> ls = getLabels();
        return new TrainSet<Series,String>(ss, ls);
    }

    /**
     * @return
     */
    public TestSet<Series, String> toTestSet() {
        List<Series> ss = toList();
        List<String> ls = getLabels();
        return new TestSet<Series,String>(ss, ls);
    }

    /**
     * @return
     */

    public double max() {
        double r = Double.MAX_VALUE;
        Series[] ss = toArray();
        for (Series a : ss) {
            r = Math.min(r, a.max());
        }
        return r;
    }

    /**
     * @return
     */

    public double min() {
        double r = Double.MAX_VALUE;
        Series[] ss = toArray();
        for (Series a : ss) {
            r = Math.min(r, a.min());
        }
        return r;
    }

    /**
     * to time series list
     *
     * @return
     */
    public List<Series> toList() {
        ArrayList<Series> ss = new ArrayList<>();
        List<TSSeries> list=tsMultiSeries.getSeriesList();
        for(TSSeries t: list){
            ss.add(new Series(t));
        }
        return ss;
    }

    /**
     * to time series array
     *
     * @return
     */
    public Series[] toArray() {
        int c = tsMultiSeries.getSeriesCount();
        Series[] ss = new Series[c];
        List<TSSeries> list=tsMultiSeries.getSeriesList();
        int i=0;
        for(TSSeries t: list){
            ss[i]=new Series(t);
            ++i;
        }
        return ss;
    }
}
