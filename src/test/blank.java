package test;

import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class blank {
}


/*

package test;

        import manager.HistoryManager;
        import manager.Managers;
        import manager.TaskManager;
        import org.junit.jupiter.api.Test;
        import task.Epic;
        import task.StatusTask;
        import task.Subtask;
        import task.Task;

        import java.util.ArrayList;
        import java.util.HashMap;

        import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest <T extends TaskManager> {

    protected T taskManager;
    int id = 1;

*/
/*    public void setTaskManager(T taskManager) {
        this.taskManager = taskManager;
    }*//*


    public void setTaskManager(){
    }


    protected Task task;
    protected Epic epic;
    protected StatusTask statusTask;


    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    //создание ID
    int creatId() {
        return id++;
    }


    //  @Test
    public void saveTask(Task task) {
        task.setTaskStatus(StatusTask.NEW);
        int taskId = creatId();
        task.setIdTask(taskId);
        tasks.put(taskId, task);

        assertEquals(1, tasks.size(), "Размер HashMap не соответствует количеству Заданий!");
    }

    //  @Test
    public void saveEpic(Epic epic) {
        epic.setTaskStatus(StatusTask.NEW);
        int epicId = creatId();
        epic.setIdTask(epicId);
        epics.put(epicId, epic);

        assertEquals(1, epics.size(), "Размер HashMap не соответствует количеству Заданий!");
    }

    //  @Test
    public void saveSubtask(Subtask subtask) {
        subtask.setTaskStatus(StatusTask.NEW);
        int subTaskId = creatId();
        subtask.setIdTask(subTaskId);
        for (Integer epic : epics.keySet()) {
            if (epic.equals(subtask.getEpicGroup())) {
                epics.get(epic).getSubTaskGroup().add(subTaskId); // ???
            }
        }
        subtasks.put(subTaskId, subtask);

        assertEquals(1, subtasks.size(), "Размер HashMap не соответствует количеству Заданий!");
    }

    //   @Test
    public void printAllTask() {
        int countPrintTask = 0;
        int countPrintEpic = 0;
        int countPrintSubtask = 0;

        System.out.println("Задачи: ");
        for (Integer taskId : tasks.keySet()) {
            Task taskData = tasks.get(taskId);
            System.out.println("ID: " + taskId + " taskName: " + taskData.getTaskName() + ", taskDescription: " +
                    taskData.getTaskDescription() + ", taskStatus: " + taskData.getTaskStatus());
            countPrintTask++;
        }
        for (Integer epicId : epics.keySet()) {
            System.out.println("Эпики: ");
            Epic ecipData = epics.get(epicId);
            System.out.println("ID: " + epicId + " taskName: " + ecipData.getTaskName() + ", taskDescription: " +
                    ecipData.getTaskDescription() + ", taskStatus: " + ecipData.getTaskStatus());
            countPrintEpic++;

            System.out.println("Субзадачи:");
            for (Integer subTaskId : subtasks.keySet()) {
                Subtask subTaskData = subtasks.get(subTaskId);
                if (subTaskData.getEpicGroup() == epicId) {
                    System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                            ", taskDescription: " + subTaskData.getTaskDescription() +
                            ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " + subTaskData.getEpicGroup());
                    countPrintSubtask++;
                }
            }
        }

        assertEquals(1, countPrintTask, "Задачи напечатаны неполностью");
        assertEquals(1, countPrintEpic, "Задачи напечатаны неполностью");
        assertEquals(1, countPrintSubtask, "Задачи напечатаны неполностью");
    }


    //  @Test
    void removeAllTask() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        System.out.println("Список задач пуст");

        assertEquals(0, tasks.size(), "Задания удалены неполностью!");
        assertEquals(0, epics.size(), "Задания удалены неполностью!");
        assertEquals(0, subtasks.size(), "Задания удалены неполностью!");

    }


    public void getTaskById(Integer iDnumber) {
        for (Integer id : tasks.keySet()) {
            if (id.equals(iDnumber)) {
                Task data = tasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                getTask(data);

                assertEquals("Позвать гостей", data.getTaskName(), "Не соответствует ID Задания!");
            }
        }
        for (Integer id : epics.keySet()) {
            if (id.equals(iDnumber)) {
                Epic data = epics.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                getEpic(data);

                assertEquals("Приготовить коктейль", data.getTaskName(), "Не соответствует ID Задания!");
            }
        }
        for (Integer id : subtasks.keySet()) {
            if (id.equals(iDnumber)) {
                Subtask data = subtasks.get(id);
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());
                getSubtask(data);

                assertEquals("Купить Ром/Колу", data.getTaskName(), "Не соответствует ID Задания!");
            }
        }
    }


    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.

    public void updateById(int idUpdate, Task updateDataById) {
        System.out.println("Обновление данных по номеру ID:");
        for (Integer id : tasks.keySet()) {
            if (id.equals(idUpdate)) {
                Task data = tasks.get(id);
                data.setIdTask(id);
                data.setTaskName(updateDataById.getTaskName());
                data.setTaskDescription(updateDataById.getTaskDescription());
                data.setTaskStatus(updateDataById.getTaskStatus());

                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());

                assertEquals("Пригласить ДРУЗЕЙ", data.getTaskName(), "Ошибка в обновлении задания!");
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

                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());

                assertEquals("Приготовить НАПИТКИ", data.getTaskName(), "Ошибка в обновлении задания!");
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

                statusUpdate();
                System.out.println("ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus() + " epicGroup: " + data.getEpicGroup());

                assertEquals("Купить ИНГРИДИЕТЫ", data.getTaskName(), "Ошибка в обновлении задания!");
            }
        }
    }

    //Удаление по идентификатору.

    public void removeTaskById(Integer iDnumber) {
        System.out.println("Удаление по номеру ID:" + iDnumber);

        for (Integer id : tasks.keySet()) {
            if (id.equals(iDnumber)) {
                Task data = tasks.get(id);
                System.out.println("Задача удалена: ID: " + id + " taskName: " + data.getTaskName() + ", taskDescription: " +
                        data.getTaskDescription() + ", taskStatus: " + data.getTaskStatus());
                tasks.remove(iDnumber);
                inMemoryHistoryManager.remove(iDnumber);

                assertNull(tasks.get(id),"Задание не удалено!");
                // assertEquals(null, data.getTaskName(), "Задание не удалено!");
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

                    assertNull(subtasks.get(i),"subtasks не удалено!");
                }
                epics.remove(iDnumber);
                inMemoryHistoryManager.remove(iDnumber);

                assertNull(epics.get(id),"Задание не удалено!");

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

                assertNull(subtasks.get(id),"Задание не удалено!");
                break;
            }
        }
    }


    //Получение списка Подзадач Эпика по ID Эпика.

    public void printEpicSubtask(int epicIdNumber) {
        for (Integer epicId : epics.keySet()) {
            if (epicIdNumber == epicId) {
                Epic epicData = epics.get(epicId);
                System.out.println("Эпик: ID: " + epicId + " taskName: " + epicData.getTaskName() + ", taskDescription: " +
                        epicData.getTaskDescription() + ", taskStatus: " + epicData.getTaskStatus());
                System.out.println("Субзадачи:");
                for (Integer subTaskId : subtasks.keySet()) {
                    Subtask subTaskData = subtasks.get(subTaskId);
                    int countPrintSubtask = 0;
                    if (subTaskData.getEpicGroup() == epicId) {
                        System.out.println("ID: " + subTaskId + " taskName: " + subTaskData.getTaskName() +
                                ", taskDescription: " + subTaskData.getTaskDescription() +
                                ", taskStatus: " + subTaskData.getTaskStatus() + " epicGroup: " + subTaskData.getEpicGroup());
                        countPrintSubtask++;
                    }
                    assertEquals(epicData.getSubTaskGroup().size(), countPrintSubtask, "Напечатаны не все субзадчи!");

                }
            }
        }
    }

    //Управление статусами

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
            } else if (countNew ==0 && countDone ==0) {
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

    public void getTask(Task task) {
        inMemoryHistoryManager.add(task);
    }


    public void getEpic(Task task) {
        inMemoryHistoryManager.add(task);


    }


    public void getSubtask(Task task) {
        inMemoryHistoryManager.add(task);
    }


    //получение истории

    public void getArrayHistory() {
        inMemoryHistoryManager.getHistory();
    }

}
*/


/*


package test;

        import manager.InMemoryTaskManager;
        import manager.Managers;
        import manager.TaskManager;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import task.Epic;
        import task.StatusTask;
        import task.Subtask;
        import task.Task;

        import java.util.HashMap;

        import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

/*
   TaskManager taskManager = Managers.getDefault();
  // TaskManagerTest taskManager1 = new InMemoryTaskManagerTest();
   InMemoryTaskManager f = new InMemoryTaskManager();

   InMemoryTaskManager InMemoryTaskManager = new InMemoryTaskManager();
   // HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
   HashMap<Integer, Task> tasks = new HashMap<>();
   HashMap<Integer, Epic> epics = new HashMap<>();
   HashMap<Integer, Subtask> subtasks = new HashMap<>();


   Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
*/
  /*  @BeforeEach
    static void beforeEach(){
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask3 = new Subtask("Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);
    }*/

/*
    @Test
    public void testSaveTask() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("19.02.2023 12:00", "150","Позвать гостей", "Обзвонить по списку", StatusTask.NEW);

        inMemoryTaskManager.saveTask(task);

    }


    @Test
    public void testSaveEpic() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);

        inMemoryTaskManager.saveEpic(epic2);

    }

    @Test
    public void testSaveSubtask() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Subtask subtask3 = new Subtask("19.02.2023 12:00", "150","Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);

        inMemoryTaskManager.saveSubtask(subtask3);

    }

    @Test
    public void testPrintAllTask() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("19.02.2023 12:00", "150","Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask3 = new Subtask("19.02.2023 12:00", "150","Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);
        inMemoryTaskManager.saveTask(task);
        inMemoryTaskManager.saveEpic(epic2);
        inMemoryTaskManager.saveSubtask(subtask3);

        inMemoryTaskManager.printAllTask();

    }




    @Test
    public void testRemoveAllTask() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("19.02.2023 12:00", "150","Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask3 = new Subtask("19.02.2023 12:00", "150","Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);
        inMemoryTaskManager.saveTask(task);
        inMemoryTaskManager.saveEpic(epic2);
        inMemoryTaskManager.saveSubtask(subtask3);

        inMemoryTaskManager.removeAllTask();
    }


    @Test
    public void testGetTaskById(){
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("19.02.2023 12:00", "150","Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask3 = new Subtask("19.02.2023 12:00", "150","Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);
        inMemoryTaskManager.saveTask(task);
        inMemoryTaskManager.saveEpic(epic2);
        inMemoryTaskManager.saveSubtask(subtask3);

        inMemoryTaskManager.getTaskById(3);
    }




    @Test
    void testUpdateById() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("19.02.2023 12:00", "150","Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask3 = new Subtask("19.02.2023 12:00", "150","Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);
        inMemoryTaskManager.saveTask(task);
        inMemoryTaskManager.saveEpic(epic2);
        inMemoryTaskManager.saveSubtask(subtask3);

        Task taskUpData = new Task("19.02.2023 12:00", "150","Пригласить ДРУЗЕЙ", "Обзвонить по списку", StatusTask.NEW);
        Epic epicUpData2 = new Epic("Приготовить НАПИТКИ", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtaskUpData3 = new Subtask("19.02.2023 12:00", "150","Купить ИНГРИДИЕТЫ", "Купить 1 литр", StatusTask.DONE, 2);
        inMemoryTaskManager.updateById(1, taskUpData);
        inMemoryTaskManager.updateById(2, epicUpData2);
        inMemoryTaskManager.updateById(3, subtaskUpData3);

    }

    @Test
    void testRemoveTaskById() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("19.02.2023 12:00", "150","Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask3 = new Subtask("19.02.2023 12:00", "150","Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);
        inMemoryTaskManager.saveTask(task);
        inMemoryTaskManager.saveEpic(epic2);
        inMemoryTaskManager.saveSubtask(subtask3);

        inMemoryTaskManager.removeTaskById(3);

    }

    @Test
    void testPrintEpicSubtask() {
        InMemoryTaskManagerTest inMemoryTaskManager = new InMemoryTaskManagerTest();
        Task task = new Task("19.02.2023 12:00", "150","Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask3 = new Subtask("19.02.2023 12:00", "150","Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 2);

        inMemoryTaskManager.saveTask(task);
        inMemoryTaskManager.saveEpic(epic2);
        inMemoryTaskManager.saveSubtask(subtask3);


        inMemoryTaskManager.printEpicSubtask(2);

    }





  /* Протестирован в EpicTest
   @Test
   void testStatusUpdate() {
   }*/





   /*


   @Test
   void getTask() {
   }



   @Test
   void getEpic() {
   }

   @Test
   void getSubtask() {
   }

   @Test
   void getArrayHistory() {
   }
*/



