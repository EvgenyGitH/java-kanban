package task;

public class Subtask extends Task {
    private int epicGroup;

    public Subtask(String taskName, String taskDescription, StatusTask taskStatus, int epicGroup) {
        super(taskName, taskDescription, taskStatus);
        this.epicGroup = epicGroup;
    }

    public Subtask(String startTime, String duration, String taskName, String taskDescription, StatusTask taskStatus, int epicGroup) {
        super(startTime, duration, taskName, taskDescription, taskStatus);
        this.epicGroup = epicGroup;
    }

    public int getEpicGroup() {
        return epicGroup;
    }

    public void setEpicGroup(int epicGroup) {
        this.epicGroup = epicGroup;
    }
}
