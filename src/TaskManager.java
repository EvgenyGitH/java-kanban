import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    private int id = 1;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    //создание ID
    protected int creatId (){
        return id++;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskManager that = (TaskManager) o;
        return id == that.id && Objects.equals(tasks, that.tasks) && Objects.equals(epics, that.epics) && Objects.equals(subtasks, that.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tasks, epics, subtasks);
    }

    //Добавление Задач, Эпиков, Субзадач
    public void saveTask (Task task){
        task.taskStatus = "NEW";
        int taskId = creatId();
        tasks.put(taskId, task);
    }
    public void saveEpic (Epic epic){
        epic.taskStatus = "NEW";
        int epicId = creatId();
        epics.put(epicId, epic);
    }
    public void saveSubtask (Subtask subtask) {
        subtask.taskStatus = "NEW";
        int subTaskId = creatId();
        for (Integer epic : epics.keySet()) {
            if (epic.equals(subtask.epicGroup)){
          // Epic epicData = epics.get(epic);
                epics.get(epic).subTaskGroup.add(subTaskId);
              //  epicData.subTaskGroup.add(subTaskId);

               // epics.put(epic, epics.get(epic));
            }
        }
        subtasks.put(subTaskId, subtask);
    }


    //проверочный код
 /*   void print (ArrayList <Integer> subTaskGroup){
        for(int i: subTaskGroup){
            System.out.println(i);
        }
    }*/


    //проверочный код
   /* void print2 (){
        for (Integer epicId : epics.keySet()) {
            System.out.println("Эпики: ");
            Epic ecipData = epics.get(epicId);
            System.out.println("ID: " + epicId + " taskName: " + ecipData.taskName + ", taskDescription: " +
                    ecipData.taskDescription + ", taskStatus: " + ecipData.taskStatus);
            for(int i: ecipData.subTaskGroup) {
                System.out.println(i);

                        }
        }
    }*/


    //Получение списка всех задач.
    public void printAllTask() {
        System.out.println("Задачи: ");
        for (Integer taskId : tasks.keySet()) {
            Task taskData = tasks.get(taskId);
            System.out.println("ID: " + taskId + " taskName: " + taskData.taskName + ", taskDescription: " +
                    taskData.taskDescription + ", taskStatus: " + taskData.taskStatus);
        }
        for (Integer epicId : epics.keySet()) {
            System.out.println("Эпики: ");
            Epic ecipData = epics.get(epicId);
            System.out.println("ID: " + epicId + " taskName: " + ecipData.taskName + ", taskDescription: " +
                    ecipData.taskDescription + ", taskStatus: " + ecipData.taskStatus);

            System.out.println("Субзадачи:");
            for (Integer subTaskId : subtasks.keySet()) {
                Subtask subTaskData = subtasks.get(subTaskId);
                if (subTaskData.epicGroup == epicId){
                    System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.taskName +
                            ", taskDescription: " + subTaskData.taskDescription +
                            ", taskStatus: " + subTaskData.taskStatus + " epicGroup: " + subTaskData.epicGroup);
                }
            }
        }
    }


    //Удаление всех задач.
    public void removeAllTask() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        System.out.println("Список задач пуст");
    }

    //Получение по идентификатору.
    public void getTaskById (int iDnumber) {
        System.out.println("Поиск по номеру ID:");
        for (Integer id : tasks.keySet()) {
            if (id.equals(iDnumber)) {
                Task data = tasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus);
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = epics.get(id);
                System.out.println("ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus);
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = subtasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus + " epicGroup: " + data.epicGroup);
            }
        }
    }


    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateById (int idUpdate, Object updateDataById) {
        System.out.println("Обновление данных по номеру ID:");
        for (Integer id : tasks.keySet()) {
            if (id.equals(idUpdate)) {
                Task data = (Task)updateDataById;
                tasks.put(idUpdate,data);
                System.out.println("ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus);
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(idUpdate)) {
                Epic data = (Epic) updateDataById;
                epics.put(idUpdate,data);
                System.out.println("ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus);
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(idUpdate)) {
                Subtask data = (Subtask) updateDataById;
                subtasks.put(idUpdate,data);
                statusUpdate();
                System.out.println("ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus + " epicGroup: " + data.epicGroup);
            }
        }
    }

    //Удаление по идентификатору.
    public void removeTaskById (int iDnumber) {
        System.out.println("Удаление по номеру ID:" + iDnumber);

        for (Integer id : tasks.keySet()) {
            if (id.equals(iDnumber)) {
                Task data = tasks.get(id);
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus);
                tasks.remove(iDnumber);
                break;
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = epics.get(id);
                ArrayList <Integer> removeId = new ArrayList<>();
                for (Integer idSubTask : subtasks.keySet()) {
                    Subtask dataSubTask = subtasks.get(idSubTask);
                    if (dataSubTask.epicGroup == iDnumber) {
                        removeId.add(idSubTask);
                    }
                }
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus);
                for(int i:removeId){
                    subtasks.remove(i);
                }
                epics.remove(iDnumber);
                break;
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = subtasks.get(id);
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.taskName + ", taskDescription: " +
                        data.taskDescription + ", taskStatus: " + data.taskStatus + " epicGroup: " + data.epicGroup);

                for (Integer epic : epics.keySet()) {
                    if (epic.equals(data.epicGroup)){
                        Epic epicData = epics.get(epic); // !!! здесь проблема , получаю код с пустым аррейлистом
                            for (int i = 0; i < epicData.subTaskGroup.size(); i++) {
                            if (epicData.subTaskGroup.get(i) == id) {
                                epicData.subTaskGroup.remove(i);
                            }
                        }
                    }
                }

                subtasks.remove(iDnumber);
                statusUpdate();
                break;
            }
        }
    }


    //Получение списка Подзадач Эпика по ID Эпика.
    public void printEpicSubtask(int epicIdNumber ) {
        for (Integer epicId : epics.keySet()) {
            if (epicIdNumber == epicId) {
                Epic ecipData = epics.get(epicId);
                System.out.println("Эпик: ID: " + epicId + " taskName: " + ecipData.taskName + ", taskDescription: " +
                        ecipData.taskDescription + ", taskStatus: " + ecipData.taskStatus);
                System.out.println("Субзадачи:");
                for (Integer subTaskId : subtasks.keySet()) {
                    Subtask subTaskData = subtasks.get(subTaskId);
                    if (subTaskData.epicGroup == epicId) {
                        System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.taskName +
                                ", taskDescription: " + subTaskData.taskDescription +
                                ", taskStatus: " + subTaskData.taskStatus + " epicGroup: " + subTaskData.epicGroup);
                    }
                }
            }
        }
    }

    //Управление статусами
    public void statusUpdate() {
        ArrayList <String> newDone = new ArrayList<>();
        int countDone = 0;
        int countNew = 0;

        for (Integer idEpic : epics.keySet()) {
            Epic dataEpic = epics.get(idEpic);
            for (Integer idSubtask : subtasks.keySet()) {
                Subtask dataSubtask = subtasks.get(idSubtask);
                if (dataSubtask.epicGroup == idEpic) {
                    if (dataSubtask.taskStatus != null) {
                        if (dataSubtask.taskStatus.equals("DONE")) {
                            newDone.add("D");
                        }
                        if (dataSubtask.taskStatus.equals("NEW")) {
                            newDone.add("N");
                        }
                    }
                }
            }
            for (String result : newDone) {
                if(result.equals("D")){
                    countDone++;
                } else {
                    countNew++;
                }
            }

            if (countDone>0 && countNew==0){
                dataEpic.taskStatus = "DONE";
                Epic epicsUpdate = new Epic(dataEpic.taskName, dataEpic.taskDescription, dataEpic.taskStatus);
                epics.put(idEpic, epicsUpdate);
            } else if (countNew>0 && countDone==0){
                dataEpic.taskStatus = "NEW";
                Epic epicsUpdate = new Epic(dataEpic.taskName, dataEpic.taskDescription, dataEpic.taskStatus);
                epics.put(idEpic, epicsUpdate);
            } else {
                dataEpic.taskStatus = "IN_PROGRESS";
                Epic epicsUpdate = new Epic(dataEpic.taskName, dataEpic.taskDescription, dataEpic.taskStatus);
                epics.put(idEpic, epicsUpdate);
            }
            newDone.clear();
            countDone = 0;
            countNew = 0;
        }
    }

}
