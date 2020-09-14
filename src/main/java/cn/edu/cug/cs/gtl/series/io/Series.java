package cn.edu.cug.cs.gtl.series.io;

import cn.edu.cug.cs.gtl.protos.TSSeries;
import cn.edu.cug.cs.gtl.protos.Timestamp;
import cn.edu.cug.cs.gtl.protos.Value;
import cn.edu.cug.cs.gtl.protoswrapper.ValueWrapper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
/**
 * 表示一对序列数据，包含X轴和Y轴，对于时序数据而言，
 * X轴表示时间，Y轴表示序列值，
 * 当X轴的数据为空的时候，则表示X是一个由0开始，步长为1的一个递增序列，器长度与Y轴一致
 */
public class Series implements cn.edu.cug.cs.gtl.series.common.Series {

    private static final long serialVersionUID = -1765975818686067145L;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    TSSeries tsSeries=null;

    protected Series(TSSeries s){
        tsSeries=s;
    }

    public String getMeasurement(){
        return tsSeries.getSchema().getMeasurement();
    }

    public String getFieldKey(){
        return tsSeries.getSchema().getFieldKey();
    }

    public SeriesSchema getSchema(){
        return new SeriesSchema(tsSeries.getSchema());
    }

    public List<Value> getFieldValues(){
        return tsSeries.getFieldValueList();
    }

    public Map<String,String> getTagMap(){
        return tsSeries.getSchema().getTagMap();
    }

    public List<Timestamp> getTimeValues(){
        return tsSeries.getTimeValueList();
    }

    public double[] getDataX() {
        List<Timestamp> l = getTimeValues();
        int i=0;
        if(l==null || l.size()==0){
            double [] xData = new double[tsSeries.getFieldValueCount()];
            for(i=0;i<xData.length;++i){
                xData[i]=i;
            }
            return xData;
        }
        else{
            double [] xData = new double[tsSeries.getTimeValueCount()];
            for(Timestamp t: l){
                xData[i]=t.getTime();
                ++i;
            }
            return xData;
        }
    }

    public double[] getDataY() {
        List<Value> l = getFieldValues();
        return ValueWrapper.arrayOf(l);
    }

    public String getDefaultLabel(){
        return tsSeries.getSchema().getTagMap().get("label");
    }


    Series() {

    }


    /**
     * 对象深拷贝
     *
     * @return 返回新的对象
     */
    @Override
    public Object clone() {
        TSSeries s0 = tsSeries.toBuilder().build();
        Series s1 = new Series();
        s1.tsSeries=s0;
        return s1;
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
            tsSeries=TSSeries.parseFrom(bs);
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

        int s = 0;//this.data.length;
        if(tsSeries==null) {
            out.writeInt(s);
            return true;
        }
        else{
            byte[] bs = tsSeries.toByteArray();
            s=bs.length;
            out.writeInt(s);
            out.write(bs);
            return true;
        }
    }

    /**
     * @return the count of series in this object
     */
    public long count() {
        return 1;
    }


    /**
     * return the length of the series
     *
     * @return
     */
    public int length() {
        return tsSeries == null ? 0 : tsSeries.getFieldValueCount();
    }


    /**
     * calculate sub-series
     *
     * @param paaSize
     * @param paaIndex
     * @return
     */
    public Series subseries(int paaSize, int paaIndex) {
        double [] dataY = getDataY();
        double[] tsY = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(dataY, paaSize, paaIndex);
        double [] fDataX = getDataX();
        double[] tsX = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(fDataX, paaSize, paaIndex);
        return new SeriesBuilder()
                .addValues(tsX,tsY)
                .setMeasurement(getMeasurement())
                .setFieldKey(getFieldKey())
                .addTags(getTagMap())
                .build();
    }

    /**
     * @return
     */
    public double max() {
        double [] t = SeriesBuilder.doubleArray(tsSeries.getFieldValueList());
        return cn.edu.cug.cs.gtl.series.common.Series.max(t);
    }

    /**
     * @return
     */
    public double min() {
        return cn.edu.cug.cs.gtl.series.common.Series.min(
                SeriesBuilder.doubleArray(tsSeries.getFieldValueList()));
    }



    public SeriesBuilder toBuilder(){
        return SeriesBuilder.toBuilder(this);
    }

    /**
     * 简化成数字型的Series,便于计算
     * @return
     */
    public cn.edu.cug.cs.gtl.series.common.Series simplify(){
        cn.edu.cug.cs.gtl.series.ml.Series s = new cn.edu.cug.cs.gtl.series.ml.Series(null,getDataY(),getDefaultLabel());
        return s;
    }
}
