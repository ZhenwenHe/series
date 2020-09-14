package cn.edu.cug.cs.gtl.series.io;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.common.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class SeriesTest {
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
    public void getMeasurement() {
    }

    @Test
    public void getFieldKey() {
    }

    @Test
    public void getSchema() {
    }

    @Test
    public void getFieldValues() {
    }

    @Test
    public void getTagMap() {
    }

    @Test
    public void getTimeValues() {
    }

    @Test
    public void getDataX() {
    }

    @Test
    public void getDataY() {
    }

    @Test
    public void getDefaultLabel() {
    }

    @Test
    public void testClone() {
    }

    @Test
    public void load() {
        double[] ys =new double[]{50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0};
        Series s1 = SeriesBuilder.build("test","value",ys, new Pair("label","1"));
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            s1.write(baos);
            byte[] bytes = baos.toByteArray();
            baos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Series s3 = SeriesBuilder.parseFrom(bais);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void store() {
        double[] ys = new double[]{50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0};
        Series s1 = SeriesBuilder.build("test","value",ys, new Pair("label","1"));
        try {
            byte[] bytes = s1.storeToByteArray();
            Series s3 = SeriesBuilder.parseFrom(bytes);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void count() {
    }

    @Test
    public void length() {
    }

    @Test
    public void subseries() {
    }

    @Test
    public void max() {
    }

    @Test
    public void min() {
    }

    @Test
    public void toBuilder() {
    }

    @Test
    public void simplify() {
    }
    @Test
    public void copy(){
        double[] ys = new double[]{50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0};
        Series s1 = SeriesBuilder.build("test","value",ys, new Pair("label","1"));
        try {
            FileOutputStream f = new FileOutputStream(testFileName);
            s1.write(f);
            f.close();
            FileInputStream f2 =new FileInputStream(testFileName);
            Series s3 = SeriesBuilder.parseFrom(f2);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyTimeSeries() {
        double[] xs = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        double [] ys = new double[]{50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0};
        Series s1 = SeriesBuilder.build("test","value",xs,ys, new Pair("label","1"));
        Series s2 = SeriesBuilder.build("test","value",ys, new Pair("label","1"));
        try {
            FileOutputStream f = new FileOutputStream(testFileName);
            s1.write(f);
            f.close();
            FileInputStream f2 = new FileInputStream(testFileName);
            Series s3 = SeriesBuilder.parseFrom(f2);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
            Assert.assertArrayEquals(ys, s2.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  loadTimeSeries() {
        double[] xs = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        double [] ys = new double[]{50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0};
        Series s1 = SeriesBuilder.build("test","value",xs,ys, new Pair("label","1"));
        Series s2 = SeriesBuilder.build("test","value",ys, new Pair("label","1"));
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            s1.write(baos);
            byte[] bytes = baos.toByteArray();
            baos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Series s3 = SeriesBuilder.parseFrom(bais);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
            Assert.assertArrayEquals(ys, s2.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void storeTimeSeries() {
        double[] xs = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        double [] ys = new double[]{50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0};
        Series s1 = SeriesBuilder.build("test","value",xs,ys, new Pair("label","1"));
        Series s2 = SeriesBuilder.build("test","value",ys, new Pair("label","1"));
        try {
            byte[] bytes = s1.storeToByteArray();
            Series s3 = SeriesBuilder.parseFrom(bytes);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
            Assert.assertArrayEquals(ys, s2.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}