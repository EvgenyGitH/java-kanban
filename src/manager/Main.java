package manager;

import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;



public class Main {

    //Тестовый класс

    public static void main(String[] args) {

      //  TaskManager taskManager = Managers.getDefault();
      //  HistoryManager historyManager = Managers.getDefaultHistory(); - реализован в InMemoryTaskManager (стр 21)

        TaskManager inMemoryTaskManager = Managers.getDefault();

      //InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
      //InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        inMemoryTaskManager.saveTask (task1);
        Task task2 = new Task("Заказать пиццу ", "Позвонить в ресторан", StatusTask.NEW);
        inMemoryTaskManager.saveTask (task2);


        Epic epic1 = new Epic ("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом",StatusTask.NEW);
        Subtask subtask1 = new Subtask( "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3 );
        Subtask subtask2 = new Subtask( "Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3 );
        Subtask subtask3 = new Subtask( "Купить Колу", "Купить 2 литра", StatusTask.NEW, 3 );
        inMemoryTaskManager.saveEpic(epic1);
        inMemoryTaskManager.saveSubtask(subtask1);
        inMemoryTaskManager.saveSubtask(subtask2);
        inMemoryTaskManager.saveSubtask(subtask3);



        Epic epic2 = new Epic ("Приготовить мороженое", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
      //  Subtask subtask3 = new Subtask( "Купить Сливки/Молоко", "Купить 1 литр/2 литра", StatusTask.NEW, 5);
        inMemoryTaskManager.saveEpic(epic2);
    //    inMemoryTaskManager.saveSubtask(subtask3);



        //печать всех задач
        System.out.println("---printAllTask---");
        inMemoryTaskManager.printAllTask();


        //удаление всех задач
        //inMemoryTaskManager.removeAllTask();

        //Получение по идентификатору.
        System.out.println("--------------------------------");
        System.out.println("---ТЗ-5 -добавление запросов----");
        System.out.println("--------------------------------");

        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
        inMemoryTaskManager.getTaskById(1); // 1 - 2, 2 - 3, 3 - 1 , 4 - 2, 5 - 1 , 6 - 3
      //  inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
        inMemoryTaskManager.getTaskById(3);
      // inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
        inMemoryTaskManager.getTaskById(2);
        //inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
        inMemoryTaskManager.getTaskById(6);
        //inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
        inMemoryTaskManager.getTaskById(5);
        //inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
        inMemoryTaskManager.getTaskById(2);
       // inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
        inMemoryTaskManager.getTaskById(6);
      //  inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");

        System.out.println("---ТЗ-5-удаление--");
        inMemoryTaskManager.removeTaskById (3);
     //   inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();
        System.out.println("-------");
     //   inMemoryTaskManager.getTaskById(5);
       // inMemoryTaskManager.getTaskById(6);
       // inMemoryTaskManager.getTaskById(4);
       // inMemoryTaskManager.getTaskById(6);

        System.out.println("---ТЗ-5-Итог--");
     //   inMemoryHistoryManager.getHistory();
        inMemoryTaskManager.getArrayHistory();


        System.out.println("---ТЗ-5---");



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

    }
}

