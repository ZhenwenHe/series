package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.io.Storable;
import java.util.List;

/**
 * 公用一个X轴的多个Series的集合，其中的所有序列对象必须具有相同的长度。
 */
public interface MultiSeries extends Storable {


    /**
     * 序列的长度
     * @return
     */

    int length() ;

    /**
     * 获取第i个序列
     * @param i
     * @return
     */
    Series getSeries(int i) ;

    /**
     * @return the count of series in this object
     */
    long count();

    /**
     * get the i-th series' default label
     * @param i
     * @return
     */
    String getDefaultLabel(int i) ;

    /**
     * get all default labels
     * @return
     */
    List<String> getDefaultLabels() ;



    /**
     * 深度拷贝
     * @return
     */
    Object clone();



    /**
     * 获取所有的Series对象的Y值最大值
     * @return
     */
    double max();

    /**
     * 获取所有的Series对象的Y值最小值
     * @return
     */
    double min() ;

    /**
     * to time series list
     *
     * @return
     */
    List<Series> toList() ;

    /**
     * to time series array
     *
     * @return
     */
    Series[] toArray() ;
}
