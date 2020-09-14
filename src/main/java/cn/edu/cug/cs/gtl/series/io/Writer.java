package cn.edu.cug.cs.gtl.series.io;

import java.util.List;

public interface Writer {
    /** Write by Data Point
     *  Point point = Point.measurement("temperature")
     *                     .addTag("location", "west")
     *                     .addField("value", 55D)
     *                     .time(Instant.now().toEpochMilli());
     *  writeApi.writePoint(record);
     * @param p
     * @return
     */
    boolean writePoint(Point p);
    /** Write by LineProtocol
     * String record = "temperature,location=north value=60.0";
     * writeApi.writeRecord(record);
     * @param record
     * @return
     */
    boolean writeRecord(String record);

    /** Write by POJO
     * Temperature temperature = new Temperature();
     *             temperature.location = "south";
     *             temperature.value = 62D;
     *             temperature.time = Instant.now();
     * writeApi.writeMeasurement(temperature);
     * @param objectPOJO
     * @return
     */
    boolean writeMeasurement(Object objectPOJO);

    /**
     *
     * @param seriesList
     * @return
     */
    boolean writeSeries(List<Series> seriesList);

    /**
     *
     * @param multiSeries
     * @return
     */
    boolean writeSeries(MultiSeries multiSeries);
}
