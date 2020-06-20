package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.protos.TSSeriesSchema;
import cn.edu.cug.cs.gtl.protoswrapper.TSSeriesSchemaWrapper;

public class SchemaBuilder {
    private transient TSSeriesSchemaWrapper.TSSeriesSchemaBuilder builder= null;

    public static Schema build(TSSeriesSchema m){
        return new Schema(m);
    }
}
