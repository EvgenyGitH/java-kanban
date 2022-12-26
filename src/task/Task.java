package task;

public class Task {

    protected int idTask;
    protected String taskName;
    protected String taskDescription;
    protected StatusTask taskStatus; // NEW - IN_PROGRESS - DONE

    public Task(String taskName, String taskDescription, StatusTask taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
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
