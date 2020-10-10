package cn.edu.cug.cs.gtl.series.app.cmd;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.series.io.UCRArchiveReader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java -cp series-1.0-SNAPSHOT-jar-with-dependencies.jar cn.edu.cug.cs.gtl.series.app.cmd.MainApp -h  -c series.properties  -d /Users/zhenwenhe/git/data+local/UCRArchive_96  -o /Users/zhenwenhe/git/data/outputResult.xls  -p 5,21  -a 3,17   -m  knn  -r  hax
 */
public class MainApp {
    public static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class); //slf4j
    String dataDirectory=null;//输入数据文件的根目录
    List<Pair<String, String>> dataFiles=null;//所有有效的数据集文件对，每对包含一个训练文件和一个测试文件
    Pair<Integer, Integer> paaSizeRange=null;
    Pair<Integer, Integer> alphabetRange=null;
    String outputFile=null;//输出结果文件
    String configFile =null;//app 配置文件
    String algorithm=null;//方法名
    String representation=null;//表达方式

    public static void main(String[] args) {
        if(args.length<=4){
            String[] arg ={
                    "-h",
                    "-c","series.properties",
                    "-d","/Users/zhenwenhe/git/data/UCRArchive_2018",
                    "-o","/Users/zhenwenhe/git/data/outputResult.xls",
                    "-p", "5,6",
                    "-a",  "3,17",
                    "-m", "naivebayes",
                    "-r" , "hax"
            };
            args=arg;
        };
        MainApp mainApp=new MainApp();
        mainApp.parseLine(addOptions(),args);
        mainApp.run();
    }

    /**
     * 获取所有要参与计算的数据文件名（绝对名称）
     */
    protected  void getAllDataFiles(){
        UCRArchiveReader reader= new UCRArchiveReader(this.dataDirectory,true,true,Integer.MAX_VALUE,Integer.MAX_VALUE);
        //按照从参数设置，遍历数据文件
        this.dataFiles = reader.getDataFiles();
    }

    protected  void parseLine(Options options, String[] args){

        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        CommandLine commandLine = null;
        CommandLineParser parser = new PosixParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                // 打印使用帮助
                hf.printHelp("MainApp", options, true);
            }


            // 打印opts的名称和值
            System.out.println("--------------------------------------");
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt1 : opts) {
                    String name = opt1.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    System.out.println(name + "=>" + value);
                }
            }
            System.out.println("--------------------------------------");

            String tmp;
            this.configFile="series.properties";
            if (commandLine.hasOption('c')) {
                this.configFile=commandLine.getOptionValue('c');
            }
            if (commandLine.hasOption('p')) {
                tmp=commandLine.getOptionValue('p');
                Pattern pattern=Pattern.compile("\\d+");
                Matcher m = pattern.matcher(tmp);
                this.paaSizeRange=new Pair<>(0,0);
                m.find();
                this.paaSizeRange.setKey(Integer.valueOf(m.group()));
                m.find();
                this.paaSizeRange.setValue(Integer.valueOf(m.group()));
            }

            if (commandLine.hasOption('a')) {
                tmp=commandLine.getOptionValue('a');
                Pattern pattern=Pattern.compile("\\d+");
                Matcher m = pattern.matcher(tmp);
                this.alphabetRange=new Pair<>(0,0);
                m.find();
                this.alphabetRange.setKey(Integer.valueOf(m.group()));
                m.find();
                this.alphabetRange.setValue(Integer.valueOf(m.group()));
            }

            if (commandLine.hasOption('d')) {
                this.dataDirectory=commandLine.getOptionValue('d');
            }

            if (commandLine.hasOption('o')) {
                this.outputFile=commandLine.getOptionValue('o');
            }

            if (commandLine.hasOption('m')) {
                this.algorithm=commandLine.getOptionValue('m');
            }

            if (commandLine.hasOption('r')) {
                this.representation=commandLine.getOptionValue('r');
            }

            getAllDataFiles();
        }
        catch (ParseException e) {
            hf.printHelp("MainApp", options, true);
        }

    }
    protected static Options addOptions( ) {
        Options options = new Options();
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("c", "configFile", true, "config properties file");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("p", "paaSizeRange", true, "PAA size range, for example, [5,21]");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("d", "dataDirectory", true, "the root directory of the data set");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("o", "outputFile", true, "the output result file");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("m", "methodName", true, "the method name for example knn, bayes");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("r", "representation", true, "series representation method for example hax, sax");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("a", "alphabetRange", true, "alphabet range , for example, [3,17]");
        opt.setRequired(false);
        options.addOption(opt);


        return options;
    }

    public void run(){
        LOGGER.info(this.dataDirectory);
        if(this.algorithm.toLowerCase().equals("knn")){

            KNNApp app=new KNNApp(this.dataFiles,this.paaSizeRange,this.alphabetRange);

            if(this.representation.toUpperCase().equals("HAX")){
                app.HAX();
            }
            else if(this.representation.toUpperCase().equals("HCAX")){
                app.HCAX();
            }
            else if(this.representation.toUpperCase().equals("PAX")){
                app.PAX();
            }
            else if(this.representation.toUpperCase().equals("SAX")){
                app.SAX();
            }
            else if(this.representation.toUpperCase().equals("ZCAX")){
                app.ZCAX();
            }
            else if(this.representation.toUpperCase().equals("ESAX")){
                app.ESAX();
            }
            else if(this.representation.toUpperCase().equals("SAXTD")){
                app.SAXTD();
            }
            else if(this.representation.toUpperCase().equals("SAXTG")){
                app.SAXTG();
            }
            else if(this.representation.toUpperCase().equals("ED")){
                app.ED();
            }
            else if(this.representation.toUpperCase().equals("DTW")){
                app.DTW();
            }
            System.gc();
        }
        else if(this.algorithm.toLowerCase().equals("naivebayes")){

            NaiveBayesApp app=new NaiveBayesApp(this.dataFiles,this.paaSizeRange,this.alphabetRange);
            if(this.representation.toUpperCase().equals("HAX")){
                app.HAX();
            }
            else if(this.representation.toUpperCase().equals("HCAX")){
                app.HCAX();
            }
            else if(this.representation.toUpperCase().equals("PAX")){
                app.PAX();
            }
            else if(this.representation.toUpperCase().equals("SAX")){
                app.SAX();
            }
            else if(this.representation.toUpperCase().equals("ZCAX")){
                app.ZCAX();
            }
            else if(this.representation.toUpperCase().equals("ESAX")){
                app.ESAX();
            }
            else if(this.representation.toUpperCase().equals("SAXTD")){
                app.SAXTD();
            }
            else if(this.representation.toUpperCase().equals("SAXTG")){
                app.SAXTG();
            }
            else if(this.representation.toUpperCase().equals("ED")){
                app.ED();
            }
            else if(this.representation.toUpperCase().equals("DTW")){
                app.DTW();
            }

            System.gc();
        }

    }
}
