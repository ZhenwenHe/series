package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.protos.TSSeries;
import cn.edu.cug.cs.gtl.protos.Timestamp;
import cn.edu.cug.cs.gtl.protos.Value;
import cn.edu.cug.cs.gtl.io.Storable;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * 表示一对序列数据，包含X轴和Y轴，对于时序数据而言，
 * X轴表示时间，Y轴表示序列值，
 * 当X轴的数据为空的时候，则表示X是一个由0开始，步长为1的一个递增序列，器长度与Y轴一致
 */
public class Series implements Storable {

    private static final long serialVersionUID = -1765975818686067145L;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected TSSeries tsSeries=null;

    protected Series(TSSeries s){
        tsSeries=s;
    }

    public String getMeasurement(){
        return tsSeries.getMeasurement();
    }

    public String getFieldKey(){
        return tsSeries.getFieldKey();
    }

    public List<Value> getFieldValues(){
        return tsSeries.getFieldValueList();
    }

    public Map<String,String> getTagMap(){
        return tsSeries.getTagMap();
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
        return SeriesBuilder.doubleArray(l);
    }

    public String getLabel(){
        return tsSeries.getTagMap().get("label");
    }

    /**
     * Finds the maximal value in series.
     *
     * @param series The series.
     * @return The max value.
     */
    public static double max(double[] series) {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < series.length; i++) {
            if (max < series[i]) {
                max = series[i];
            }
        }
        return max;
    }


    /**
     * Finds the minimal value in series.
     *
     * @param series The series.
     * @return The min value.
     */
    public static double min(double[] series) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < series.length; i++) {
            if (min > series[i]) {
                min = series[i];
            }
        }
        return min;
    }

    /**
     * Computes the mean value of series.
     *
     * @param series The series.
     * @return The mean value.
     */
    public static double mean(double[] series) {
        double res = 0D;
        int count = 0;
        for (double tp : series) {
            res += tp;
            count += 1;
        }
        if (count > 0) {
            return res / ((Integer) count).doubleValue();
        }
        return Double.NaN;
    }

    /**
     * Computes the mean value of series.
     *
     * @param series The series.
     * @return The mean value.
     */
    public static double mean(int[] series) {
        double res = 0D;
        int count = 0;
        for (int tp : series) {
            res += (double) tp;
            count += 1;

        }
        if (count > 0) {
            return res / ((Integer) count).doubleValue();
        }
        return Double.NaN;
    }

    /**
     * Computes the median value of series.
     *
     * @param series The series.
     * @return The median value.
     */
    public static double median(double[] series) {
        double[] clonedSeries = series.clone();
        Arrays.sort(clonedSeries);

        double median;
        if (clonedSeries.length % 2 == 0) {
            median = (clonedSeries[clonedSeries.length / 2]
                    + (double) clonedSeries[clonedSeries.length / 2 - 1]) / 2;
        } else {
            median = clonedSeries[clonedSeries.length / 2];
        }
        return median;
    }

    /**
     * Compute the variance (方差) of series.
     *
     * @param series The series.
     * @return The variance.
     */
    public static double variance(double[] series) {
        double res = 0D;
        double mean = mean(series);
        int count = 0;
        for (double tp : series) {
            res += (tp - mean) * (tp - mean);
            count += 1;
        }
        if (count > 0) {
            return res / ((Integer) (count - 1)).doubleValue();
        }
        return Double.NaN;
    }

    /**
     * 标准差（Standard Deviation）,又常称均方差，是离均差平方的算术平均数的平方根，用σ表示。
     * 在概率统计中最常使用作为统计分布程度上的测量。标准差是方差的算术平方根。
     * 标准差能反映一个数据集的离散程度。
     * 平均数相同的两组数据，标准差未必相同。
     *
     * @param series The series.
     * @return the standard deviation.
     */
    public static double standardDeviation(double[] series) {
        double num0 = 0D;
        double sum = 0D;
        int count = 0;
        for (double tp : series) {
            num0 = num0 + tp * tp;
            count += 1;
        }
        double len = ((Integer) count).doubleValue();
        return Math.sqrt((len * num0 - sum * sum) / (len * (len - 1)));
    }

    /**
     * 标准差（Standard Deviation）
     *
     * @param a
     * @param b
     * @return
     */
    public static double standardDeviation(double a, double[] b) {
        double s = 0.0;
        int n = b.length;
        for (int i = 0; i < n; i++) {
            double d = (b[i] - a) * (b[i] - a);
            s += d;
        }
        s = s / n;
        return Math.sqrt(s);
    }

    /**
     * Z-Normalize routine.
     *
     * @param series                 the input series.
     * @param normalizationThreshold the zNormalization threshold value.
     * @return Z-normalized series.
     */
    public static double[] zNormalize(double[] series, double normalizationThreshold) {
        double[] res = new double[series.length];
        double sd = standardDeviation(series);
        if (sd < normalizationThreshold) {
            // return array of zeros
            return res;
        }
        double mean = mean(series);
        for (int i = 0; i < res.length; i++) {
            res[i] = (series[i] - mean) / sd;
        }
        return res;
    }

    /**
     * Extract subseries out of series.
     *
     * @param series The series array.
     * @param start  the fragment start.
     * @param end    the fragment end.
     * @return The subseries.
     * @throws IndexOutOfBoundsException If error occurs.
     */
    public static double[] subseries(double[] series, int start, int end)
            throws IndexOutOfBoundsException {
        if ((start > end) || (start < 0) || (end > series.length)) {
            throw new IndexOutOfBoundsException("Unable to extract subseries, series length: "
                    + series.length + ", start: " + start + ", end: " + String.valueOf(end - start));
        }
        return Arrays.copyOfRange(series, start, end);
    }


    Series() {

    }

    /**
     * @return
     */
    public double[] getValues() {
        List<Value> vs = tsSeries.getFieldValueList();
        double[] data = SeriesBuilder.doubleArray(vs);
        return data;
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
    public long length() {
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
        return Series.max(SeriesBuilder.doubleArray(tsSeries.getFieldValueList()));
    }

    /**
     * @return
     */
    public double min() {
        return Series.min(SeriesBuilder.doubleArray(tsSeries.getFieldValueList()));
    }

}
