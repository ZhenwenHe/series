package cn.edu.cug.cs.gtl.series.simple;

import cn.edu.cug.cs.gtl.io.StorableComparable;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Series implements StorableComparable {

    private static final long serialVersionUID = -3587253199097827732L;

    private double [] data=null;

    protected Series(double [] v){
        data = v;
    }

    protected Series(double [] v, boolean copy){
        if(copy){
           data=Arrays.copyOf(v,v.length);
        }
        else
            data=v;
    }

    protected Series(float [] v){
        data = new double[v.length];
        for(int i=0;i<v.length;++i)
            data[i]=(double)(v[i]);
    }



    protected Series(int [] v){
        data = new double[v.length];
        for(int i=0;i<v.length;++i)
            data[i]=(double)(v[i]);
    }



    protected Series(long [] v){
        data = new double[v.length];
        for(int i=0;i<v.length;++i)
            data[i]=(double)(v[i]);
    }


    /**
     * 构造一个Series对象
     * @param v the values for series
     * @param copy copy the origin data
     * @return
     */
    public static  Series of(double [] v, boolean copy){
        return new Series(v,true);
    }


    /**
     * 构造一个Series对象
     * @param v
     * @return
     */
    public static  Series of(double [] v){
        return new Series(v,true);
    }

    /**
     * 构造一个Series对象
     * @param v
     * @return
     */
    public static  Series of(float [] v){
        return new Series(v);
    }

    /**
     * 构造一个Series对象
     * @param v
     * @return
     */
    public static  Series of(int [] v){
        return new Series(v);
    }



    /**
     * 构造一个Series对象
     * @param v
     * @return
     */
    public static  Series of(long [] v){
        return new Series(v);
    }




    /**
     * 克隆
     * @return
     */
    @Override
    public Object clone() {
        return of(this.data,true);
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        int len = dataInput.readInt();
        if(len>0){
            data = new double[len];
            for(int i=0;i<len;++i){
                data[i]= dataInput.readDouble();
            }
        }
        else
            data=null;
        return true;
    }

    @Override
    public boolean store(DataOutput dataOutput) throws IOException {
        if(data==null || data.length==0){
            dataOutput.writeInt(0);
        }
        else{
            dataOutput.writeInt(data.length);
            for(double d: data){
                dataOutput.writeDouble(d);
            }
        }
        return true;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@NotNull Object o) {
        //1 compare the length
        //2 compare the mean value

        return 0;
    }
}
