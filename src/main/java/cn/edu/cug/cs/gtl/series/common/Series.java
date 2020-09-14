package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.io.Storable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * 序列接口，每个序列包含两列数据，一列为X值，一列为Y值；其中X值默认为空，表示为默认的递增时间序列，Y值表示测量的序列值；
 * 此外，序列对象还有一系列的标签，具体完整的概论参见
 * ref : https://docs.influxdata.com/influxdb/v1.8/concepts/key_concepts/
 * 其子类包括：
 * 1）cn.edu.cug.cs.gtl.series.io.Series, 这是一个完整通用的序列类，适合存储，但不适合做快速计算
 * 2）cn.edu.cug.cs.gtl.series.ml.Series，这是一个简化版本的以数值为主的序列类，适合做快速计算
 *
 */
public interface Series extends Storable {
    Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 获取X列的值
     * @return
     */
    double[] getDataX();

    /**
     * 将X列以时间戳的形式返回
     * @return
     */
    default long[] getTimestamps(){
        double[] xs = getDataX();
        int s = xs.length;
        long [] r = new long[s];
        for(int i=0;i<s;++i){
            r[i]=(long)xs[i];
        }
        return r;
    }

    /**
     * 获取默认标签，也就是用于分类和聚类的标签值
     * @return
     */
    String getDefaultLabel();

    /**
     * 获取序列的Y列值
     * @return
     */
    double[] getDataY();

    /**
     * 获取序列的Y列的值
     * @return
     */
    default double[] getValues(){
        return getDataY();
    }


    /**
     * Finds the maximal value in series.
     *
     * @param series The series.
     * @return The max value.
     */
    static double max(double[] series) {
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
    static double min(double[] series) {
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
    static double mean(double[] series) {
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
    static double mean(int[] series) {
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
    static double median(double[] series) {
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
    static double variance(double[] series) {
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
    static double standardDeviation(double[] series) {
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
    static double standardDeviation(double a, double[] b) {
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
    static double[] zNormalize(double[] series, double normalizationThreshold) {
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
    static double[] subseries(double[] series, int start, int end)
            throws IndexOutOfBoundsException {
        if ((start > end) || (start < 0) || (end > series.length)) {
            throw new IndexOutOfBoundsException("Unable to extract subseries, series length: "
                    + series.length + ", start: " + start + ", end: " + String.valueOf(end - start));
        }
        return Arrays.copyOfRange(series, start, end);
    }


    /**
     * 对象深拷贝
     *
     * @return 返回新的对象
     */
    @Override
    Object clone();


    /**
     * @return the count of series in this object
     */
    long count() ;


    /**
     * return the length of the series
     *
     * @return
     */
    int length();


    /**
     * calculate sub-series
     *
     * @param paaSize
     * @param paaIndex
     * @return
     */
    Series subseries(int paaSize, int paaIndex);

    /**
     * 获取Y列的最大值
     * @return
     */
    double max() ;

    /**
     * 获取Y列的最小值
     * @return
     */
    double min() ;

    /**
     * 如果是io模块的Series，则将其转变为ml模块的Series，
     * 否则返回自身。
     * @return
     */
    Series simplify();
}
