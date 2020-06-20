package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.protos.TSSeriesSchema;

import java.util.Map;

public class Schema {
    private TSSeriesSchema seriesSchema=null;

    Schema(TSSeriesSchema m){
        this.seriesSchema=m;
    }

    public String getMeasurement(){
        return seriesSchema.getMeasurement();
    }

    public String getFieldKey(){
        return seriesSchema.getFieldKey();
    }

    public Map<String,String> getTagMap(){
        return seriesSchema.getTagMap();
    }

}
