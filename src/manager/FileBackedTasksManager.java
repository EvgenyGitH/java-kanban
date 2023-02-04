package manager;

import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    File file;
    public FileBackedTasksManager(File file) {
        this.file = file;
    }


    public static void main(String[] args) throws IOException {

        File file = new File("src/resources/backup.csv");
        TaskManager fileBackedTasksManager = new FileBackedTasksManager(file);

// ПРОВЕРКА: восстановления из файла тасков, эпиков, сабтасков, истории (снять // , закомментить Запись в файл /*...*/  )
        loadFromFile(file);
// ПРОВЕРКА: восстановления из файла тасков, эпиков, сабтасков, истории (снять // , закомментить Запись в файл /*...*/  )



// ПРОВЕРКА: запись в файл тасков, эпиков, сабтасков (снять /*  ...  */ , закомментить Восстановление из файл // )
/*
        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        fileBackedTasksManager.saveTask (task1);
        Task task2 = new Task("Заказать пиццу ", "Позвонить в ресторан", StatusTask.NEW);
        fileBackedTasksManager.saveTask (task2);

        Epic epic1 = new Epic ("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом",StatusTask.NEW);
        Subtask subtask1 = new Subtask( "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3 );
        Subtask subtask2 = new Subtask( "Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3 );
        Subtask subtask3 = new Subtask( "Купить Колу", "Купить 2 литра", StatusTask.NEW, 3 );
        fileBackedTasksManager.saveEpic(epic1);
        fileBackedTasksManager.saveSubtask(subtask1);
        fileBackedTasksManager.saveSubtask(subtask2);
        fileBackedTasksManager.saveSubtask(subtask3);

        Epic epic2 = new Epic ("Приготовить мороженое", "Купить ингредиенты", StatusTask.NEW);
        //  Subtask subtask3 = new Subtask( "Купить Сливки/Молоко", "Купить 1 литр/2 литра", StatusTask.NEW, 5);
        fileBackedTasksManager.saveEpic(epic2);
        //    inMemoryTaskManager.saveSubtask(subtask3);

        //печать всех задач
        System.out.println("---printAllTask---");
        fileBackedTasksManager.printAllTask();

        //Получение по идентификатору.
        System.out.println("--------------------------------");
        System.out.println("---ТЗ-5 -добавление запросов----");
        System.out.println("--------------------------------");

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

    //    System.out.println("---ТЗ-5-удаление--");
    //    fileBackedTasksManager.removeTaskById (3);

    //    fileBackedTasksManager.getArrayHistory();
        System.out.println("-------");


        System.out.println("---ТЗ-6-Итог--");
        fileBackedTasksManager.getArrayHistory();
        System.out.println("---ТЗ-6---");
*/
// ПРОВЕРКА: запись в файл тасков, эпиков, сабтасков (снять /*  ...  */ , закомментить Восстановление из файл // )


    }


    //сохранение в файл
    private void save() {
        try (Writer fileWriter = new FileWriter("src/resources/backup.csv")) {
            fileWriter.write("id,type,name,description,status,epic \n");

                for (Integer id : tasks.keySet()) {
                    Task dataForLoad = tasks.get(id);
                    fileWriter.write(toString(dataForLoad));
                }

                for (Integer id : epics.keySet()) {
                    Epic dataForLoad = epics.get(id);
                    fileWriter.write(toString(dataForLoad));
                }

                for (Integer id : subtasks.keySet()) {
                    Subtask dataForLoad = subtasks.get(id);
                    fileWriter.write(toString(dataForLoad));
                }

            fileWriter.write("\n");

            if (historyToString(inMemoryHistoryManager) != ""){
                fileWriter.write(historyToString(inMemoryHistoryManager));
            }

        }catch (ManagerSaveException e){                        // Просьба проверить все ли корректно у меня с исключениями ? или должно быть как-то по другому ?
            throw new ManagerSaveException("Ошибка записи");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //сохранения менеджера истории из CSV
    static String historyToString(HistoryManager manager){
        List<Task> historyArrayID = manager.getTasks();
        String listHistoryId = "";
        for (Task taskIdPrint : historyArrayID) {
            listHistoryId = listHistoryId + taskIdPrint.getIdTask() +  ",";
        }
        return listHistoryId;
    }


    //восстановления менеджера истории из CSV
    static List<Integer> historyFromString(String value){
        List<Integer> idForHistory = new ArrayList<>();
        String[] split = value.split(",");

        for (int i = 0; i < split.length; i++) {
            idForHistory.add( Integer.parseInt(split[i]));
        }
        return idForHistory;
    }


    //восстановление Task из String
    public Task fromString(String value){
        Task newTask = null;
        String[] split = value.split(",");  //id,type,name,description,status,epic

        int idTask = Integer.parseInt(split[0]);
        TypeTask typeTask = TypeTask.valueOf(split[1]);
        String taskName  = split[2];
        String taskDescription  = split[3];
        StatusTask taskStatus  = StatusTask.valueOf(split[4]);

        int epicGroup = 0;
        if (typeTask == TypeTask.SUBTASK){
            epicGroup = Integer.parseInt(split[5]);
        }

        switch (typeTask){
            case TASK:
                newTask = new Task(taskName, taskDescription, taskStatus );
                break;
            case EPIC:
                newTask = new Epic(taskName, taskDescription, taskStatus );
                break;
            case SUBTASK:
                newTask = new Subtask(taskName, taskDescription, taskStatus, epicGroup);
                break;
        }
        newTask.setIdTask(idTask);
        return newTask;

    }


    //восстановление данных из файла
    static FileBackedTasksManager  loadFromFile(File file) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        try (Reader fileReader = new FileReader(file); BufferedReader br = new BufferedReader(fileReader)){
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("TASK") || line.contains("EPIC") ||line.contains("SUBTASK")) {
                    Task newTask = manager.fromString(line);
                    int idTask = newTask.getIdTask();
                    if (newTask.getClass() == Task.class) {
                        manager.tasks.put(idTask, newTask);
                    } else if (newTask.getClass() == Epic.class) {
                        manager.epics.put(idTask, (Epic) newTask);
                    } else {
                        manager.subtasks.put(idTask, (Subtask) newTask);
                    }
                }else if (!line.isBlank() && (!line.startsWith("id"))){
                    List<Integer> idForHistory =  historyFromString(line);
                    for (Integer integer : idForHistory) {
                        manager.getTaskById(integer);
                    }
                }
            }
        }

//ДЛЯ ПРОВЕРКИ: печать всех задач + история загруженная из файла
        System.out.println("---printAllTask---");
        manager.printAllTask();
        System.out.println("---ТЗ-6-История--");
        manager.getArrayHistory();
        System.out.println("---ТЗ-6-История--");
//ДЛЯ ПРОВЕРКИ: печать всех задач + история загруженная из файла

        return manager;
    }


    //сохранение Таска toString
    String toString(Task task){
        return task.getIdTask() + "," + TypeTask.TASK + "," + task.getTaskName()  + "," +
                task.getTaskDescription()  + "," + task.getTaskStatus() + ",\n" ;
    }
    //сохранение Таска toString
    String toString(Epic task){
        return task.getIdTask() + "," + TypeTask.EPIC + "," + task.getTaskName()  + "," +
                task.getTaskDescription()  + "," + task.getTaskStatus() + ",\n" ;
    }
    //сохранение СабТаска toString
    String toString(Subtask task){
        return task.getIdTask() + "," + TypeTask.SUBTASK + "," + task.getTaskName()  + "," +
                task.getTaskDescription()  + "," + task.getTaskStatus() + "," + task.getEpicGroup() + "\n";
    }





    @Override
    public void saveTask (Task task) throws IOException { // Просьба проверить все ли корректно у меня с исключениями ? или должно быть как-то по другому ?
        super.saveTask(task);
        save();
    }

    @Override
    public void saveEpic (Epic epic) throws IOException { // Просьба проверить все ли корректно у меня с исключениями ? или должно быть как-то по другому ?
        super.saveEpic(epic);
        save();
    }

    @Override
    public void saveSubtask (Subtask subtask) throws IOException { // Просьба проверить все ли корректно у меня с исключениями ? или должно быть как-то по другому ?
        super.saveSubtask(subtask);
        save();
    }

    @Override
    public void removeAllTask(){
        super.removeAllTask();
        save();
    }

    @Override
    public void updateById (int idUpdate, Object updateDataById) {
        super.updateById(idUpdate, updateDataById);
        save();
    }

    @Override
    public void removeTaskById (Integer iDnumber){
        super.removeTaskById(iDnumber);
        save();
    }

    @Override
    public void statusUpdate(){
        super.statusUpdate();
        save();
    }


    @Override
    public void getTask(Task task){
        super.getTask(task);
        save();
    }

    @Override
    public void getEpic(Task task){
        super.getEpic(task);
        save();
    }

    @Override
    public void getSubtask(Task task){
        super.getSubtask(task);
        save();
    }

}








/*Для этого создайте метод static void main(String[] args) в классе FileBackedTasksManager и реализуйте небольшой сценарий:

    Заведите несколько разных задач, эпиков и подзадач.
    Запросите некоторые из них, чтобы заполнилась история просмотра.
    Создайте новый FileBackedTasksManager менеджер из этого же файла.
    Проверьте, что история просмотра восстановилась верно и все задачи, эпики, подзадачи, которые были в старом, есть в новом менеджере.


 */
