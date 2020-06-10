package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.protos.TSPoint;
import cn.edu.cug.cs.gtl.protos.Timestamp;
import cn.edu.cug.cs.gtl.protos.Value;

public class PointBuilder {
    private transient TSPoint.Builder builder=TSPoint.newBuilder();

    TSPoint.Builder setMeasurement(String measurement){
        builder.setMeasurement(measurement);
        return builder;
    }

    TSPoint.Builder addField(String key, Value value){
        builder.putField(key,value);
        return builder;
    }

    TSPoint.Builder addTag(String key, String value){
        builder.putTag(key,value);
        return builder;
    }

    TSPoint.Builder setTimestamp(long t){
        builder.setTimestamp(Timestamp.newBuilder().setTime(t).build());
        return builder;
    }

    Point build(){
        TSPoint tsPoint = this.builder.build();
        return new Point(tsPoint);
    }
}
