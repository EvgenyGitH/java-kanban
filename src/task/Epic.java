package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    // protected LocalDateTime endTime;
    public ArrayList<Integer> subTaskGroup = new ArrayList<>();

    public Epic(String taskName, String taskDescription, StatusTask taskStatus) {
        super(taskName, taskDescription, taskStatus);
        endTime = LocalDateTime.now();
        duration = Duration.ZERO;
        startTime = LocalDateTime.now();
    }

    public Epic(String startTime, String duration, String taskName, String taskDescription, StatusTask taskStatus) {
        super(startTime, duration, taskName, taskDescription, taskStatus);
        endTime = LocalDateTime.now();
    }

    public ArrayList<Integer> getSubTaskGroup() {
        return subTaskGroup;
    }

    public void setSubTaskGroup(ArrayList<Integer> subTaskGroup) {
        this.subTaskGroup = subTaskGroup;
    }

}
