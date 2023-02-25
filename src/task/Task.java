package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

    protected LocalDateTime startTime;
    protected Duration duration;

    protected LocalDateTime endTime;

    protected int idTask;
    protected String taskName;
    protected String taskDescription;
    protected StatusTask taskStatus; // NEW - IN_PROGRESS - DONE

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Task(String taskName, String taskDescription, StatusTask taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public Task(String startTime,String duration, String taskName, String taskDescription, StatusTask taskStatus) {
        this.startTime = LocalDateTime.parse(startTime, formatter);
        this.duration = Duration.ofMinutes(Long.parseLong(duration));
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }



    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public StatusTask getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(StatusTask taskStatus) {
        this.taskStatus = taskStatus;
    }


}
