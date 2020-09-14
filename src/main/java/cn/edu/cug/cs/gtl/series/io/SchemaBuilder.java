package cn.edu.cug.cs.gtl.series.io;

import cn.edu.cug.cs.gtl.protos.CompressionType;
import cn.edu.cug.cs.gtl.protos.EncodingType;
import cn.edu.cug.cs.gtl.protos.Type;
import cn.edu.cug.cs.gtl.protoswrapper.TSSeriesSchemaWrapper;

import java.util.Map;

public class SchemaBuilder {


    private transient TSSeriesSchemaWrapper.TSSeriesSchemaBuilder builder= null;

    SchemaBuilder() {
        builder= TSSeriesSchemaWrapper.newBuilder();
    }

    public SchemaBuilder measurement(String m){
        return setMeasurement(m);
    }

    public SchemaBuilder setMeasurement(String m){
        builder.setMeasurement(m);
        return this;
    }

    public SchemaBuilder setFieldKey(String value){
        builder.setFieldKey(value);
        return this;
    }

    public SchemaBuilder addTag(String name, String value){
        builder.addTag(name,value);
        return this;
    }

    public SchemaBuilder addTags(Map<String,String> tagMap){
        builder.addTags(tagMap);
        return this;
    }

    public SchemaBuilder setTimeUnit(String timeUnit){
        builder.setTimeUnit(timeUnit);
        return this;
    }

    public SchemaBuilder setValueType(Type type){
        builder.setValueType(type);
        return this;
    }

    public SchemaBuilder setEncodingType(EncodingType type){
        builder.setEncodingType(type);
        return this;
    }

    public SchemaBuilder setCompressionType(CompressionType type){
        builder.setCompressionType(type);
        return this;
    }

    public SchemaBuilder mergeFrom(SeriesSchema seriesSchema){
        builder.mergeFrom(seriesSchema.seriesSchema);
        return this;
    }

    public SeriesSchema build( ){
        return new SeriesSchema(builder.build());
    }

}
