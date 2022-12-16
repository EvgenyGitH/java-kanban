public class Subtask extends Task {
    protected int epicGroup;
    public Subtask(String taskName, String taskDescription, String taskStatus, int epicGroup) {
        super(taskName, taskDescription, taskStatus);
        this.epicGroup = epicGroup;
    }
}

