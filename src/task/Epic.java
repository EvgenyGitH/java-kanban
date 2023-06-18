package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

 //   protected LocalDateTime endTime;
    public ArrayList<Integer> subTaskGroup = new ArrayList<>();

    public Epic( String taskName, String taskDescription, StatusTask taskStatus) {
        super(taskName, taskDescription, taskStatus);
        endTime = LocalDateTime.now();
        duration = Duration.ZERO;
        startTime = LocalDateTime.now();
    }

    public Epic( String startTime,String duration, String taskName, String taskDescription, StatusTask taskStatus) {

        super(startTime, duration, taskName, taskDescription, taskStatus);
      /*  this.startTime = LocalDateTime.parse(startTime, formatter);
        this.duration = Duration.ofMinutes(Long.parseLong(duration));
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;*/

        endTime = LocalDateTime.now();
    }


    public ArrayList<Integer> getSubTaskGroup() {
        return subTaskGroup;
    }

    public void setSubTaskGroup(ArrayList<Integer> subTaskGroup) {
        this.subTaskGroup = subTaskGroup;
    }

/*    public LocalDateTime getEndTimeEpic(){
        return startTime +
    }*/


}
