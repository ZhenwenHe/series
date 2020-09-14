package cn.edu.cug.cs.gtl.series.storage.iotdb;

import cn.edu.cug.cs.gtl.series.io.SeriesSchema;
import org.apache.iotdb.tsfile.common.conf.TSFileConfig;
import org.apache.iotdb.tsfile.exception.write.WriteProcessException;
import org.apache.iotdb.tsfile.write.TsFileWriter;
import org.apache.iotdb.tsfile.write.schema.MeasurementSchema;
import org.apache.iotdb.tsfile.write.schema.Schema;
import org.apache.iotdb.tsfile.write.writer.TsFileIOWriter;
import org.apache.iotdb.tsfile.write.writer.TsFileOutput;

import java.io.File;
import java.io.IOException;

public class FileWriter extends TsFileWriter {

    public FileWriter(File file) throws IOException {
        super(file);
    }

    protected FileWriter(TsFileIOWriter fileWriter) throws IOException {
        super(fileWriter);
    }

    protected FileWriter(File file, Schema schema) throws IOException {
        super(file, schema);
    }

    protected FileWriter(TsFileOutput output, Schema schema) throws IOException {
        super(output, schema);
    }

    protected FileWriter(File file, Schema schema, TSFileConfig conf) throws IOException {
        super(file, schema, conf);
    }

    protected FileWriter(TsFileIOWriter fileWriter, Schema schema, TSFileConfig conf) throws IOException {
        super(fileWriter, schema, conf);
    }

    public void registerTimeseries(SeriesSchema seriesSchema)
            throws WriteProcessException {

    }

    protected MeasurementSchema toMSchema(SeriesSchema seriesSchema){
        MeasurementSchema measurementSchema = new MeasurementSchema();
        return measurementSchema;
    }
}
