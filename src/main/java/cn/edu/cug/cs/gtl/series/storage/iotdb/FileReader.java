package cn.edu.cug.cs.gtl.series.storage.iotdb;

import org.apache.iotdb.tsfile.read.TsFileSequenceReader;
import org.apache.iotdb.tsfile.read.reader.TsFileInput;

import java.io.IOException;

public class FileReader extends TsFileSequenceReader {
    public FileReader(String file) throws IOException {
        super(file);
    }

    public FileReader(String file, boolean loadMetadataSize) throws IOException {
        super(file, loadMetadataSize);
    }

    public FileReader(String file, boolean loadMetadata, boolean cacheDeviceMetadata) throws IOException {
        super(file, loadMetadata, cacheDeviceMetadata);
    }

    protected FileReader(TsFileInput input) throws IOException {
        super(input);
    }

    protected FileReader(TsFileInput input, boolean loadMetadataSize) throws IOException {
        super(input, loadMetadataSize);
    }

    protected FileReader(TsFileInput input, long fileMetadataPos, int fileMetadataSize) {
        super(input, fileMetadataPos, fileMetadataSize);
    }
}
