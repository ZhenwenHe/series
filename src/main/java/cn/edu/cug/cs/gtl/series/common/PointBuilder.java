package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.protos.TSPoint;
import cn.edu.cug.cs.gtl.protos.Timestamp;
import cn.edu.cug.cs.gtl.protos.Value;

public class PointBuilder {
    private transient TSPoint.Builder builder=TSPoint.newBuilder();

    public TSPoint.Builder setMeasurement(String measurement){
        builder.setMeasurement(measurement);
        return builder;
    }

    public TSPoint.Builder addField(String key, Value value){
        builder.putField(key,value);
        return builder;
    }

    public TSPoint.Builder addTag(String key, String value){
        builder.putTag(key,value);
        return builder;
    }

    public TSPoint.Builder setTimestamp(long t){
        builder.setTimestamp(Timestamp.newBuilder().setTime(t).build());
        return builder;
    }

    public Point build(){
        TSPoint tsPoint = this.builder.build();
        return new Point(tsPoint);
    }

    /**
     * LineProtocol
     *                  measurement,    tags         , fields
     * String lineRecord = "temperature,location=north device=HP, s1=60.0 s2=70";
     * @param lineRecord
     * @return
     */
    public static Point parseFrom(String lineRecord){
        return null;
    }
}
