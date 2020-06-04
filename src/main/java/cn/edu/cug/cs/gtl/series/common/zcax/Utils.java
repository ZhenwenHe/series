package cn.edu.cug.cs.gtl.series.common.zcax;

import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;

/**
 * convert pax to z-curve coding
 */
public class Utils {
    /**
     * @param ts
     * @param w  the total number of divisions, or the paaSize.
     * @return result An array of hexadecimal digits [0x0,0xf].
     * @brief Hexadecimal Aggregate approXimation (HAX). It transforms a numeric time series into a
     * set of hexadecimal digits. The algorithm was proposed by Zhenwen He et al. and
     * extends the PAA-based approach inheriting the original algorithm simplicity and low
     * computational complexity while providing satisfactory sensitivity and selectivity in range
     * query processing.
     */
    public static byte[] hax(Series ts, int w, TIOPlane tioPlane) {
        return tioPlane.mapZCAX(ts, w);
    }

    /**
     * 将单条时序数据转换成HAX ， 采用默认构建的TIOPlane；
     * 如果要两条时序数据之间生成的HAX具有可比性，需要采用相同的TIOPlane；
     *
     * @param ts
     * @param w
     * @return
     */
    public static byte[] hax(Series ts, int w) {
        TIOPlane tioPlane = TIOPlane.of(ts.min(), ts.max());
        return tioPlane.mapZCAX(ts, w);
    }

    /**
     * the distance between two hax digits
     *
     * @param h1 hax digit
     * @param h2 hax digit
     * @return the distance between two hax digits
     */
    public static double distance(byte h1, byte h2) {
        if (h1 == h2) return 0;

        //计算所属象限
        int h11 = (h1 / 4);
        int h22 = (h2 / 4);

        //1 如果在同一个象限内,最大距离为sqrt(2)
        if (h11 == h22) {
            //象限内的编号
            int a = h1 % 4;
            int b = h2 % 4;
            //计算象限内的二维网格坐标[0,0],[0,1],[1,1],[1,0]
            int x1 = a / 2;
            int y1 = a % 2;
            int x2 = b / 2;
            int y2 = b % 2;
            return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        } else {//2 如果不在同一个象限,采用网格距离+最大象限内距离sqrt(2)
            int[] h1xy = toGridXY(h1);
            int[] h2xy = toGridXY(h2);
            return Math.sqrt(2) + Math.sqrt((h1xy[0] - h2xy[0]) * (h1xy[0] - h2xy[0]) + (h1xy[1] - h2xy[1]) * (h1xy[1] - h2xy[1]));
        }
    }

    /**
     * the distance between two hax strings
     *
     * @param ts1 hax string
     * @param ts2 hax string
     * @return the distance between two hax strings
     */
    public static double distance(byte[] ts1, byte[] ts2) {
        int n = Math.min(ts1.length, ts2.length);
        int s = 0;
        for (int i = 0; i < n; ++i)
            s += distance(ts1[i], ts2[i]);
        return s;
    }

    /**
     * 计算两个时序数据对象之间的HAX距离
     *
     * @param s1 时序数据对象
     * @param s2 时序数据对象
     * @param w  paa的段数
     * @return 返回两个时序数据对象之间的HAX距离
     */
    public static double distance(Series s1, Series s2, int w, TIOPlane tioPlane) {
        if (tioPlane == null)
            tioPlane = TIOPlane.of(Math.min(s1.min(), s2.min()), Math.max(s1.max(), s2.max()));
        byte[] a = hax(s1, w, tioPlane);
        byte[] b = hax(s2, w, tioPlane);
        return distance(a, b);
    }

    /**
     * 计算两个时序数据对象之间的HAX距离,采用默认的TIOPlane
     *
     * @param s1 时序数据对象
     * @param s2 时序数据对象
     * @param w  paa的段数
     * @return 返回两个时序数据对象之间的HAX距离
     */
    public static double distance(Series s1, Series s2, int w) {
        TIOPlane tioPlane = TIOPlane.of(Math.min(s1.min(), s2.min()), Math.max(s1.max(), s2.max()));
        byte[] a = hax(s1, w, tioPlane);
        byte[] b = hax(s2, w, tioPlane);
        return distance(a, b);
    }

    /**
     * 计算两个数据集合中每条时序数据对象之间的距离
     *
     * @param s1 m条时序数据的集合
     * @param s2 n条时序数据的集合
     * @param w  paa的段数
     * @return 返回n行m列的2D数组 a
     * 也即，s1中的第0条与s2中的n条时序数据的距离存储在第0列；
     * s1中的第i条与s2中的第j条时序数据之间的距离为 a.get(j,i);
     * 获取s1中第i条与s2中所有时序数据对象的距离为一个n元列向量，也即 a.col(i)
     */
    public static Array distances(MultiSeries s1, MultiSeries s2, int w) {
        int m = (int) s1.count();
        int n = (int) s2.count();
        TIOPlane tioPlane = TIOPlane.of(Math.min(s1.min(), s2.min()), Math.max(s1.max(), s2.max()));
        double[] dist = new double[m * n];
        int k = 0;
        for (int i = 0; i < m; ++i) {
            Series s11 = s1.getSeries(i);
            for (int j = 0; j < n; ++j) {
                Series s22 = s2.getSeries(j);
                dist[k] = distance(s11, s22, w, tioPlane);
                ++k;
            }
        }
        return Array.of(n, m, dist);
    }

    /**
     * 将hexadecimalIndex[0,15], 映射到4X4的网格坐标
     *
     * @param hexadecimalIndex
     * @return
     */
    private static int[] toGridXY(byte hexadecimalIndex) {
        int[] xy = new int[2];
        switch (hexadecimalIndex) {
            case 15: {
                xy[0] = 0;
                xy[1] = 0;
                return xy;
            }
            case 14: {
                xy[0] = 1;
                xy[1] = 0;
                return xy;
            }
            case 13: {
                xy[0] = 0;
                xy[1] = 1;
                return xy;
            }
            case 12: {
                xy[0] = 1;
                xy[1] = 1;
                return xy;
            }
            case 11: {
                xy[0] = 2;
                xy[1] = 0;
                return xy;
            }
            case 10: {
                xy[0] = 3;
                xy[1] = 0;
                return xy;
            }
            case 9: {
                xy[0] = 2;
                xy[1] = 1;
                return xy;
            }
            case 8: {
                xy[0] = 3;
                xy[1] = 1;
                return xy;
            }
            case 7: {
                xy[0] = 0;
                xy[1] = 2;
                return xy;
            }
            case 6: {
                xy[0] = 1;
                xy[1] = 2;
                return xy;
            }
            case 5: {
                xy[0] = 0;
                xy[1] = 3;
                return xy;
            }
            case 4: {
                xy[0] = 1;
                xy[1] = 3;
                return xy;
            }
            case 3: {
                xy[0] = 2;
                xy[1] = 2;
                return xy;
            }
            case 2: {
                xy[0] = 3;
                xy[1] = 2;
                return xy;
            }
            case 1: {
                xy[0] = 2;
                xy[1] = 3;
                return xy;
            }
            case 0: {
                xy[0] = 3;
                xy[1] = 3;
                return xy;
            }

        }
        return null;
    }
}
