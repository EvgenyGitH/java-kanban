package manager;

import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;


    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    //  HistoryManager historyManager = Managers.getDefaultHistory();
    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();


    //создание ID
    protected int creatId (){
        return id++;
    }


    //Добавление Задач, Эпиков, Субзадач
    @Override
    public void saveTask (Task task){
        task.setTaskStatus(StatusTask.NEW);
        int taskId = creatId();
        task.setIdTask(taskId);
        tasks.put(taskId, task);
    }
    @Override
    public void saveEpic (Epic epic){
        epic.setTaskStatus(StatusTask.NEW);
        int epicId = creatId();
        epic.setIdTask(epicId);
        epics.put(epicId, epic);
    }
    @Override
    public void saveSubtask (Subtask subtask) {
        subtask.setTaskStatus(StatusTask.NEW);
        int subTaskId = creatId();
        subtask.setIdTask(subTaskId);
        for (Integer epic : epics.keySet()) {
            if (epic.equals(subtask.getEpicGroup())){
                epics.get(epic).getSubTaskGroup().add(subTaskId); // ???
            }
        }
        subtasks.put(subTaskId, subtask);
    }


    //проверочный код -- удалить
   void print (ArrayList <Integer> subTaskGroup){
        for(int i: subTaskGroup){
            System.out.println(i);
        }
    }


    //проверочный код -- удалить
   void print2 (){
        for (Integer epicId : epics.keySet()) {
            System.out.println("Эпики: ");
            Epic ecipData = epics.get(epicId);
            System.out.println("ID: " + epicId + " taskName: " + ecipData.getTaskName() + ", taskDescription: " +
                    ecipData.getTaskDescription() + ", taskStatus: " + ecipData.getTaskStatus());
            for(int i: ecipData.getSubTaskGroup()) {
                System.out.println(i);
            }
        }
    }


    //Получение списка всех задач.
    @Override
    public void printAllTask() {
        System.out.println("Задачи: ");
        for (Integer taskId : tasks.keySet()) {
            Task taskData = tasks.get(taskId);
            System.out.println("ID: " + taskId + " taskName: " + taskData.getTaskName() + ", taskDescription: " +
                    taskData.getTaskDescription() + ", taskStatus: " + taskData.getTaskStatus());
        }
        for (Integer epicId : epics.keySet()) {
            System.out.println("Эпики: ");
            Epic ecipData = epics.get(epicId);
            System.out.println("ID: " + epicId + " taskName: " + ecipData.getTaskName() + ", taskDescription: " +
                    ecipData.getTaskDescription() + ", taskStatus: " + ecipData.getTaskStatus());

            System.out.println("Субзадачи:");
            for (Integer subTaskId : subtasks.keySet()) {
                Subtask subTaskData = subtasks.get(subTaskId);
                if (subTaskData.getEpicGroup() == epicId){
                    System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                            ", taskDescription: " + subTaskData.getTaskDescription() +
                            ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " + subTaskData.getEpicGroup());
                }
            }
        }
    }


    //Удаление всех задач.
    @Override
    public void removeAllTask() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        System.out.println("Список задач пуст");
    }

    //Получение по идентификатору.
    @Override
    public  void getTaskById (Integer iDnumber) {
        for (Integer id : tasks.keySet()) {
            if (id.equals(iDnumber)) {
                Task data = tasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                getTask(data);
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = epics.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                getEpic(data);
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = subtasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());
                getSubtask(data);
            }
        }
    }


    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateById (int idUpdate, Object updateDataById) {
        System.out.println("Обновление данных по номеру ID:");
        for (Integer id : tasks.keySet()) {
            if (id.equals(idUpdate)) {
                Task updateData = (Task) updateDataById;
                Task data = tasks.get(id);
                data.setIdTask(id);
                data.setTaskName(updateData.getTaskName());
                data.setTaskDescription(updateData.getTaskDescription());
                data.setTaskStatus(updateData.getTaskStatus());
                //task.Task data = (task.Task)updateDataById; - удалить
              //  tasks.put(idUpdate,data); - удалить
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(idUpdate)) {
                Epic updateData = (Epic) updateDataById;
                Epic data = epics.get(id);
                data.setIdTask(id);
                data.setTaskName(updateData.getTaskName());
                data.setTaskDescription(updateData.getTaskDescription());
                data.setTaskStatus(updateData.getTaskStatus());
                //task.Epic data = (task.Epic) updateDataById; - удалить
                //epics.put(idUpdate,data); - удалить
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(idUpdate)) {
                Subtask updateData = (Subtask) updateDataById;
                Subtask data = subtasks.get(id);
                data.setIdTask(id);
                data.setTaskName(updateData.getTaskName());
                data.setTaskDescription(updateData.getTaskDescription());
                data.setTaskStatus(updateData.getTaskStatus());
                data.setEpicGroup(updateData.getEpicGroup());
              //  task.Subtask data = (task.Subtask) updateDataById; - удалить
              //  subtasks.put(idUpdate,data); - удалить
                statusUpdate();
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());
            }
        }
    }

    //Удаление по идентификатору.
    @Override
    public void removeTaskById (Integer iDnumber) {
        System.out.println("Удаление по номеру ID:" + iDnumber);

        for (Integer id : tasks.keySet()) {
            if (id.equals(iDnumber)) {
                Task data = tasks.get(id);
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                tasks.remove(iDnumber);
                inMemoryHistoryManager.remove(iDnumber);
                break;
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = epics.get(id);
                ArrayList <Integer> removeId = new ArrayList<>();
                for (Integer idSubTask : subtasks.keySet()) {
                    Subtask dataSubTask = subtasks.get(idSubTask);
                    if (dataSubTask.getEpicGroup() == iDnumber) {
                        removeId.add(idSubTask);
                        inMemoryHistoryManager.remove(dataSubTask.getIdTask());
                    }
                }
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                for(int i:removeId){
                    subtasks.remove(i);
                }
                epics.remove(iDnumber);
                inMemoryHistoryManager.remove(iDnumber);
                break;
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = subtasks.get(id);
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());

                Epic epicData = epics.get(data.getEpicGroup());
                for (int i = 0; i < epicData.getSubTaskGroup().size(); i++) {
                    if (epicData.getSubTaskGroup().get(i) == id) {
                        epicData.getSubTaskGroup().remove(i);
                    }
                }
                subtasks.remove(iDnumber);
                inMemoryHistoryManager.remove(iDnumber);
                statusUpdate();
                break;
            }
        }
    }


    //Получение списка Подзадач Эпика по ID Эпика.
    @Override
    public void printEpicSubtask(int epicIdNumber ) {
        for (Integer epicId : epics.keySet()) {
            if (epicIdNumber == epicId) {
                Epic epicData = epics.get(epicId);
                System.out.println("Эпик: ID: " + epicId + " taskName: " + epicData.getTaskName() + ", taskDescription: " +
                        epicData.getTaskDescription() + ", taskStatus: " + epicData.getTaskStatus());
                System.out.println("Субзадачи:");
                for (Integer subTaskId : subtasks.keySet()) {
                    Subtask subTaskData = subtasks.get(subTaskId);
                    if (subTaskData.getEpicGroup() == epicId) {
                        System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                                ", taskDescription: " + subTaskData.getTaskDescription() +
                                ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " + subTaskData.getEpicGroup());
                    }
                }
            }
        }
    }

    //Управление статусами
    @Override
    public void statusUpdate() {
        ArrayList <String> newDone = new ArrayList<>();
        int countDone = 0;
        int countNew = 0;

        for (Integer idEpic : epics.keySet()) {
            Epic dataEpic = epics.get(idEpic);
            for (Integer idSubtask : subtasks.keySet()) {
                Subtask dataSubtask = subtasks.get(idSubtask);
                if (dataSubtask.getEpicGroup() == idEpic) {
                    if (dataSubtask.getTaskStatus() != null) {
                        if (StatusTask.DONE==dataSubtask.getTaskStatus()) {
                            newDone.add("D");
                        }
                        if (StatusTask.NEW==dataSubtask.getTaskStatus()) {
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
                dataEpic.setTaskStatus(StatusTask.DONE);
            } else if (countNew>0 && countDone==0){
                dataEpic.setTaskStatus(StatusTask.NEW);
            } else {
                dataEpic.setTaskStatus(StatusTask.IN_PROGRESS);
            }
            newDone.clear();
            countDone = 0;
            countNew = 0;
        }
    }

    //отметка о просмотре
    @Override
    public void getTask(Task task){
        inMemoryHistoryManager.add(task);
    }
    @Override
    public void getEpic(Task task) {
        inMemoryHistoryManager.add(task);
    }
    @Override
    public void getSubtask(Task task){
        inMemoryHistoryManager.add(task);
    }


    //получение истории
    @Override
    public void getArrayHistory(){
        inMemoryHistoryManager.getHistory();
    }


}
