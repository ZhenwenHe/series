package cn.edu.cug.cs.gtl.series.io;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class MultiSeriesTest {
    String testFileName;

    @Before
    public void setUp() throws Exception {
        Config.setConfigFile("series.properties");
        testFileName = (Config.getTestOutputDirectory()
                + File.separator + "test.series");
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void length() {
    }

    @Test
    public void getSeries() {
    }

    @Test
    public void count() {
    }

    @Test
    public void getDefaultLabel() {
    }

    @Test
    public void getDefaultLabels() {
    }

    @Test
    public void testClone() {
    }

    @Test
    public void load() {
    }

    @Test
    public void store() {
    }

    @Test
    public void toTrainSet() {
    }

    @Test
    public void toTestSet() {
    }

    @Test
    public void max() {
    }

    @Test
    public void min() {
    }

    @Test
    public void toList() {
    }

    @Test
    public void toArray() {
    }

    @Test
    public void  of() {
        double[] xs = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        double[][] ys = new double[][]{
                {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
                {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0},
                {3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0},
                {4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0},
                {5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0}
        };

        MultiSeries ms = SeriesBuilder.build("test","value",xs,ys, new Pair("label","1"));
        try {
            byte[] bytes = ms.storeToByteArray();
            MultiSeries ms2 = SeriesBuilder.parseMultiSeriesFrom(bytes);
            Series s2 = ms2.getSeries(0);
            Assert.assertArrayEquals(s2.getValues(), ys[0], 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  of1() {
        double[] xs = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        double[][] ys = new double[][]{
                {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
                {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0},
                {3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0},
                {4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0},
                {5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0}
        };
        MultiSeries ms = SeriesBuilder.build("test","value",xs,ys, new Pair("label","1"));
        try {
            FileOutputStream f = new FileOutputStream(testFileName);
            ms.write(f);
            f.close();
            FileInputStream f2 = new FileInputStream(testFileName);
            MultiSeries ms2 = SeriesBuilder.parseMultiSeriesFrom(f2);
            Series s2 = ms2.getSeries(0);
            Assert.assertArrayEquals(s2.getValues(), ys[0], 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}