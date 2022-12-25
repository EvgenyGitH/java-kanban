public class Subtask extends Task {
    protected int epicGroup;
    public Subtask(String taskName, String taskDescription, StatusTask taskStatus, int epicGroup) {
        super(taskName, taskDescription, taskStatus);
        this.epicGroup = epicGroup;
    }
}

