package test;

import manager.FileBackedTasksManager;
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

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    @Override
    public void setTaskManager() {
        taskManager = new InMemoryTaskManager();
    }

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


*/



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

}