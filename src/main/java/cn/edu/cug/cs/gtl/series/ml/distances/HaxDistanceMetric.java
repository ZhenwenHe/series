package cn.edu.cug.cs.gtl.series.ml.distances;

import cn.edu.cug.cs.gtl.ml.dataset.NumericalData;
import cn.edu.cug.cs.gtl.ml.dataset.Vector;
import cn.edu.cug.cs.gtl.series.ml.Series;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;


public class HaxDistanceMetric<T extends NumericalData> implements DistanceMetric<T> {
    protected int wordSize;
    protected TIOPlane tioPlane;

    public HaxDistanceMetric(int wordSize, TIOPlane tioPlane) {
        this.wordSize = wordSize;
        this.tioPlane = tioPlane;
    }

    @Override
    public double distance(Object a, Object b) {
        if (a instanceof Series && b instanceof Series) {
            return DistanceUtils.hax((Series) a, (Series) b, this.wordSize, tioPlane);
        }
        else{
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
            return DistanceUtils.hax( sa, sb, this.wordSize, tioPlane);
        }

    }
    @Override
    public DistanceMetric<T> clone(){
        return null;
    }
}
