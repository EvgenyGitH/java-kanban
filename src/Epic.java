import java.util.ArrayList;

public class Epic extends Task {
    ArrayList <Integer> subTaskGroup = new ArrayList<>();

    public Epic(String taskName, String taskDescription, String taskStatus) {
        super(taskName, taskDescription, taskStatus);
    }
}
