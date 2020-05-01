package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Point implements Storable {

    private static final long serialVersionUID = 8942132755757422844L;

    private String measurement;
    private Map<String,String> tags= new TreeMap<String,String>();
    private Map<String,Object> fields= new TreeMap<String,Object>();

    /**
     * 对象深拷贝
     *
     * @return 返回新的对象
     */
    @Override
    public Object clone() {
        return null;
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
        return false;
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
        return false;
    }
}
