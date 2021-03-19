package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MonitoredData {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String activityName;

    public MonitoredData(LocalDateTime startTime, LocalDateTime endTime, String activityName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityName = activityName;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "start=" + startTime.format(formatter) +
                "\tend=" + endTime.format(formatter) +
                "\t\tactivity=" + activityName;
    }

    public Long activityDuration() {
        return getStartTime().until(getEndTime(), ChronoUnit.MINUTES);
    }

    public Integer getDistinctDay() {
        return startTime.getDayOfYear();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getActivityName() {
        return activityName;
    }
}
