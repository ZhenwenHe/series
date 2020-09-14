package cn.edu.cug.cs.gtl.series.ml.distances;

import cn.edu.cug.cs.gtl.ml.dataset.NumericalData;
import cn.edu.cug.cs.gtl.ml.dataset.Vector;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.ml.Series;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;

public class PaxDistanceMetric<T extends NumericalData> implements DistanceMetric<T> {
    protected int wordSize;
    protected TIOPlane tioPlane = null;

    public PaxDistanceMetric(int wordSize, TIOPlane tioPlane) {
        this.wordSize = wordSize;
        this.tioPlane = tioPlane;
    }

    public PaxDistanceMetric(int wordSize) {
        this.wordSize = wordSize;
        this.tioPlane = null;
    }

    @Override
    public double distance(Object a, Object b) {
        Series sa,sb;
        if(a instanceof Vector) {
            sa=new Series((Vector)a);
        }
        else if(a instanceof  Series){
            sa = (Series)a;
        }
        else{
            System.out.println("HaxDistanceMetric Error: the input object, a is not Series");
            return Double.MAX_VALUE;
        }

        if(b instanceof Vector) {
            sb=new Series((Vector)b);
        }
        else if(b instanceof  Series){
            sb = (Series)b;
        }
        else{
            System.out.println("HaxDistanceMetric Error: the input object, b is not Series");
            return Double.MAX_VALUE;
        }

        if (tioPlane == null)
            return DistanceUtils.pax((Series) sa, (Series) sb, this.wordSize);
        else
            return DistanceUtils.pax((Series) sa, (Series) sb, this.wordSize, tioPlane);

    }

    @Override
    public DistanceMetric<T> clone(){
        return null;
    }
}
