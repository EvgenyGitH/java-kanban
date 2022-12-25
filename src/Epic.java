import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList <Integer> subTaskGroup = new ArrayList<>();

    public Epic(String taskName, String taskDescription, StatusTask taskStatus) {
        super(taskName, taskDescription, taskStatus);
    }
}
