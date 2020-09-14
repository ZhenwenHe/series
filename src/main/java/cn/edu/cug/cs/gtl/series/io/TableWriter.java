package cn.edu.cug.cs.gtl.series.io;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableWriter implements Writer {

    Map<SeriesSchema, SeriesBuilder> seriesBuilders = new LinkedHashMap<>();

    /**
     * Write by Data Point
     * Point point = Point.measurement("temperature")
     * .addTag("location", "west")
     * .addField("value", 55D)
     * .time(Instant.now().toEpochMilli());
     * writeApi.writePoint(record);
     *
     * @param p
     * @return
     */
    @Override
    public boolean writePoint(Point p) {
        List<SeriesSchema> ssl = p.getSchemas();
        for(SeriesSchema ss:ssl){
            SeriesBuilder builder = seriesBuilders.get(ss);
            if(builder==null){
                builder = SeriesBuilder.newBuilder();
                seriesBuilders.put(ss,builder);
            }
            builder.setSchema(ss)
                    .addTimeValue(p.getTimestamp())
                    .addFieldValue(p.getFieldValue(ss.getFieldKey()));
        }
        return true;
    }

    /**
     * Write by LineProtocol
     *                  measurement,    tags         , fields
     * String record = "temperature,location=north device=HP, s1=60.0 s2=70";
     * writeApi.writeRecord(record);
     *
     * @param record
     * @return
     */
    @Override
    public boolean writeRecord(String record) {
        Point p= Point.parseFrom(record);
        return writePoint(p);
    }

    /**
     * Write by POJO
     * Temperature temperature = new Temperature();
     * temperature.location = "south";
     * temperature.value = 62D;
     * temperature.time = Instant.now();
     * writeApi.writeMeasurement(temperature);
     *
     * @param objectPOJO 该POJO对象必须实现toString（）函数，转换成LineProtocol字符串
     * @return
     */
    @Override
    public boolean writeMeasurement(Object objectPOJO) {
        return writeRecord(objectPOJO.toString());
    }

    /**
     * @param ss
     * @return
     */
    @Override
    public boolean writeSeries(List<Series> ss) {
        for (Series s: ss){
            SeriesSchema seriesSchema = s.getSchema();
            SeriesBuilder seriesBuilder = seriesBuilders.get(seriesSchema);
            if(seriesBuilder==null)
                seriesBuilders.put(seriesSchema,s.toBuilder());
            else {
                seriesBuilder.addFieldValues(s.getFieldValues());
                seriesBuilder.addTimeValues(s.getTimeValues());
            }
        }
        return true;
    }

    /**
     * @param multiSeries
     * @return
     */
    @Override
    public boolean writeSeries(MultiSeries multiSeries) {
        return false;
    }

}
