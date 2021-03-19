package Business;

import Model.MonitoredData;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessor {
    private final List<MonitoredData> monitoredData;

    public DataProcessor(List<MonitoredData> monitoredData) {
        this.monitoredData = monitoredData;
    }

    public void task1() {
        String filename = "Task_1.txt";
        File taskFile = new File(filename);
        try {
            taskFile.createNewFile();
            Files.write(Paths.get(filename), (Iterable<String>) monitoredData.stream().map(MonitoredData::toString)::iterator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void task2() {
        String filename = "Task_2.txt";
        File taskFile = new File(filename);
        try {
            taskFile.createNewFile();
            Stream<LocalDate> startDates = monitoredData.stream().map(data -> data.getStartTime().toLocalDate());
            Stream<LocalDate> endDates = monitoredData.stream().map(data -> data.getEndTime().toLocalDate());
            Stream<LocalDate> dates = Stream.concat(startDates, endDates);
            PrintWriter printWriter = new PrintWriter(taskFile);
            printWriter.println(dates.distinct().count());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void task3() {
        String filename = "Task_3.txt";
        File taskFile = new File(filename);
        try {
            taskFile.createNewFile();
            Map<String, Long> occurrences = monitoredData.stream()
                    .collect(Collectors.groupingBy(MonitoredData::getActivityName, Collectors.counting()));
            PrintWriter printWriter = new PrintWriter(taskFile);
            occurrences.forEach((key, value) -> printWriter.println(key + " - " + value.toString() + " times"));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void task4() {
        String filename = "Task_4.txt";
        File taskFile = new File(filename);
        try {
            taskFile.createNewFile();
            Collector<MonitoredData, ?, Map<String, Integer>> summingAppearancesByName =
                    Collectors.groupingBy(MonitoredData::getActivityName, Collectors.summingInt(o -> 1));
            Collector<MonitoredData, ?, Map<Integer, Map<String, Integer>>> summingAppearancesByNameAndDate =
                    Collectors.groupingBy(MonitoredData::getDistinctDay, summingAppearancesByName);
            Map<Integer, Map<String, Integer>> output = monitoredData.stream()
                    .collect(summingAppearancesByNameAndDate);
            PrintWriter printWriter = new PrintWriter(taskFile);
            output.forEach((key, value) -> {
                printWriter.println("Day of year: " + key.toString());
                value.forEach((innerKey, innerValue) -> printWriter.println("\tActivity: " + innerKey + " - " + innerValue + " times."));
            });
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void task5() {
        String filename = "Task_5.txt";
        File taskFile = new File(filename);

        try {
            taskFile.createNewFile();
            Collector<MonitoredData, ?, Long> activityDuration = Collectors.summingLong(MonitoredData::activityDuration);
            Collector<MonitoredData, ?, Map<String, Long>> groupedDurations =
                    Collectors.groupingBy(MonitoredData::getActivityName, activityDuration);
            Map<String, Long> durations = monitoredData.stream().collect(groupedDurations);
            PrintWriter printWriter = new PrintWriter(taskFile);
            durations.forEach((key, value) -> printWriter.println("Activity " + key + ", total duration: " + value + " minutes."));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void task6() {
        String filename = "Task_6.txt";
        File taskFile = new File(filename);

        try {
            taskFile.createNewFile();
            Function<MonitoredData, Integer> noActivities =
                    (data -> ((Long) monitoredData.stream()
                            .filter(o -> o.getActivityName().equals(data.getActivityName()))
                            .count()).intValue());
            Function<MonitoredData, Integer> noShortActivities =
                    (data -> ((Long) monitoredData.stream()
                            .filter(o -> o.getActivityName().equals(data.getActivityName()))
                            .filter(o -> o.activityDuration() < 5)
                            .count()).intValue());
            List<String> longActivities = monitoredData.stream()
                    .filter(data -> noShortActivities.apply(data) / noActivities.apply(data).floatValue() > 0.9)
                    .map(MonitoredData::getActivityName)
                    .distinct()
                    .collect(Collectors.toList());
            PrintWriter printWriter = new PrintWriter(taskFile);
            longActivities.forEach(printWriter::println);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
