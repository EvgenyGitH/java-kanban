public class Task {

    protected int idTask; // есть ли необходимость в idTask? мы же используем в качестве ID сгенерированный ключ id для Hashmap.
    protected String taskName;
    protected String taskDescription;
    protected String taskStatus; // NEW - IN_PROGRESS - DONE

    public Task(String taskName, String taskDescription, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

}
