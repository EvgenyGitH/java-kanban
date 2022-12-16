public class Task {
    protected String taskName;
    protected String taskDescription;
    protected String taskStatus; // NEW - IN_PROGRESS - DONE

    public Task(String taskName, String taskDescription, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

}
