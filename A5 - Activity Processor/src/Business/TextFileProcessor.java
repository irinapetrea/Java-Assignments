package Business;

import Model.MonitoredData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextFileProcessor {
    public static List<MonitoredData> parseTextFile(String pathname) {
        List<MonitoredData> monitoredDataList = null;
        try {
            Stream<String> stream = Files.lines(Paths.get(pathname));
            monitoredDataList = stream.map(TextFileProcessor::parseMonitoredData).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monitoredDataList;
    }

    private static MonitoredData parseMonitoredData(String data) {
        String[] parts = data.split("\t\t");

        String startTimeString = parts[0].trim();
        String endTimeString = parts[1].trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeString, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeString, formatter);
        String activityName = parts[2].trim();
        return new MonitoredData(startTime, endTime, activityName);
    }


}
