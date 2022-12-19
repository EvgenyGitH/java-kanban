public class Main {

    //Тестовый класс

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", "NEW");
        taskManager.saveTask (task1);


        Epic epic1 = new Epic ("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом","NEW");
        Subtask subtask1 = new Subtask( "Купить Ром/Колу", "Купить 1 литр/2 литра", "NEW", 2 );
        Subtask subtask2 = new Subtask( "Приготовить лед", "Воду налить в форму и поставить в морозилку", "NEW", 2 );
        taskManager.saveEpic(epic1);
        taskManager.saveSubtask(subtask1);
        taskManager.saveSubtask(subtask2);

        Epic epic2 = new Epic ("Приготовить мороженое", "Купить ингредиенты в соответствии с рецептом", "NEW");
        Subtask subtask3 = new Subtask( "Купить Сливки/Молоко", "Купить 1 литр/2 литра", "New", 5);
        taskManager.saveEpic(epic2);
        taskManager.saveSubtask(subtask3);

        //печать всех задач
        System.out.println("---1---");
        taskManager.printAllTask();


        //удаление всех задач
        //taskManager.removeAllTask();

        //Получение по идентификатору.
        System.out.println("---2---");
        taskManager.getTaskById(6);


        //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        System.out.println("---3---");
        Epic updateEpic1 = new Epic("Приготовить коктейль Cuba Libre", "Купить ингредиенты в соответствии с рецептом", "NEW");
        Subtask updateSubtask2 = new Subtask( "КУПИТЬ ЛЕД", "Воду налить в форму и поставить в морозилку", "DONE", 2 );
     //   Subtask updateSubtask1 = new Subtask( "Купить Ром/Колу", "Купить 1 литр/2 литра", "DONE", 2);
        taskManager.updateById(2, updateEpic1);
        taskManager.updateById(4, updateSubtask2);
      //  taskManager.updateById(3, updateSubtask1);

       // Subtask updateSubtask3 = new Subtask( "Купить Сливки/Молоко", "Купить 1 литр/2 литра", "DONE", 5);
       // taskManager.updateById(6, updateSubtask3);

        taskManager.printAllTask();

        System.out.println("---D---");
        taskManager.print(epic1.subTaskGroup);



        //Удаление по идентификатору.
        System.out.println("---4---");
        System.out.println("---2---");
        taskManager.getTaskById(2);
        taskManager.print(epic1.subTaskGroup);
        System.out.println("---D---");
        taskManager.print2();
        System.out.println("---Del---");
        taskManager.removeTaskById (3);
        System.out.println("---D---");
        taskManager.print(epic1.subTaskGroup);
        System.out.println("---A---");

        taskManager.print2();
        System.out.println("---D---");
        taskManager.printAllTask();


        //Получение списка всех подзадач определённого эпика
        System.out.println("---5---");
        taskManager.printEpicSubtask(2);


        //Управление статусами
        System.out.println("---6---");
        taskManager.statusUpdate();
        taskManager.printAllTask();


    }
}

