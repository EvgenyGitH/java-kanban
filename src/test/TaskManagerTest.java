package test;

import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagerTest<T extends TaskManager> {
    public T taskManager;
    public Task task;
    public Epic epic2;
    public Subtask subtask3;

    public void setTaskManager() {
    }

    @BeforeEach
    public void setUp() {
        setTaskManager();
        HashMap<Integer, Task> tasks = taskManager.getTasksHashMap();
        HashMap<Integer, Epic> epics = taskManager.getEpicsHashMap();
        HashMap<Integer, Subtask> subtasks = taskManager.getSubtasksHashMap();

        task = new Task("19.02.2023 12:00", "14", "Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        subtask3 = new Subtask("19.02.2023 12:45", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);

    }

    @Test
    public void testSaveTask() {
        taskManager.saveTask(task);
        assertEquals(1, taskManager.getTasksHashMap().size(), "Размер HashMap не соответствует количеству Заданий!");
    }


    @Test
    public void testSaveEpic() {

        taskManager.saveEpic(epic2);
        assertEquals(1, taskManager.getEpicsHashMap().size());
    }

    @Test
    public void testSaveSubtask() {
        taskManager.saveTask(task);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);
        assertEquals(1, taskManager.getSubtasksHashMap().size());

    }

    @Test
    public void testPrintAllTask() {
        int countPrintTask = 0;
        int countPrintEpic = 0;
        int countPrintSubtask = 0;

        taskManager.saveTask(task);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);

        System.out.println("Задачи: ");

        for (Integer taskId : taskManager.getTasksHashMap().keySet()) {
            Task taskData = taskManager.getTasksHashMap().get(taskId);
            System.out.println("ID: " + taskId + " taskName: " + taskData.getTaskName() + ", taskDescription: " +
                    taskData.getTaskDescription() + ", taskStatus: " + taskData.getTaskStatus());
            countPrintTask++;
        }
        for (Integer epicId : taskManager.getEpicsHashMap().keySet()) {
            System.out.println("Эпики: ");
            Epic ecipData = taskManager.getEpicsHashMap().get(epicId);
            System.out.println("ID: " + epicId + " taskName: " + ecipData.getTaskName() + ", taskDescription: " +
                    ecipData.getTaskDescription() + ", taskStatus: " + ecipData.getTaskStatus());
            countPrintEpic++;

            System.out.println("Субзадачи:");
            for (Integer subTaskId : taskManager.getSubtasksHashMap().keySet()) {
                Subtask subTaskData = taskManager.getSubtasksHashMap().get(subTaskId);
                if (subTaskData.getEpicGroup() == epicId) {
                    System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                            ", taskDescription: " + subTaskData.getTaskDescription() +
                            ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " + subTaskData.getEpicGroup());
                    countPrintSubtask++;
                }
            }
        }

        assertEquals(1, countPrintTask, "Ошибка печати");
        assertEquals(1, countPrintEpic, "Ошибка печати");
        assertEquals(1, countPrintSubtask, "Ошибка печати");

    }


    @Test
    public void testRemoveAllTask() {

        taskManager.saveTask(task);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);

        taskManager.removeAllTask();

        assertEquals(0, taskManager.getTasksHashMap().size(), "Задания удалены неполностью!");
        assertEquals(0, taskManager.getEpicsHashMap().size(), "Задания удалены неполностью!");
        assertEquals(0, taskManager.getSubtasksHashMap().size(), "Задания удалены неполностью!");

    }


    @Test
    public void testGetTaskById() {
        taskManager.saveTask(task);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);

        Integer iDnumber = 3;

        for (Integer id : taskManager.getTasksHashMap().keySet()) {
            if (id.equals(iDnumber)) {
                Task data = taskManager.getTasksHashMap().get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                taskManager.getTask(data);

                assertEquals("Позвать гостей", data.getTaskName(), "Не соответствует ID Задания!");
            }
        }
        for (Integer id : taskManager.getEpicsHashMap().keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = taskManager.getEpicsHashMap().get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                taskManager.getEpic(data);

                assertEquals("Приготовить коктейль", data.getTaskName(), "Не соответствует ID Задания!");
            }
        }
        for (Integer id : taskManager.getSubtasksHashMap().keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = taskManager.getSubtasksHashMap().get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());
                taskManager.getSubtask(data);

                assertEquals("Купить Ром/Колу", data.getTaskName(), "Не соответствует ID Задания!");


            }
        }

        taskManager.removeAllTask();

        for (Integer id : taskManager.getTasksHashMap().keySet()) {
            if (id.equals(iDnumber)) {
                Task data = taskManager.getTasksHashMap().get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                taskManager.getTask(data);

                assertEquals("Позвать гостей", data.getTaskName(), "Не соответствует ID Задания!");
            }
        }
        for (Integer id : taskManager.getEpicsHashMap().keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = taskManager.getEpicsHashMap().get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                taskManager.getEpic(data);

                assertEquals("Приготовить коктейль", data.getTaskName(), "Не соответствует ID Задания!");
            }
        }
        for (Integer id : taskManager.getSubtasksHashMap().keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = taskManager.getSubtasksHashMap().get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());
                taskManager.getSubtask(data);

                assertEquals("Купить Ром/Колу", data.getTaskName(), "Не соответствует ID Задания!");

            }
        }
    }


    @Test
    void testUpdateById() {

        taskManager.saveTask(task);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);

        Task taskUpData = new Task("19.02.2023 12:00", "150", "Пригласить ДРУЗЕЙ", "Обзвонить по списку", StatusTask.NEW);
        Epic epicUpData2 = new Epic("Приготовить НАПИТКИ", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtaskUpData3 = new Subtask("19.02.2023 12:00", "150", "Купить ИНГРИДИЕТЫ", "Купить 1 литр", StatusTask.DONE, 2);
        taskManager.updateById(1, taskUpData);
        taskManager.updateById(2, epicUpData2);
        taskManager.updateById(3, subtaskUpData3);

        assertEquals("Пригласить ДРУЗЕЙ", taskManager.getTasksHashMap().get(1).getTaskName(), "Ошибка в обновлении задания! NAME");
        assertEquals("Приготовить НАПИТКИ", taskManager.getEpicsHashMap().get(2).getTaskName(), "Ошибка в обновлении задания! NAME");
        assertEquals(StatusTask.DONE, taskManager.getSubtasksHashMap().get(3).getTaskStatus(), "Ошибка в обновлении задания! STATUS");

        Epic epicUpData2v1 = new Epic("Приготовить__ОШИБКА", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        taskManager.updateById(7, epicUpData2v1);

        assertEquals(1, taskManager.getEpicsHashMap().size(), "Ошибка в обновлении задания! Не соответствует номер ID");
        assertEquals("Приготовить НАПИТКИ", taskManager.getEpicsHashMap().get(2).getTaskName(), "Ошибка в обновлении задания! Не соответствует номер ID");

        taskManager.removeAllTask();
        Epic epicUpData2v2 = new Epic("Приготовить__ОШИБКА", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        taskManager.updateById(7, epicUpData2v2);

        assertEquals(0, taskManager.getEpicsHashMap().size(), "Ошибка в обновлении задания! Не соответствует номер ID");

    }

    @Test
    void testRemoveTaskById() {

        taskManager.saveTask(task);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);

        taskManager.removeTaskById(2);

        assertEquals(0, taskManager.getEpicsHashMap().size(), "задача не удалена");
    }

    @Test
    void testPrintEpicSubtask() {
        taskManager.saveTask(task);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);

        int epicIdNumber = 2;

        for (Integer epicId : taskManager.getEpicsHashMap().keySet()) {
            if (epicIdNumber == epicId) {
                Epic epicData = taskManager.getEpicsHashMap().get(epicId);
                System.out.println("Эпик: ID: " + epicId + " taskName: " + epicData.getTaskName() + ", taskDescription: " +
                        epicData.getTaskDescription() + ", taskStatus: " + epicData.getTaskStatus());
                System.out.println("Субзадачи:");
                for (Integer subTaskId : taskManager.getSubtasksHashMap().keySet()) {
                    Subtask subTaskData = taskManager.getSubtasksHashMap().get(subTaskId);
                    int countPrintSubtask = 0;
                    if (subTaskData.getEpicGroup() == epicId) {
                        System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                                ", taskDescription: " + subTaskData.getTaskDescription() +
                                ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " + subTaskData.getEpicGroup());
                        countPrintSubtask++;
                    }

                    assertEquals(taskManager.getEpicsHashMap().get(2).getSubTaskGroup().size(), countPrintSubtask, "Напечатаны не все субзадчи!");

                }
            }
        }
    }


}
