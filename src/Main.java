/*--------------
import server.KVTaskClient;

import com.google.gson.Gson;
import server.KVServer;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        KVTaskClient n =new KVTaskClient("http://localhost:8090");

        Gson gson = new Gson();
       /* Task task1 = new Task( "Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
       // String json = gson.toJson(task1);

        n.put("1", gson.toJson(task1));

        n.load("1");

        System.out.println("-------------------");

        Epic epic1 = new Epic( "Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);

        n.put("2", gson.toJson(epic1));

        n.load("2");*/

/*-----------------------------
        Task task1 = new Task( "Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        n.put("1", gson.toJson(task1));

        Task task2 = new Task( "19.02.2023 12:00","14","Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        n.put("2", gson.toJson(task2));

        Epic epic1 = new Epic( "Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        n.put("3", gson.toJson(epic1));

        n.load("1");
        n.load("2");
        n.load("3");

        Subtask subtask1 = new Subtask("19.02.2023 12:15","14","Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3);
        Subtask subtask2 = new Subtask("19.02.2023 12:30","14","Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3);
       // Subtask subtask3 = new Subtask("19.02.2023 12:45","14","Купить Колу", "Купить 2 литра", StatusTask.NEW, 3);
        n.put("4", gson.toJson(subtask1));
        n.put("5", gson.toJson(subtask2));
      //  n.put("6", gson.toJson(subtask3));


        n.load("4");
        n.load("5");

        n.load("3");



    }
}



/*

Main.java  -  ПОКА НЕ УДАЛИЛ (НА ВСЯКИЙ СЛУЧАЙ)
Рабочий    main   в FileBackedTasksManager

*/

/*

import manager.FileBackedTasksManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;



public class Main {

    //Тестовый класс

    public static void main(String[] args) {


        TaskManager taskManager = Managers.getDefault();
        //  HistoryManager historyManager = Managers.getDefaultHistory(); - реализован в InMemoryTaskManager (стр 21)

        // -TaskManager inMemoryTaskManager = Managers.getDefault();
        // FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();


        //InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        //InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        taskManager.saveTask(task1);
        Task task2 = new Task("Заказать пиццу ", "Позвонить в ресторан", StatusTask.NEW);
        taskManager.saveTask(task2);


        Epic epic1 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask1 = new Subtask("Купить Ром/Колу", "Купить 1 литр", StatusTask.DONE, 3);
        Subtask subtask2 = new Subtask("Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.DONE, 3);
        Subtask subtask3 = new Subtask("Купить Колу", "Купить 2 литра", StatusTask.DONE, 3);
        taskManager.saveEpic(epic1);
        taskManager.saveSubtask(subtask1);
        taskManager.saveSubtask(subtask2);
        taskManager.saveSubtask(subtask3);


        Epic epic2 = new Epic("Приготовить мороженое", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        //  Subtask subtask3 = new Subtask( "Купить Сливки/Молоко", "Купить 1 литр/2 литра", StatusTask.NEW, 5);
        taskManager.saveEpic(epic2);
        //    inMemoryTaskManager.saveSubtask(subtask3);


        //печать всех задач
        System.out.println("---printAllTask---");
        taskManager.printAllTask();


        //удаление всех задач
        //inMemoryTaskManager.removeAllTask();

        //Получение по идентификатору.
        System.out.println("--------------------------------");
        System.out.println("---ТЗ-5 -добавление запросов----");
        System.out.println("--------------------------------");
/*
        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
        fileBackedTasksManager.getTaskById(1); // 1 - 2, 2 - 3, 3 - 1 , 4 - 2, 5 - 1 , 6 - 3
      //  inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
        fileBackedTasksManager.getTaskById(3);
      // inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
        fileBackedTasksManager.getTaskById(2);
        //inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
        fileBackedTasksManager.getTaskById(6);
        //inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
        fileBackedTasksManager.getTaskById(5);
        //inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
        fileBackedTasksManager.getTaskById(2);
       // inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
        fileBackedTasksManager.getTaskById(6);
      //  inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");

        System.out.println("---ТЗ-5-удаление--");
        fileBackedTasksManager.removeTaskById (3);
     //   inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");
     //   inMemoryTaskManager.getTaskById(5);
       // inMemoryTaskManager.getTaskById(6);
       // inMemoryTaskManager.getTaskById(4);
       // inMemoryTaskManager.getTaskById(6);

        System.out.println("---ТЗ-5-Итог--");
     //   inMemoryHistoryManager.getHistory();
        fileBackedTasksManager.getArrayHistory();


        System.out.println("---ТЗ-5---");
*/


/*



        //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        System.out.println("---3---");
        Epic updateEpic1 = new Epic("Приготовить коктейль Cuba Libre", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask updateSubtask2 = new Subtask( "КУПИТЬ ЛЕД", "Воду налить в форму и поставить в морозилку", StatusTask.DONE, 2 );
        Subtask updateSubtask1 = new Subtask( "Купить Ром/Колу", "Купить 1 литр/2 литра", StatusTask.DONE, 2);
        inMemoryTaskManager.updateById(2, updateEpic1);
        inMemoryTaskManager.updateById(4, updateSubtask2);
 //       inMemoryTaskManager.updateById(3, updateSubtask1);

      //  task.Subtask updateSubtask3 = new task.Subtask( "Купить Сливки/Молоко", "Купить 1 литр/2 литра", task.StatusTask.DONE, 5);
       // inMemoryTaskManager.updateById(6, updateSubtask3);

        inMemoryTaskManager.printAllTask();

        System.out.println("---D---");
        inMemoryTaskManager.print(epic1.getSubTaskGroup());



 /*       //Удаление по идентификатору.
        System.out.println("---4---");
        System.out.println("---2---");
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.print(epic1.getSubTaskGroup());
        System.out.println("---D---");
        inMemoryTaskManager.print2();
        System.out.println("---Del---");
        inMemoryTaskManager.removeTaskById (3);
        System.out.println("---D---");
        inMemoryTaskManager.print(epic1.getSubTaskGroup());
        System.out.println("---A---");

        inMemoryTaskManager.print2();
        System.out.println("---D---");
        inMemoryTaskManager.printAllTask();


        //Получение списка всех подзадач определённого эпика
        System.out.println("---5---");
        inMemoryTaskManager.printEpicSubtask(2);


        //Управление статусами
        System.out.println("---6---");
        inMemoryTaskManager.statusUpdate();
        inMemoryTaskManager.printAllTask();
*/
/*




    }
}



*/

   // }}