package cn.edu.cug.cs.gtl.series.ml;

import cn.edu.cug.cs.gtl.ml.dataset.Vector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 本类主要是针对icn.edu.cug.cs.gtl.series.io.Series类太过于低效，设计的一个专门用于数值计算的简化版本；
 * 可以通过cn.edu.cug.cs.gtl.series.io.Series.simplify()函数得到。
 */
public class Series  extends Vector implements cn.edu.cug.cs.gtl.series.common.Series {

    private static final long serialVersionUID = -3587253199097827732L;

    /**
     * 如果没有给出时间列，则该变量为空
     */
    double[]  timestamps=null;
    /**
     * 序列对象的标签值，如果有多个标签，通过逗号分隔，默认为空
     */
    String    defaultLabel="";

    /**
     * 设置序列的长度，默认时间列为空。
     * 之后可以通过set函数设置每一个序列值
     * for(int i=0;i<length;++i)
     *    set(i,value);
     * @param length 序列长度
     */
    public Series(int length) {
        super(length);
    }

    /**
     * 传入序列值列表，构建Series对象，时间列为空
     * @param list
     */
    public Series(List<Double> list) {
        super(list);
    }

    /**
     * 传入序列值数组，构建Series对象，时间列为空
     * @param array
     */
    public Series(double[] array) {
        super(array);
    }

    /**
     * 传入序列值数组，构建Series对象，时间列为空
     * @param array 序列的测量值数组
     * @param start 数组中的开始下标
     * @param end 数组中的结束下标
     */
    public Series(double[] array, int start, int end) {
        super(array, start, end);
    }

    /**
     * 传入时间值和序列值数组，构建Series对象
     * @param times 时间点数组
     * @param array 序列的测量值数组
     */
    public Series(double [] times, double[] array) {
        super(array);
        timestamps=times;
    }

    /**
     * 传入时间值和序列值数组，构建Series对象
     * @param times 时间点数组
     * @param array 序列的测量值数组
     * @param label 序列的默认标签值
     */
    public Series(double [] times, double[] array,String label) {
        super(array);
        timestamps=times;
        this.defaultLabel=label;
    }

    /**
     * 传入序列值向量，构建Series对象，时间列为空
     * @param values 序列的测量值向量
     */
    public Series(Vector values) {
        super(values);
    }


    /**
     * 获取序列的时间列，如果时间列为空，则返回[0,length)的步长为1的递增数组
     * @return
     */
    @Override
    public double[] getDataX() {
        if(timestamps==null){
            int s = super.length();
            double [] r = new double[s];
            for(int i=0;i<s;++i){
                r[i]=i;
            }
            return r;
        }
        else{
            return timestamps;
        }
    }

    /**
     * 获取序列的测量值数组
     * @return
     */
    @Override
    public double[] getDataY() {
        return super.array;
    }


    /**
     * 获取序列对象的标签值
     * @return
     */
    @Override
    public String getDefaultLabel() {
        return this.defaultLabel;
    }

    /**
     * @return the count of series in this object
     */
    @Override
    public long count() {
        return 1;
    }

    /**
     * calculate sub-series
     *
     * @param paaSize
     * @param paaIndex
     * @return
     */
    @Override
    public Series subseries(int paaSize, int paaIndex) {
        double [] dataY = getDataY();
        double[] tsY = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(dataY, paaSize, paaIndex);
        double [] fDataX = getDataX();
        double[] tsX = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(fDataX, paaSize, paaIndex);
        return new Series(tsX,tsY);
    }

    @Override
    public cn.edu.cug.cs.gtl.series.common.Series simplify() {
        return this;
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        super.load(dataInput);
        int f = dataInput.readInt();
        if(f==0) {
            timestamps=null;
        }
        else {
            timestamps=new double[f];
            for (int i=0;i<f;++i)
                timestamps[i]=dataInput.readDouble();
        }
        f = dataInput.readInt();
        if(f==0){
            defaultLabel="";
        }
        else {
            defaultLabel=dataInput.readUTF();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dataOutput) throws IOException {
        super.store(dataOutput);
        if(timestamps==null) {
            dataOutput.writeInt(0);
        }
        else {
            dataOutput.writeInt(timestamps.length);
            for (double d : timestamps)
                dataOutput.writeDouble(d);
        }
        if(defaultLabel==null){
            dataOutput.writeInt(0);
        }
        else {
            dataOutput.writeInt(defaultLabel.length());
            dataOutput.writeUTF(defaultLabel);
        }
        return true;
    }

    @Override
    public Series clone(){
        double [] times=null;
        if(timestamps!=null)
            times= Arrays.copyOf(timestamps,timestamps.length);
        double [] arrayOld = getDataY();
        double[] arrayNew= Arrays.copyOf(arrayOld,arrayOld.length);
        return new Series(times,arrayNew,this.defaultLabel);
    }

}
