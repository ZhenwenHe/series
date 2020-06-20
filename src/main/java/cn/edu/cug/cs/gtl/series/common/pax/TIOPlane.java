package cn.edu.cug.cs.gtl.series.common.pax;

import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.series.common.Series;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;

public class TIOPlane implements Storable {

    private static final long serialVersionUID = -5054832890966265894L;

    private double minValue;
    private double maxValue;
    private double minAngle;
    private double maxAngle;

    /**
     * @param minValue
     * @param maxValue
     * @param minAngle
     * @param maxAngle
     * @return
     */
    public static TIOPlane of(double minValue, double maxValue, double minAngle, double maxAngle) {
        return new TIOPlane(minValue, maxValue, minAngle, maxAngle);
    }

    /**
     * @param minValue
     * @param maxValue
     * @return
     */
    public static TIOPlane of(double minValue, double maxValue) {
        return new TIOPlane(minValue, maxValue, -90, 90);
    }

    /**
     * @param s1
     * @param s2
     * @return
     */
    public static TIOPlane of(Series s1, Series s2) {
        return new TIOPlane(Math.min(s1.min(), s2.min()), Math.max(s1.max(), s2.max()), -90, 90);
    }

    /**
     * create a new Series Object from a byte array,
     * this byte array must be generated by storeToByteArray() function
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static TIOPlane of(byte[] bytes) throws IOException {
        TIOPlane s = new TIOPlane();
        s.loadFromByteArray(bytes);
        return s;
    }

    /**
     * create a new Series Object from a input stream,
     * it must be generated by write() function
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static TIOPlane of(InputStream inputStream) throws IOException {
        TIOPlane s = new TIOPlane();
        s.read(inputStream);
        return s;
    }

    /**
     * @param minValue
     * @param maxValue
     * @param minAngle
     * @param maxAngle
     */
    TIOPlane(double minValue, double maxValue, double minAngle, double maxAngle) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    /**
     *
     */
    private TIOPlane() {

    }

    /**
     * @param s1
     * @param s2
     */
    TIOPlane(Series s1, Series s2) {
        this.maxValue = Math.max(s1.max(), s2.max());
        this.minValue = Math.min(s1.min(), s2.min());
        this.minAngle = -90;
        this.maxAngle = 90;
    }

    /**
     * @return
     */
    public double getMinValue() {
        return minValue;
    }


    /**
     * @return
     */
    public double getMaxValue() {
        return maxValue;
    }


    /**
     * @return
     */
    public double getMinAngle() {
        return minAngle;
    }

    /**
     * @return
     */
    public double getMaxAngle() {
        return maxAngle;
    }


    /**
     * 将TIOPoint映射到4X4的网格中
     *    5 6   9 10
     *    4 7   8 11
     *    3 2   13 12
     *    0 1   14 15
     * @param tioPoint
     * @return hilbet curve index [0-15]
     */
    public byte hilbertMap(TIOPoint tioPoint) {
        TIOPoint p = normalize(tioPoint);
        //判断所属象限
        double v = p.getValue() - 0.5;
        double a = p.getAngle() - 0.5;
        if (v >= 0 && a >= 0) {//第一象限
            if (v <= 0.25 && a <= 0.25) {
                return 8;
            } else if (v <= 0.25 && a > 0.25) {
                return 11;
            } else if (v > 0.25 && a <= 0.25) {
                return 9;
            } else {
                return 10;
            }
        } else if (v >= 0 && a < 0) { //第二象限
            if (v <= 0.25 && a >= -0.25) {
                return 7;
            } else if (v <= 0.25 && a < -0.25) {
                return 4;
            } else if (v > 0.25 && a >= -0.25) {
                return 6;
            } else {
                return 5;
            }
        } else if (v < 0 && a < 0) {//第三象限
            if (v >= -0.25 && a >= -0.25) {
                return 2;
            } else if (v >= -0.25 && a < -0.25) {
                return 3;
            } else if (v < -0.25 && a >= -0.25) {
                return 1;
            } else {
                return 0;
            }
        } else {//第四象限
            if (v >= -0.25 && a <= 0.25) {
                return 13;
            } else if (v >= -0.25 && a > 0.25) {
                return 12;
            } else if (v < -0.25 && a <= 0.25) {
                return 14;
            } else {
                return 15;
            }
        }
    }

    /**
     * @param tioPoints
     * @return
     */
    public byte[] hilbertMap(TIOPoints tioPoints) {
        int s = tioPoints.size();
        byte[] bytes = new byte[s];
        s = 0;
        for (TIOPoint p : tioPoints) {
            bytes[s] = hilbertMap(p);
            s++;
        }
        return bytes;
    }

    /**
     * @param s
     * @param paaSize
     * @return
     */
    public byte[] hilbertMap(Series s, int paaSize) {
        TIOPoints tioPoints = cn.edu.cug.cs.gtl.series.common.pax.Utils.pax(s, paaSize);
        return hilbertMap(tioPoints);
    }
    /**
     * 将TIOPoint映射到4X4的网格中
     *    7  6     2  3
     *    5  4     0  1
     *    13 12    8  9
     *    15 14   10  11
     * @param tioPoint
     * @return hexadecimal index [0-15]
     */
    public byte mapHAX(TIOPoint tioPoint) {
        TIOPoint p = normalize(tioPoint);
        //判断所属象限
        double v = p.getValue() - 0.5;
        double a = p.getAngle() - 0.5;
        if (v >= 0 && a >= 0) {//第一象限
            if (v <= 0.25 && a <= 0.25) {
                return 0;
            } else if (v <= 0.25 && a > 0.25) {
                return 1;
            } else if (v > 0.25 && a <= 0.25) {
                return 2;
            } else {
                return 3;
            }
        } else if (v >= 0 && a < 0) { //第二象限
            if (v <= 0.25 && a >= -0.25) {
                return 4;
            } else if (v <= 0.25 && a < -0.25) {
                return 5;
            } else if (v > 0.25 && a >= -0.25) {
                return 6;
            } else {
                return 7;
            }
        } else if (v < 0 && a < 0) {//第三象限
            if (v >= -0.25 && a >= -0.25) {
                return 12;
            } else if (v >= -0.25 && a < -0.25) {
                return 13;
            } else if (v < -0.25 && a >= -0.25) {
                return 14;
            } else {
                return 15;
            }
        } else {//第四象限
            if (v >= -0.25 && a <= 0.25) {
                return 8;
            } else if (v >= -0.25 && a > 0.25) {
                return 9;
            } else if (v < -0.25 && a <= 0.25) {
                return 10;
            } else {
                return 11;
            }
        }
    }

    /**
     * 将TIOPoint映射到4X4的网格中
     *    7  6     2  3
     *    5  4     0  1
     *    13 12    8  9
     *    15 14   10  11
     * @param tioPoint
     * @return hexadecimal index [0-15]
     */
    public byte mapZCAX(TIOPoint tioPoint) {
        TIOPoint p = normalize(tioPoint);
        //判断所属象限
        double v = p.getValue() - 0.5;
        double a = p.getAngle() - 0.5;
        if (v >= 0 && a >= 0) {//第一象限
            if (v <= 0.25 && a <= 0.25) {
                return 6;
            } else if (v <= 0.25 && a > 0.25) {
                return 7;
            } else if (v > 0.25 && a <= 0.25) {
                return 4;
            } else {
                return 3;
            }
        } else if (v >= 0 && a < 0) { //第二象限
            if (v <= 0.25 && a >= -0.25) {
                return 3;
            } else if (v <= 0.25 && a < -0.25) {
                return 2;
            } else if (v > 0.25 && a >= -0.25) {
                return 1;
            } else {
                return 0;
            }
        } else if (v < 0 && a < 0) {//第三象限
            if (v >= -0.25 && a >= -0.25) {
                return 9;
            } else if (v >= -0.25 && a < -0.25) {
                return 8;
            } else if (v < -0.25 && a >= -0.25) {
                return 11;
            } else {
                return 10;
            }
        } else {//第四象限
            if (v >= -0.25 && a <= 0.25) {
                return 12;
            } else if (v >= -0.25 && a > 0.25) {
                return 13;
            } else if (v < -0.25 && a <= 0.25) {
                return 14;
            } else {
                return 15;
            }
        }
    }

    /**
     * @param tioPoints
     * @return
     */
    public byte[] mapHAX(TIOPoints tioPoints) {
        int s = tioPoints.size();
        byte[] bytes = new byte[s];
        s = 0;
        for (TIOPoint p : tioPoints) {
            bytes[s] = mapHAX(p);
            s++;
        }
        return bytes;
    }

    /**
     * @param tioPoints
     * @return
     */
    public byte[] mapZCAX(TIOPoints tioPoints) {
        int s = tioPoints.size();
        byte[] bytes = new byte[s];
        s = 0;
        for (TIOPoint p : tioPoints) {
            bytes[s] = mapZCAX(p);
            s++;
        }
        return bytes;
    }

    /**
     * @param s
     * @param paaSize
     * @return
     */
    public byte[] mapHAX(Series s, int paaSize) {
        TIOPoints tioPoints = cn.edu.cug.cs.gtl.series.common.pax.Utils.pax(s, paaSize);
        return mapHAX(tioPoints);
    }

    /**
     * @param s
     * @param paaSize
     * @return
     */
    public byte[] mapZCAX(Series s, int paaSize) {
        TIOPoints tioPoints = cn.edu.cug.cs.gtl.series.common.pax.Utils.pax(s, paaSize);
        return mapZCAX(tioPoints);
    }

    /**
     * 对象深拷贝
     *
     * @return 返回新的对象
     */
    @Override
    public Object clone() {
        return new TIOPlane(minValue, maxValue, minAngle, maxAngle);
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
        minValue = in.readDouble();
        maxValue = in.readDouble();
        minAngle = in.readDouble();
        maxAngle = in.readDouble();
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
        out.writeDouble(minValue);
        out.writeDouble(maxValue);
        out.writeDouble(minAngle);
        out.writeDouble(maxAngle);
        return true;
    }


    /**
     * @param tioPoint
     * @return
     */
    public TIOPoint normalize(TIOPoint tioPoint) {
        double v = (tioPoint.getValue() - minValue) / (maxValue - minValue);
        double a = (tioPoint.getAngle() - minAngle) / (maxAngle - minAngle);
        return TIOPoint.of(a, v);
    }
}
