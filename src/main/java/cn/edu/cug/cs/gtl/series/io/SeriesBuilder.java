package cn.edu.cug.cs.gtl.series.io;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.protos.*;
import cn.edu.cug.cs.gtl.protoswrapper.TSSeriesWrapper;
import cn.edu.cug.cs.gtl.protoswrapper.ValueWrapper;
import cn.edu.cug.cs.gtl.series.common.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeriesBuilder {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private transient TSSeriesWrapper.TSSeriesBuilder builder=TSSeriesWrapper.newBuilder();


    public static SeriesBuilder newBuilder(){
        return new SeriesBuilder();
    }

    public static SeriesBuilder toBuilder(Series s){
        return new SeriesBuilder(TSSeriesWrapper.toBuilder(s.tsSeries));
    }

    SeriesBuilder(TSSeriesWrapper.TSSeriesBuilder b){
        builder=b;
    }

    SeriesBuilder(){

    }

    public SeriesBuilder setSchema(SeriesSchema m){
        builder.setSchema(m.seriesSchema);
        return this;
    }
    public SeriesBuilder setMeasurement(String measurement){
        builder.setMeasurement(measurement);
        return this;
    }

    public SeriesBuilder setFieldKey(String fieldKey){
        builder.setFieldKey(fieldKey);
        return this;
    }

    public SeriesBuilder addValue(Timestamp timestamp, Value fieldValue){
        builder.addFieldValue(fieldValue);
        builder.addTimeValue(timestamp);
        return this;
    }

    public SeriesBuilder addValue(Timestamp timestamp, double fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(timestamp);
        return this;
    }

    public SeriesBuilder addValue(double timestamp, double fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }

    public SeriesBuilder addValue(long timestamp, double fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }

    public SeriesBuilder addValue(Timestamp timestamp, float fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(timestamp);
        return this;
    }

    public SeriesBuilder addValue(float timestamp, float fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }


    public SeriesBuilder addValue(Timestamp timestamp, int fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(timestamp);
        return this;
    }

    public SeriesBuilder addValue(int timestamp, int fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }


    public SeriesBuilder addValue(Timestamp timestamp, long fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(timestamp);
        return this;
    }

    public SeriesBuilder addValue(long timestamp, long fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }

    public SeriesBuilder addValues(long[] timestamps, double[] fieldValue){
        return addFieldValues(fieldValue).addTimeValues(timestamps);
    }


    public SeriesBuilder addValues(long[] timestamps, float[] fieldValue){
        return addFieldValues(fieldValue).addTimeValues(timestamps);
    }


    public SeriesBuilder addValues(double[] timestamps, double[] fieldValue){
        return addFieldValues(fieldValue).addTimeValues(timestamps);
    }

    public SeriesBuilder addValues(float[] timestamps, float[] fieldValue){
        return addFieldValues(fieldValue).addTimeValues(timestamps);
    }


    public SeriesBuilder addFieldValue(Value fieldValue){
        builder.addFieldValue(fieldValue);
        return this;
    }

    public SeriesBuilder addFieldValue(double fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        return this;
    }

    public SeriesBuilder addFieldValue(float fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        return this;
    }

    public SeriesBuilder addFieldValue(int fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        return this;
    }

    public SeriesBuilder addFieldValue(long fieldValue){
        builder.addFieldValue(ValueWrapper.of(fieldValue));
        return this;
    }

    public SeriesBuilder addFieldValues(double []fieldValues){
        for(double d: fieldValues)
            builder.addFieldValue(ValueWrapper.of(d));
        return this;
    }

    public SeriesBuilder addFieldValues(float []fieldValues){
        for(float d: fieldValues)
            builder.addFieldValue(ValueWrapper.of(d));
        return this;
    }

    public SeriesBuilder addFieldValues(int []fieldValues){
        for(int d: fieldValues)
            builder.addFieldValue(ValueWrapper.of(d));
        return this;
    }

    public SeriesBuilder addFieldValues(long []fieldValues){
        for(long d: fieldValues)
            builder.addFieldValue(ValueWrapper.of(d));
        return this;
    }

    public SeriesBuilder addFieldValues(List<Value> values){
        builder.addFieldValues(values);
        return this;
    }

    public SeriesBuilder addTimeValue(Timestamp timestamp){
        builder.addTimeValue(timestamp);
        return this;
    }

    public SeriesBuilder addTimeValue(float timestamp){
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }

    public SeriesBuilder addTimeValue(double timestamp){
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }

    public SeriesBuilder addTimeValue(int timestamp){
        builder.addTimeValue(Timestamp.newBuilder().setTime((long)timestamp).build());
        return this;
    }

    public SeriesBuilder addTimeValue(long timestamp){
        builder.addTimeValue(Timestamp.newBuilder().setTime(timestamp).build());
        return this;
    }

    public SeriesBuilder addTimeValues(long [] timestamps){
        for(long t: timestamps)
            builder.addTimeValue(Timestamp.newBuilder().setTime(t).build());
        return this;
    }

    public SeriesBuilder addTimeValues(double [] timestamps){
        for(double t: timestamps)
            builder.addTimeValue(Timestamp.newBuilder().setTime((long)t).build());
        return this;
    }


    public SeriesBuilder addTimeValues(float [] timestamps){
        for(double t: timestamps)
            builder.addTimeValue(Timestamp.newBuilder().setTime((long)t).build());
        return this;
    }

    public SeriesBuilder addTimeValues(List<Timestamp> timestamps){
        builder.addTimeValues(timestamps);
        return this;
    }

    public SeriesBuilder addTag(String tagKey, String tagValue){
        builder.addTag(tagKey,tagValue);
        return this;
    }

    public SeriesBuilder addTags(Map<String,String> tags){
        builder.addTags(tags);
        return this;
    }

    public Series build(){
        return new Series(builder.build());
    }



    public static double [] doubleArray(List<Value> ls){
        double [] data = new double[ls.size()];
        int i=0;
        for(Value v:ls){
            data[i]=ValueWrapper.getReal(v);
            i++;
        }
        return data;
    }


    /**
     * 从TSV文件中读取数据构建时序数据集合
     *
     * @param name 时序数据文件名
     * @return
     * @throws IOException
     */
    public static MultiSeries readTSV(String name) throws IOException{
        MultiSeries ms = new MultiSeries();
        ms.tsMultiSeries= TSMultiSeries.newBuilder()
                .addAllSeries(readFromTSV(name))
                .build();
        return ms;
    }
    /**
     * 从TSV文件中读取数据构建时序数据集合
     *
     * @param name 时序数据文件名
     * @return
     * @throws IOException
     */
    private static List<TSSeries> readFromTSV(String name) throws IOException {
        List<Pair<String,double[]>> r = readTSVFile(name);
        ArrayList<TSSeries> sa = new ArrayList<>();
        int i=0;
        for(Pair<String,double[]> p: r){
            TSSeries s= TSSeriesWrapper.newBuilder()
                    .setMeasurement(name)
                    .addTag("filename",name)
                    .addTag("label",p.first())
                    .addAllFieldValue(ValueWrapper.iterableOf(p.second()))
                    .build();
            sa.add(s);
            i++;
        }
        return sa;
    }

    /**
     * 读取TSV文件中的数据，第一列为标签，其余的为序列值，返回列表中原始的个数等于文件中的行数
     * @param name
     * @return
     * @throws IOException
     */
    public static List<Pair<String,double[]>> readTSVFile(String name) throws IOException{
        return UCRArchiveReader.readTSVFile(name);
    }
    /**
     * 从NSV文件中读取数据构建时序数据集合，所谓的NSV文件是以换行符为分隔的文件
     * ，也就是只有一列的CSV文件。
     * NSV is that file has a single double value on every line.
     *
     * @param fileName  时序数据文件名
     * @param columnIdx 从columnIdx开始读，下标从0开始
     * @param sizeLimit 总共读取sizeLimit行，0=all
     * @return 数据序列
     * @throws IOException
     */
    public static Series readNSVFile(String fileName, int columnIdx, int sizeLimit)
            throws IOException, SAXException {
        Path path = Paths.get(fileName);
        if (!(Files.exists(path))) {
            throw new SAXException("unable to load data - data source not found.");
        }
        BufferedReader reader = Files.newBufferedReader(path, DEFAULT_CHARSET);
        double[] ds = readNSVFile(reader, columnIdx, sizeLimit);
        return new SeriesBuilder()
                .setMeasurement(fileName)
                .addTag("filename",fileName)
                .addFieldValues(ds)
                .build();
    }

    /**
     * 从NSV文件中读取数据构建时序数据集合，所谓的NSV文件是以换行符为分隔的文件
     * ，也就是只有一列的CSV文件。
     * NSV is that file has a single double value on every line.
     *
     * @param fileName 时序数据文件名
     * @return 数据序列
     * @throws IOException
     */
    public static Series readNSVFile(String fileName)
            throws IOException, SAXException {
        return readNSVFile(fileName, 0, 0);
    }

    /**
     * 从NSV文件中读取数据构建时序数据集合，所谓的NSV文件是以换行符为分隔的文件
     * ，也就是只有一列的CSV文件。
     * NSV is that file has a single double value on every line.
     *
     * @param br        时序数据文件名
     * @param columnIdx 从columnIdx开始读，下标从0开始
     * @param sizeLimit 总共读取sizeLimit行
     * @return 数据序列的数组
     * @throws IOException
     */
    public static double[] readNSVFile(BufferedReader br, int columnIdx, int sizeLimit)
            throws IOException, SAXException, NumberFormatException {
        ArrayList<Double> preRes = new ArrayList<Double>();
        int lineCounter = 0;

        String line = null;
        while ((line = br.readLine()) != null) {
            String[] split = line.trim().split("\\s+");
            if (split.length < columnIdx) {
                String message = "Unable to read data from column " + columnIdx;
                br.close();
                throw new SAXException(message);
            }
            String str = split[columnIdx];
            double num = Double.NaN;
            try {
                num = Double.valueOf(str);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("error in  the row " + lineCounter + " with value \"" + str + "\"");
            }

            preRes.add(num);
            lineCounter++;
            if ((0 != sizeLimit) && (lineCounter >= sizeLimit)) {
                break;
            }
        }
        br.close();
        double[] res = new double[preRes.size()];
        for (int i = 0; i < preRes.size(); i++) {
            res[i] = preRes.get(i);
        }
        return res;
    }

    /**
     * 从字节数组中读取信息，构造Series对象；
     * 该数组应该是调用Series的storeToByteArray()得到的字节数组,例如：
     * double []xs = {0,1,2,3,4,5,6,7,8,9};
     * double []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = TimeSeries.of(xs,ys);
     * TimeSeries s2 = TimeSeries.of(ys);
     * try{
     * byte[] bytes = s1.storeToByteArray();
     * TimeSeries s3 = TimeSeries.of(bytes);
     * Assert.assertArrayEquals(ys,s3.getValues(),0.001);
     * Assert.assertArrayEquals(ys,s2.getValues(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static Series parseFrom(byte[] bytes) throws IOException {
        Series s = new Series();
        s.loadFromByteArray(bytes);
        return s;
    }

    /**
     * 从数据流中读取信息构建一个新的Series对象，例如：
     * double []xs = {0,1,2,3,4,5,6,7,8,9};
     * double []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = TimeSeries.of(xs,ys);
     * TimeSeries s2 = TimeSeries.of(ys);
     * try{
     * ByteArrayOutputStream baos=new ByteArrayOutputStream();
     * s1.write(baos);
     * byte[] bytes = baos.toByteArray();
     * baos.close();
     * ByteArrayInputStream bais=new ByteArrayInputStream(bytes);
     * TimeSeries s3 = TimeSeries.of(bais);
     * Assert.assertArrayEquals(ys,s3.getValues(),0.001);
     * Assert.assertArrayEquals(ys,s2.getValues(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     * <p>
     * 或者从文件中读取信息构建Series
     * <p>
     * try{
     * FileOutputStream f = new FileOutputStream("test.series");
     * s1.write(f);
     * f.close();
     * FileInputStream f2= new FileInputStream("test.series");
     * TimeSeries s3 = TimeSeries.of(f2);
     * Assert.assertArrayEquals(ys,s3.getValues(),0.001);
     * Assert.assertArrayEquals(ys,s2.getValues(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param inputStream
     * @return
     */
    public static Series parseFrom(InputStream inputStream) throws IOException {
        Series s = new Series();
        s.read(inputStream);
        return s;
    }

    /**
     * double []xs = {1,2,3,4,5,6,7,8,9};
     * double[][] ys={{1,1,1,1,1,1,1,1,1},
     * {2,2,2,2,2,2,2,2,2},
     * {3,3,3,3,3,3,3,3,3},
     * {4,4,4,4,4,4,4,4,4},
     * {5,5,5,5,5,5,5,5,5}};
     * MultiSeries ms = MultiSeries.of(xs,ys);
     * try {
     * byte [] bytes = ms.storeToByteArray();
     * MultiSeries ms2 = MultiSeries.of(bytes);
     * TimeSeries s2 = ms2.getSeries(0);
     * Assert.assertArrayEquals(s2.getValues(),ys[0],0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static MultiSeries parseMultiSeriesFrom(byte[] bytes) throws IOException {
        MultiSeries ms = new MultiSeries();
        ms.loadFromByteArray(bytes);
        return ms;
    }

    /**
     * double []xs = {1,2,3,4,5,6,7,8,9};
     * double[][] ys={{1,1,1,1,1,1,1,1,1},
     * {2,2,2,2,2,2,2,2,2},
     * {3,3,3,3,3,3,3,3,3},
     * {4,4,4,4,4,4,4,4,4},
     * {5,5,5,5,5,5,5,5,5}};
     * MultiSeries ms = MultiSeries.of(xs,ys);
     * try {
     * FileOutputStream f = new FileOutputStream("test.series");
     * ms.write(f);
     * f.close();
     * FileInputStream f2= new FileInputStream("test.series");
     * MultiSeries ms2 = MultiSeries.of(f2);
     * TimeSeries s2 = ms2.getSeries(0);
     * Assert.assertArrayEquals(s2.getValues(),ys[0],0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static MultiSeries parseMultiSeriesFrom(InputStream inputStream) throws IOException {
        MultiSeries ms = new MultiSeries();
        ms.read(inputStream);
        return ms;
    }


    public static Series build(String measurement, String fieldKey, double[] fieldValues, Pair<String,String> ... tags){
        SeriesBuilder builder=  new SeriesBuilder();

        builder.setMeasurement(measurement)
                .setFieldKey(fieldKey)
                .addFieldValues(fieldValues);
        for(Pair<String,String> p : tags){
            builder.addTag(p.first(),p.second());
        }
        return builder.build();
    }

    private static SeriesBuilder builder(String measurement, String fieldKey, double[] timeValues, double[] fieldValues, Pair<String,String> ... tags){
        SeriesBuilder builder=  new SeriesBuilder();

        builder.setMeasurement(measurement)
                .setFieldKey(fieldKey)
                .addFieldValues(fieldValues)
                .addTimeValues(timeValues);

        for(Pair<String,String> p : tags){
            builder.addTag(p.first(),p.second());
        }
        return builder;
    }

    public static Series build(String measurement, String fieldKey, double[] timeValues, double[] fieldValues, Pair<String,String> ... tags){
        return builder(measurement,fieldKey,timeValues,fieldValues,tags).build();
    }

    public static MultiSeries build(String measurement,
                                    String fieldKey,
                                    double[] timeValues,
                                    double[][] fieldValues,
                                    Pair<String,String> ... tags){
        int i=0;
        TSMultiSeries.Builder b=TSMultiSeries.newBuilder();
        for(double[] dd : fieldValues){
            SeriesBuilder s = builder(measurement,fieldKey,timeValues,dd,tags);
            s.addTag("id",String.valueOf(i));
            ++i;
            Series series=s.build();
            b.addSeries(series.tsSeries);
        }
        MultiSeries r = new MultiSeries();
        r.tsMultiSeries=b.build();
        return r;
    }
}
