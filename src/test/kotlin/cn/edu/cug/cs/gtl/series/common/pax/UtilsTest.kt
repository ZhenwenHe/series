package cn.edu.cug.cs.gtl.series.common.pax

import org.apache.commons.math3.fitting.PolynomialCurveFitter
import org.apache.commons.math3.fitting.WeightedObservedPoint
import org.junit.Assert
import org.junit.Test
import java.util.*


class UtilsTest {
    @Test
    fun pax() {
    }

    @Test
    fun poly() {
        val p =
            PolynomialCurveFitter.create(1)
        val points: MutableList<WeightedObservedPoint> =
            ArrayList()
        points.add(WeightedObservedPoint(1.0, 0.0, 1.0))
        points.add(WeightedObservedPoint(1.0, 2.0, 2.0))
        points.add(WeightedObservedPoint(1.0, 4.0, 3.0))
        points.add(WeightedObservedPoint(1.0, 6.0, 4.0))
        val f = p.fit(points)
        Assert.assertEquals(f[0], 1.0, 0.000001)
        Assert.assertEquals(f[1], 0.5, 0.000001)
        Assert.assertEquals(Math.toDegrees(Math.atan(1.0)), 45.0, 0.000001)
    }

    @Test
    fun subseriesToTIOPoint() {
    }
}