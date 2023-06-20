package manager;

import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    private int id = 1;

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();

    @Override
    public HashMap<Integer, Task> getTasksHashMap() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpicsHashMap() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasksHashMap() {
        return subtasks;
    }

    protected Set<Task> timeTree = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getStartTime() == null && o2.getStartTime() == null) {
                return 0;
            }
            if (o1.getStartTime() == null && o2.getStartTime() != null) {
                return 1;
            }
            if (o1.getStartTime() != null && o2.getStartTime() == null) {
                return -1;
            }

            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    });

    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    //создание ID
    protected int creatId() {
        return id++;
    }

//  getPrioritizedTasks возвращает отсортированный список задач. По нему можно пройтись за O(n) и проверить все задачи на пересечение.

    //Добавление Задач, Эпиков, Субзадач
    @Override
    public int saveTask(Task task) {
        task.setTaskStatus(StatusTask.NEW);
        int taskId = creatId();
        task.setIdTask(taskId);
        task.setEndTime(checkEndTime(task.getStartTime(), task.getDuration()));
        int returnId;
        //   getPrioritizedTasks();
        if (task.getStartTime() != null) {
            if (isTimeFree(task)) {
                tasks.put(taskId, task);
                timeTree.add(task);
                returnId = task.getIdTask();
            } else {
                System.out.println("Время занято");
                returnId = 0;
            }

        } else {

            tasks.put(taskId, task);
            timeTree.add(task);
            returnId = task.getIdTask();
        }
        return returnId;

    }

    @Override
    public int saveEpic(Epic epic) {
        epic.setTaskStatus(StatusTask.NEW);
        int epicId = creatId();
        epic.setIdTask(epicId);
        epics.put(epicId, epic);
        return epic.getIdTask();
    }

    @Override
    public int saveSubtask(Subtask subtask) {
        subtask.setTaskStatus(StatusTask.NEW);
        int subTaskId = creatId();
        subtask.setIdTask(subTaskId);
        subtask.setEndTime(checkEndTime(subtask.getStartTime(), subtask.getDuration()));
        int returnId;
        //   getPrioritizedTasks();
        if (subtask.getStartTime() != null) {
            if (isTimeFree(subtask)) {
                for (Integer epic : epics.keySet()) {
                    if (epic.equals(subtask.getEpicGroup())) {
                        epics.get(epic).getSubTaskGroup().add(subTaskId);
                    }
                }
                subtasks.put(subTaskId, subtask);
                timeTree.add(subtask);
                checkEndTimeEpic(epics.get(subtask.getEpicGroup()));
                returnId = subtask.getIdTask();
            } else {
                System.out.println("Время занято");
                returnId = 0;
            }
        } else {
            for (Integer epic : epics.keySet()) {
                if (epic.equals(subtask.getEpicGroup())) {
                    epics.get(epic).getSubTaskGroup().add(subTaskId);
                }
            }
            subtasks.put(subTaskId, subtask);
            timeTree.add(subtask);
            returnId = subtask.getIdTask();
        }
        return returnId;
    }


    // проверка свободного времени
    private boolean isTimeFree(Task task) {
        boolean result = true;

        if (timeTree.isEmpty()) {
            return true;
        }
        for (Task prioritizedTask : timeTree) {
            if (task.getStartTime().isBefore(prioritizedTask.getStartTime())) {
                if (task.getEndTime().isAfter(prioritizedTask.getStartTime())) {
                    result = false;
                }
            } else if (task.getStartTime().isBefore(prioritizedTask.getEndTime())) {
                result = false;
            }
        }

        return result;
    }


    //расчет endTime у Task и Subtask
    public LocalDateTime checkEndTime(LocalDateTime startTime, Duration duration) {
        if (duration != null) {
            return startTime.plus(duration);
        } else {
            return null;
        }
    }

    //расчет endTime у Epic
    public void checkEndTimeEpic(Epic epic) {
        LocalDateTime epicStartTime = LocalDateTime.parse("01.01.2100 00:00", formatter);
        Duration epicDuration = Duration.ofMinutes(Long.parseLong("0"));
        LocalDateTime epicEndTime;
        int epicId = epic.getIdTask();

        for (Integer subTaskId : subtasks.keySet()) {
            Subtask subTaskData = subtasks.get(subTaskId);
            if (subTaskData.getEpicGroup() == epicId) {

                if (subTaskData.getStartTime() != null && subTaskData.getStartTime().isBefore(epicStartTime)) {
                    epicStartTime = subTaskData.getStartTime();
                }
                if (subTaskData.getDuration() != null) {
                    epicDuration = epicDuration.plus(subTaskData.getDuration());
                }
            }
        }
        epic.setStartTime(epicStartTime);
        epic.setDuration(epicDuration);
        epic.setEndTime(epicStartTime.plus(epicDuration));

    }


    // возвращает отслотированный список
    @Override
    public Set<Task> getPrioritizedTasks() {
        for (Task task : timeTree) {
            if (task.getStartTime() == null) {
                System.out.println("ID " + task.getIdTask() + ", TaskName " + task.getTaskName() + ", Description " + task.getTaskDescription() + ", TaskStatus " +
                        task.getTaskStatus());
            } else {
                System.out.println("ID " + task.getIdTask() + ", TaskName " + task.getTaskName() + ", Description " + task.getTaskDescription() + ", TaskStatus " +
                        task.getTaskStatus() + ", StartTime " + task.getStartTime() + ", EndTime " + task.getEndTime());
            }


        }
        return timeTree;
    }


    //Получение списка всех задач.
    @Override
    public ArrayList<Task> printAllTask() {
        ArrayList<Task> printAllTask = new ArrayList<>();
        System.out.println("Задачи: ");
        for (Integer taskId : tasks.keySet()) {
            Task taskData = tasks.get(taskId);
            if (taskData.getStartTime() == null) {
                System.out.println("ID: " + taskId + " taskName: " + taskData.getTaskName() + ", taskDescription: " +
                        taskData.getTaskDescription() + ", taskStatus: " + taskData.getTaskStatus());
            } else {
                System.out.println("ID: " + taskId + " taskName: " + taskData.getTaskName() + ", taskDescription: " +
                        taskData.getTaskDescription() + ", taskStatus: " + taskData.getTaskStatus() + ", Start time: " +
                        taskData.getStartTime().format(formatter) + ", Продолжительность в мин: " +
                        taskData.getDuration().toMinutes());
            }
            printAllTask.add(taskData);
        }
        for (Integer epicId : epics.keySet()) {
            System.out.println("Эпики: ");
            Epic epicData = epics.get(epicId);
            if (epicData.getStartTime() == null) {
                System.out.println("ID: " + epicId + " taskName: " + epicData.getTaskName() + ", taskDescription: " +
                        epicData.getTaskDescription() + ", taskStatus: " + epicData.getTaskStatus());
            } else {
                System.out.println("ID: " + epicId + " taskName: " + epicData.getTaskName() + ", taskDescription: " +
                        epicData.getTaskDescription() + ", taskStatus: " + epicData.getTaskStatus() +
                        ", Start time: " + epicData.getStartTime().format(formatter) + " Продолжительность в мин: " +
                        epicData.getDuration().toMinutes());
            }
            printAllTask.add(epicData);

            System.out.println("Субзадачи:");
            for (Integer subTaskId : subtasks.keySet()) {
                Subtask subTaskData = subtasks.get(subTaskId);
                if (subTaskData.getEpicGroup() == epicId) {
                    if (subTaskData.getStartTime() == null) {
                        System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                                ", taskDescription: " + subTaskData.getTaskDescription() +
                                ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " +
                                subTaskData.getEpicGroup());
                    } else {
                        System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                                ", taskDescription: " + subTaskData.getTaskDescription() +
                                ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " +
                                subTaskData.getEpicGroup() + " Start time: " + subTaskData.getStartTime().format(formatter)
                                + " Продолжительность в мин: " + subTaskData.getDuration().toMinutes());
                    }

                }
                printAllTask.add(subTaskData);
            }
        }
        return printAllTask;
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
    public Task getTaskById(Integer iDnumber) {
        Task taskById = null;
        for (Integer id : tasks.keySet()) {
            if (id.equals(iDnumber)) {
                Task data = tasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                getTask(data);
                taskById = data;
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = epics.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                getEpic(data);
                taskById = data;
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = subtasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());
                getSubtask(data);
                taskById = data;
            }
        }
        return taskById;
    }


    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateById(int idUpdate, Task updateDataById) {
        System.out.println("Обновление данных по номеру ID:");
        for (Integer id : tasks.keySet()) {
            if (id.equals(idUpdate)) {
                Task data = tasks.get(id);
                data.setIdTask(id);
                data.setTaskName(updateDataById.getTaskName());
                data.setTaskDescription(updateDataById.getTaskDescription());
                data.setTaskStatus(updateDataById.getTaskStatus());

                if (updateDataById.getStartTime() != null && updateDataById.getDuration() != null) {
                    data.setStartTime(updateDataById.getStartTime());
                    data.setDuration(updateDataById.getDuration());

                }
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

                if (updateData.getStartTime() != null && updateData.getDuration() != null) {
                    data.setStartTime(updateData.getStartTime());
                    data.setDuration(updateData.getDuration());
                }
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
                if (updateData.getStartTime() != null && updateData.getDuration() != null) {
                    data.setStartTime(updateData.getStartTime());
                    data.setDuration(updateData.getDuration());
                    data.setEndTime(updateData.getStartTime().plus(updateData.getDuration()));

                }

                statusUpdate();

                checkEndTimeEpic(epics.get(data.getEpicGroup()));

                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());
            }
        }
    }

    //Удаление по идентификатору.
    @Override
    public void removeTaskById(Integer iDnumber) {
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
                ArrayList<Integer> removeId = new ArrayList<>();
                for (Integer idSubTask : subtasks.keySet()) {
                    Subtask dataSubTask = subtasks.get(idSubTask);
                    if (dataSubTask.getEpicGroup() == iDnumber) {
                        removeId.add(idSubTask);
                        inMemoryHistoryManager.remove(dataSubTask.getIdTask());
                    }
                }
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                for (int i : removeId) {
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
                    if (epicData.getSubTaskGroup().get(i).equals(id)) {
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
    public void printEpicSubtask(int epicIdNumber) {
        for (Integer epicId : epics.keySet()) {
            if (epicIdNumber == epicId) {
                Epic epicData = epics.get(epicId);
                if (epicData.getStartTime() == null) {
                    System.out.println("Эпик: ID: " + epicId + ", taskName: " + epicData.getTaskName() +
                            ", taskDescription: " + epicData.getTaskDescription() +
                            ", taskStatus: " + epicData.getTaskStatus());
                } else {
                    System.out.println("Эпик: ID: " + epicId + ", taskName: " + epicData.getTaskName() +
                            ", taskDescription: " + epicData.getTaskDescription() +
                            ", taskStatus: " + epicData.getTaskStatus() +
                            ", Start time: " + epicData.getStartTime().format(formatter) +
                            ", Продолжительность в мин: " + epicData.getDuration().toMinutes());
                }
                System.out.println("Субзадачи:");
                for (Integer subTaskId : subtasks.keySet()) {
                    Subtask subTaskData = subtasks.get(subTaskId);
                    if (subTaskData.getEpicGroup() == epicId) {
                        if (subTaskData.getStartTime() == null) {
                            System.out.println(
                                    "ID: " + subTaskId + ", taskName: " + subTaskData.getTaskName() +
                                            ", taskDescription: " + subTaskData.getTaskDescription() +
                                            ", taskStatus: " + subTaskData.getTaskStatus() +
                                            ", epicGroup: " + subTaskData.getEpicGroup());
                        } else {
                            System.out.println(
                                    "ID: " + subTaskId + ", taskName: " + subTaskData.getTaskName() +
                                            ", taskDescription: " + subTaskData.getTaskDescription() +
                                            ", taskStatus: " + subTaskData.getTaskStatus() +
                                            ", epicGroup: " + subTaskData.getEpicGroup() +
                                            ", Start time: " + subTaskData.getStartTime().format(formatter) +
                                            ", Продолжительность в мин: " + subTaskData.getDuration().toMinutes());
                        }
                    }
                }
            }
        }
    }

    //Управление статусами
    @Override
    public void statusUpdate() {
        ArrayList<String> newDone = new ArrayList<>();
        int countDone = 0;
        int countNew = 0;

        for (Integer idEpic : epics.keySet()) {
            Epic dataEpic = epics.get(idEpic);
            for (Integer idSubtask : subtasks.keySet()) {
                Subtask dataSubtask = subtasks.get(idSubtask);
                if (dataSubtask.getEpicGroup() == idEpic) {
                    if (dataSubtask.getTaskStatus() != null) {
                        if (StatusTask.DONE == dataSubtask.getTaskStatus()) {
                            newDone.add("D");
                        }
                        if (StatusTask.NEW == dataSubtask.getTaskStatus()) {
                            newDone.add("N");
                        }
                    }
                }
            }
            for (String result : newDone) {
                if (result.equals("D")) {
                    countDone++;
                } else {
                    countNew++;
                }
            }

            if (countDone > 0 && countNew == 0) {
                dataEpic.setTaskStatus(StatusTask.DONE);
            } else if (countNew > 0 && countDone == 0) {
                dataEpic.setTaskStatus(StatusTask.NEW);
            } else if (countNew == 0 && countDone == 0) {
                dataEpic.setTaskStatus(dataEpic.getTaskStatus());
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
    public void getTask(Task task) {
        inMemoryHistoryManager.add(task);
    }

    @Override
    public void getEpic(Task task) {
        inMemoryHistoryManager.add(task);
    }

    @Override
    public void getSubtask(Task task) {
        inMemoryHistoryManager.add(task);
    }


    //получение истории
    @Override
    public List<Task> getArrayHistory() {
        return inMemoryHistoryManager.getHistory();
    }


}
