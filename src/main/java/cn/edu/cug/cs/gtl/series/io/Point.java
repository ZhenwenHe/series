package cn.edu.cug.cs.gtl.series.io;

import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.protos.TSPoint;
import cn.edu.cug.cs.gtl.protos.TSSeriesSchema;
import cn.edu.cug.cs.gtl.protos.Timestamp;
import cn.edu.cug.cs.gtl.protos.Value;
import cn.edu.cug.cs.gtl.protoswrapper.TSPointWrapper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Point implements Storable {

    private static final long serialVersionUID = 8942132755757422844L;

    private TSPoint tsPoint =null;

    public static PointBuilder newBuilder(){
        return new PointBuilder();
    }

    private Point(){

    }

    protected Point(TSPoint tsPoint){
        this.tsPoint=tsPoint;
    }

    public String getMeasurement( ){
        return tsPoint.getMeasurement();
    }

    public Value  getFieldValue(String key){
        return tsPoint.getFieldMap().get(key);
    }

    public String getTagValue(String key){
        return tsPoint.getTagMap().get(key);
    }

    public Timestamp getTimestamp(){
        return tsPoint.getTimestamp();
    }

    public Map<String, String> getTagMap(){
        return tsPoint.getTagMap();
    }

    public Map<String, Value> getFieldMap(){
        return tsPoint.getFieldMap();
    }
    /**
     * 对象深拷贝
     *
     * @return 返回新的对象
     */
    @Override
    public Object clone() {
        Point p = new Point();
        p.tsPoint = tsPoint.toBuilder().build();
        return p;
    }

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        int len = in.readInt();
        byte[] bs = new byte[len];
        in.readFully(bs);
        tsPoint =TSPoint.parseFrom(bs);
        return true;
    }

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param out ，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        byte[] bs = tsPoint.toByteArray();
        out.writeInt(bs.length);
        out.write(bs);
        return true;
    }

    /**
     *
     * @return
     */
    public  List<SeriesSchema> getSchemas(){
        List<TSSeriesSchema> al = TSPointWrapper.getSchemas(this.tsPoint);
        ArrayList<SeriesSchema> sl = new ArrayList<>();
        for(TSSeriesSchema t: al){
            sl.add(new SeriesSchema(t));
        }
        return sl;
    }

    /**
     * LineProtocol
     *                  measurement,    tags         , fields
     * String lineRecord = "temperature,location=north device=HP, s1=60.0 s2=70";
     * @param lineRecord
     * @return
     */
    public static Point parseFrom(String lineRecord){
        return PointBuilder.parseFrom(lineRecord);
    }

}
