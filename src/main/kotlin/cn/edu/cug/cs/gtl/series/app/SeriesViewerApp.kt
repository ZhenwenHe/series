package cn.edu.cug.cs.gtl.series.app

import cn.edu.cug.cs.gtl.series.common.SeriesBuilder
import cn.edu.cug.cs.gtl.series.visualization.SeriesViewer
import java.util.*

/**
 * 绘制3条时间序列曲线的例子
 */
fun main(){
    // Generate random data-points
    val rand = Random()
    val da = DoubleArray(100){ i->rand.nextGaussian()}
    val la = LongArray(100){ i->i.toLong()}

    val s1= SeriesBuilder.newBuilder()
            .setMeasurement("cpu usage")
            .setFieldKey("usage")
            .addTag("type","1")
            .addTag("host","HP")
            .addFieldValues(da)
            .addTimeValues(la)
            .build()

    val da2 = DoubleArray(100){ i->rand.nextGaussian()}
    val la2 = LongArray(100){ i->i.toLong()}

    val s2= SeriesBuilder.newBuilder()
            .setMeasurement("cpu usage")
            .setFieldKey("usage")
            .addTag("type","2")
            .addTag("host","IBM")
            .addFieldValues(da2)
            .addTimeValues(la2)
            .build()

    val da3 = DoubleArray(100){ i->rand.nextGaussian()}
    val la3 = LongArray(100){ i->i.toLong()}

    val s3= SeriesBuilder.newBuilder()
            .setMeasurement("cpu usage")
            .setFieldKey("usage")
            .addTag("type","3")
            .addTag("host","SONY")
            .addFieldValues(da3)
            .addTimeValues(la3)
            .build()

    val frameTitle:String="show 3 time series"
    val figureTitle:String="series"

    val sv = SeriesViewer(frameTitle,figureTitle,s1,s2,s3)
    sv.plot();
}