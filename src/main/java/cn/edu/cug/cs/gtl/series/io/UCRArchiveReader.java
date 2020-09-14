package cn.edu.cug.cs.gtl.series.io;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.io.StorableComparable;
import org.jetbrains.annotations.NotNull;
import tec.uom.se.internal.DefaultSystemOfUnitsService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
public class UCRArchiveReader {

    public static class Item implements StorableComparable<Item> {

        private static final long serialVersionUID = 2830084431996806566L;

        boolean missingValue;
        String  trainFile;
        String  testFile;
        String  name;
        int trainSize;
        int testSize;
        int numberOfClasses;
        int seriesLength;//if varyLength is true, seriesLength<0;
        String type;
        double errorRateOfED;// error rate of ED

        public Item(){

        }

        @Override
        public Object clone() {
            Item i = new Item();
            i.name=this.name;
            i.trainFile=this.trainFile;
            i.testFile=this.testFile;
            i.testSize=this.testSize;
            i.trainSize=this.trainSize;
            i.numberOfClasses=this.numberOfClasses;
            i.missingValue=this.missingValue;
            i.seriesLength=this.seriesLength;
            i.type=this.type;
            i.errorRateOfED=this.errorRateOfED;

            return i;
        }

        @Override
        public boolean load(DataInput dataInput) throws IOException {
            name= dataInput.readUTF();
            trainFile=dataInput.readUTF();
            testFile=dataInput.readUTF();
            trainSize=dataInput.readInt();
            testSize=dataInput.readInt();
            numberOfClasses=dataInput.readInt();
            seriesLength=dataInput.readInt();
            missingValue=dataInput.readInt()!=0;
            type=dataInput.readUTF();
            errorRateOfED=dataInput.readDouble();

            return true;
        }

        @Override
        public boolean store(DataOutput dataOutput) throws IOException {
            dataOutput.writeUTF(name);
            dataOutput.writeUTF(trainFile);
            dataOutput.writeUTF(testFile);
            dataOutput.writeInt(trainSize);
            dataOutput.writeInt(testSize);
            dataOutput.writeInt(numberOfClasses);
            dataOutput.writeInt(seriesLength);
            dataOutput.writeInt(missingValue?1:0);
            dataOutput.writeUTF(type);
            dataOutput.writeDouble(errorRateOfED);
            return true;
        }

        @Override
        public int compareTo(@NotNull UCRArchiveReader.Item o) {
            return this.name.compareTo(o.name);
        }

        public boolean isMissingValue() {
            return missingValue;
        }

        public void setMissingValue(boolean missingValue) {
            this.missingValue = missingValue;
        }

        public String getTrainFile() {
            return trainFile;
        }

        public void setTrainFile(String trainFile) {
            this.trainFile = trainFile;
        }

        public String getTestFile() {
            return testFile;
        }

        public void setTestFile(String testFile) {
            this.testFile = testFile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTrainSize() {
            return trainSize;
        }

        public void setTrainSize(int trainSize) {
            this.trainSize = trainSize;
        }

        public int getTestSize() {
            return testSize;
        }

        public void setTestSize(int testSize) {
            this.testSize = testSize;
        }

        public int getNumberOfClasses() {
            return numberOfClasses;
        }

        public void setNumberOfClasses(int numberOfClasses) {
            this.numberOfClasses = numberOfClasses;
        }

        public int getSeriesLength() {
            return seriesLength;
        }

        public void setSeriesLength(int seriesLength) {
            this.seriesLength = seriesLength;
        }

        public String getType(){
            return this.type;
        }

        public void setType(String type){
            this.type=type;
        }

        public double getErrorRateOfED(){
            return this.errorRateOfED;
        }

        public void setErrorRateOfED(double errorRateOfED){
            this.errorRateOfED=errorRateOfED;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Item)) return false;
            Item item = (Item) o;
            return isMissingValue() == item.isMissingValue() &&
                    getTrainSize() == item.getTrainSize() &&
                    getTestSize() == item.getTestSize() &&
                    getNumberOfClasses() == item.getNumberOfClasses() &&
                    getSeriesLength() == item.getSeriesLength() &&
                    Double.compare(item.getErrorRateOfED(), getErrorRateOfED()) == 0 &&
                    Objects.equals(getTrainFile(), item.getTrainFile()) &&
                    Objects.equals(getTestFile(), item.getTestFile()) &&
                    Objects.equals(getName(), item.getName()) &&
                    Objects.equals(getType(), item.getType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(isMissingValue(), getTrainFile(), getTestFile(), getName(), getTrainSize(), getTestSize(), getNumberOfClasses(), getSeriesLength(), getType(), getErrorRateOfED());
        }

        @Override
        public String toString() {
            return "Item{" +
                    "missingValue=" + missingValue +
                    ", trainFile='" + trainFile + '\'' +
                    ", testFile='" + testFile + '\'' +
                    ", name='" + name + '\'' +
                    ", trainSize=" + trainSize +
                    ", testSize=" + testSize +
                    ", numberOfClasses=" + numberOfClasses +
                    ", seriesLength=" + seriesLength +
                    ", type='" + type + '\'' +
                    ", errorRateOfED=" + errorRateOfED +
                    '}';
        }
    }


    String dataDirectory=null;//UCRArchive根目录
    List<Item> items=null;

    public UCRArchiveReader(String dataDirectory){
        this.dataDirectory=dataDirectory;
        try {
            initialize(false,false,Integer.MAX_VALUE,Integer.MAX_VALUE);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public UCRArchiveReader(String dataDirectory, boolean excludeVaryLength, boolean excludeMissingValue){
        this.dataDirectory=dataDirectory;
        try {
            initialize( excludeVaryLength,  excludeMissingValue, Integer.MAX_VALUE,Integer.MAX_VALUE);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param dataDirectory
     * @param excludeVaryLength
     * @param excludeMissingValue
     * @param maxDataSize
     * @param maxSeriesLength
     */
    public UCRArchiveReader(String dataDirectory, boolean excludeVaryLength, boolean excludeMissingValue, int maxDataSize, int maxSeriesLength){
        this.dataDirectory=dataDirectory;
        try {
            initialize( excludeVaryLength,  excludeMissingValue,maxDataSize,maxSeriesLength);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param excludeVaryLength
     * @param excludeMissingValue
     * @param maxDataSize
     * @param maxSeriesLength
     * @throws IOException
     */
    protected  void initialize(boolean excludeVaryLength, boolean excludeMissingValue, int maxDataSize, int maxSeriesLength) throws IOException{
        //按照从参数设置，遍历数据文件
        this.items = new ArrayList<>();

        File ff = new File(this.dataDirectory);
        File[] files = ff.listFiles();
        for (File f : files) {
            if (f.isDirectory() && (!f.getName().equals("."))) {

                String trainFile = this.dataDirectory + File.separator
                        + f.getName() + File.separator + f.getName() + "_TRAIN.tsv";
                String testFile = this.dataDirectory + File.separator
                        + f.getName() + File.separator + f.getName() + "_TEST.tsv";

                String readme = this.dataDirectory + File.separator
                        + f.getName() + File.separator + "README.md";

                Item i = new Item();
                i.setName(f.getName());
                i.setTrainFile(trainFile);
                i.setTestFile(testFile);
                analysisReadmeFile(readme,i);
                items.add(i);
            }
        }

        this.items.sort((i,j)->i.compareTo(j));

        String summaryFile = this.dataDirectory + File.separator + "DataSummary.csv";
        analysisSummaryFile(summaryFile,items);
        //提出不符合要求的数据
        this.items=this.items
                .stream()
                .filter(i->{
                            if(excludeMissingValue && i.isMissingValue())
                                return false;
                            if(excludeVaryLength && i.getSeriesLength()<0)
                                return false;
                            if(i.getTrainSize()>maxDataSize || i.getTestSize()>maxDataSize)
                                return false;
                            if(i.getSeriesLength()>maxSeriesLength)
                                return false;
                            return true;
                        })
                .collect(Collectors.toList());
    }

    protected void analysisSummaryFile(String summaryFile , List<Item> items) throws IOException{
        File f = new File(summaryFile);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        int length = 0;
        int i =0;
        line = br.readLine();//skip header
        while (line != null) {
            //读取一行，以逗号分隔
            String[] columns = line.split(FileDataSplitter.CSV.getDelimiter());
            Optional<Item> itemOptional=findItem(columns[2].trim());
            Item item = itemOptional.orElse(new Item());
            //第0个元素为ID
            String label = columns[0];
            //第1个元素为Type
            item.setType(columns[1].trim());
            //第2个元素为Name
            item.setName(columns[2].trim());
            //第3个元素为TrainSize
            item.setTrainSize(Integer.valueOf(columns[3].trim()));
            //第4个元素为TestSize
            item.setTestSize(Integer.valueOf(columns[4].trim()));
            //第5个元素为Class
            item.setNumberOfClasses(Integer.valueOf(columns[5].trim()));
            //第6个元素为Length
            try {
                item.setSeriesLength(Integer.valueOf(columns[6].trim()));
            }
            catch (NumberFormatException e){
                item.setSeriesLength(-1);
            }
            //第7个元素为ED error rate
            item.setErrorRateOfED(Double.valueOf(columns[7].trim()));
            //第8个元素为DTW error rate，放弃
            line = br.readLine();
            i++;
        }
    }

    protected void analysisReadmeFile(String readme, Item item) throws IOException{
        File f = new File(readme);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        int length = 0;

        while (line != null) {
            if (line.contains("Missing value:")){
                String[] columns = line.split(":");
                if(columns[1].trim().toLowerCase().equals("yes"))
                    item.setMissingValue(true);
                else
                    item.setMissingValue(false);

                break;
            }
            line = br.readLine();
        }
        br.close();
    }

    /**
     * 读取TSV文件中的数据，第一列为标签，其余的为序列值，返回列表中原始的个数等于文件中的行数
     * @param fileName, full path name
     * @return
     * @throws IOException
     */
    public static List<Pair<String,double[]>> readTSVFile(String fileName) throws IOException{
        cn.edu.cug.cs.gtl.io.File f = new cn.edu.cug.cs.gtl.io.File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        int length = 0;

        List<Pair<String,double[]>> r = new ArrayList<>();

        while (line != null) {
            //读取一行，以\t分隔
            String[] columns = line.split(FileDataSplitter.TSV.getDelimiter());
            length = columns.length - 1;
            double[] ys = new double[length];
            //第一个元素为标签
            String label = columns[0];
            //接下来的元素为时序值
            for (int i = 1; i < columns.length; ++i) {
                ys[i - 1] = Double.parseDouble(columns[i]);
            }
            r.add(new Pair<>(label,ys));
            line = br.readLine();
        }
        return r;
    }

    public Optional<Item> findItem(String itemName){
        for(Item i: items){
            if(i.getName().equals(itemName))
                return Optional.of(i);
        }
        return Optional.empty();
    }

    public Item getItem(int i){
        return items.get(i);
    }

    public int getNumberOfItems(){
        return items.size();
    }

    public List<Item> getItems(){
        return items;
    }

    public List<Pair<String,String>> getDataFiles(){
        List<Pair<String,String>> dfs= new ArrayList<>();
        for(Item i: items){
            dfs.add(new Pair<>(i.getTrainFile(),i.getTestFile()));
        }
        return dfs;
    }

}
