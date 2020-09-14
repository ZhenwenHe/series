package cn.edu.cug.cs.gtl.series.app.ui;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.config.Config;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperimentalConfig {
    private String dataDirectory=null;//输入数据文件的根目录
    private List<Pair<String, String>> dataFiles=null;//所有有效的数据集文件对，每对包含一个训练文件和一个测试文件
    private Pair<Integer, Integer> paaSizeRange=null;
    private Pair<Integer, Integer> alphabetRange=null;
    private String resultFileName=null;//输出结果文件名
    private String runtimeDatabase=null;//运行数据库文件名
    private List<String> datasetNames=null;//有效的数据集名称
    private String configFile =null;//app 配置文件
    private String method=null;//方法名
    private String representation=null;//表达方式

    /**
     *
     * @param configFile 配置文件
     * @param runtimeDatabase 示例运行库文件, Sqlite Database,
     *                        其中的Parameters数据表中存储了实验参数
     */
    public ExperimentalConfig(String configFile, String runtimeDatabase) {
        this.configFile=configFile;
        this.runtimeDatabase=runtimeDatabase;
        resetParameters();
    }

    public void resetParameters(){
        Config.setConfigFile(configFile);
        List<String> valid_dataset_names = new ArrayList<>();
        //处理运行库中的信息，主要是读取计算参数
        Connection connection = null;
        if (runtimeDatabase != null) {
            try {

                if (runtimeDatabase.isEmpty()) {
                    connection = DriverManager.getConnection("jdbc:sqlite:series.db");
                } else
                    connection = DriverManager.getConnection("jdbc:sqlite:" + runtimeDatabase);

                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                ResultSet rs = statement.executeQuery(
                        "select data_dir, min_paasize, max_paasize, min_alphabet,max_alphabet,result_file from parameters");
                while (rs.next()) {
                    this.dataDirectory = rs.getString("data_dir");
                    this.resultFileName = rs.getString("result_file");
                    this.paaSizeRange = new Pair<>(
                            rs.getInt("min_paasize"),
                            rs.getInt("max_paasize") + 1);
                    this.alphabetRange = new Pair<>(
                            rs.getInt("min_alphabet"),
                            rs.getInt("max_alphabet") + 1);
                }
                rs.close();

                rs = statement.executeQuery(
                        "select name from valid_dataset_names");
                while (rs.next()) {
                    valid_dataset_names.add(rs.getString("name"));
                }
                rs.close();
                this.datasetNames=valid_dataset_names;

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    // connection close failed.
                    System.err.println(e.getMessage());
                }
            }
        }
        initialParameters();
    }

    private void initialParameters(){

        //如果数据库中没有设置相关参数，或者读取失败，则采用默认值
        if (this.dataDirectory == null || this.dataDirectory.isEmpty()) {
            this.dataDirectory = Config.getDataDirectory() + File.separator + "UCRArchive_2018";
        }
        if (this.resultFileName == null|| this.resultFileName.isEmpty()) {
            this.resultFileName = "example.xls";
        }
        if(this.paaSizeRange==null){
            this.paaSizeRange = new Pair<>(5, 21);
        }
        if(this.alphabetRange==null){
            this.alphabetRange = new Pair<>(3, 17);
        }
        //按照从参数设置，遍历数据文件
        this.dataFiles = new ArrayList<>();
        File ff = new File(this.dataDirectory);
        File[] files = ff.listFiles();
        for (File f : files) {
            if (f.isDirectory() && (!f.getName().equals("."))) {
                boolean b = datasetNames.stream().anyMatch(s -> s.equals(f.getName()));
                if (b) {//过滤不属于valid_dataset_names中的数据集
                    String trainFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TRAIN.tsv";
                    String testFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TEST.tsv";
                    dataFiles.add(new Pair<>(trainFile, testFile));
                }
            }
        }
    }
    public ExperimentalConfig(String runtimeDatabase) {
        this("series.properties",runtimeDatabase);
    }


    public ExperimentalConfig() {
        this("series.properties",null);
    }


    public String getRuntimeDatabase() {
        return runtimeDatabase;
    }

    public void setRuntimeDatabase(String runtimeDatabase) {
        this.runtimeDatabase = runtimeDatabase;
    }

    /**
     * Each element in the list is a pair.
     * The first item of a pair is a train file name,
     * and the second item of a pair is a test file name.
     *
     * @return
     */
    public List<Pair<String, String>> getDataFiles() {
        if(dataFiles==null)
            initialParameters();
        return dataFiles;
    }

    public String getResultFile() {
        return Config.getTestOutputDirectory() + File.separator + resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public Pair<Integer, Integer> getPaaSizeRange() {
        if(paaSizeRange==null)
            initialParameters();
        return paaSizeRange;
    }

    public void setPaaSizeRange(Pair<Integer, Integer> paaSizeRange) {
        this.paaSizeRange = paaSizeRange;
    }

    public Pair<Integer, Integer> getAlphabetRange() {
        if(alphabetRange==null)
            initialParameters();
        return alphabetRange;
    }

    public void setAlphabetRange(Pair<Integer, Integer> alphabetRange) {
        this.alphabetRange = alphabetRange;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }
    public void setDataDirectory(String d) {
         dataDirectory=d;
        //按照从参数设置，遍历数据文件
        this.dataFiles = new ArrayList<>();
        File ff = new File(this.dataDirectory);
        File[] files = ff.listFiles();
        for (File f : files) {
            if (f.isDirectory() && (!f.getName().equals("."))) {
                boolean b = datasetNames.stream().anyMatch(s -> s.equals(f.getName()));
                if (b) {//过滤不属于valid_dataset_names中的数据集
                    String trainFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TRAIN.tsv";
                    String testFile = this.dataDirectory + File.separator + f.getName() + File.separator + f.getName() + "_TEST.tsv";
                    dataFiles.add(new Pair<>(trainFile, testFile));
                }
            }
        }
    }

    public void writeResults(List<Pair<String, double[]>> results) {
        File file = new File(getResultFile());
        try {
            file.createNewFile();
            //创建新工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //新建工作表
            HSSFSheet sheet = workbook.createSheet("sheet1");
            int r = 0;
            int c = 0;
            for (Pair<String, double[]> p : results) {
                //创建行,行号作为参数传递给createRow()方法,第一行从0开始计算
                HSSFRow row = sheet.createRow(r);
                c = 0;
                HSSFCell cell = row.createCell(c);
                //设置单元格的值,即C1的值(第一行,第三列)
                cell.setCellValue(p.first());
                for (double d : p.second()) {
                    //创建单元格,row已经确定了行号,列号作为参数传递给createCell(),第一列从0开始计算
                    ++c;
                    cell = row.createCell(c);
                    //设置单元格的值,即C1的值(第一行,第三列)
                    cell.setCellValue(String.valueOf(d));
                }
                r++;
            }
            //输出到磁盘中
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
