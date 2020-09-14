package cn.edu.cug.cs.gtl.series.ml;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SeriesTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void of() throws  Exception{
        double [] doubles = new double[10];
        for(int i=0;i<10;i++)
            doubles[i]=i;
        Series s = new Series(doubles);
        Series s1 = (Series) s.clone();
        byte [] bs = s.storeToByteArray();
        byte [] bs1 = s1.storeToByteArray();
        Assert.assertArrayEquals(bs,bs1);
    }

    @Test
    public void testOf() {
    }

    @Test
    public void testOf1() {
    }

    @Test
    public void testOf2() {
    }

    @Test
    public void testOf3() {
    }

    @Test
    public void load() {
    }

    @Test
    public void store() {
    }

    @Test
    public void compareTo() {
    }
}