package cn.edu.cug.cs.gtl.series.ml.distances;

import cn.edu.cug.cs.gtl.ml.dataset.NumericalData;
import cn.edu.cug.cs.gtl.ml.dataset.Vector;
import cn.edu.cug.cs.gtl.series.ml.Series;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetric;

public class ESaxDistanceMetric<T extends NumericalData> implements DistanceMetric<T> {
    protected int alphabet;
    protected long wordSize;

    public ESaxDistanceMetric(long w, int alphabet) {
        this.alphabet = alphabet;
        this.wordSize = w;
    }

    @Override
    public double distance(Object a, Object b) {
        if (a instanceof Series && b instanceof Series) {
            return DistanceUtils.esax((Series) a, (Series) b, (int)this.wordSize,alphabet);
        }
        else{
            Series sa,sb;
            if(a instanceof Vector) {
                sa= new Series((Vector)a);
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
            return DistanceUtils.esax( sa, sb, (int) this.wordSize, this.alphabet);
        }

    }

    @Override
    public DistanceMetric<T> clone(){
        return null;
    }
}
