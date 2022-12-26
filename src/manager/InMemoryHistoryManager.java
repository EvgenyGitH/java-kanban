package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    int historyListLimit = 10;

    static ArrayList<Integer> historyList = new ArrayList<>();
    static List<Task> historyTask = new ArrayList<>();

    public InMemoryHistoryManager() {
        this.historyListLimit = historyListLimit;
    }

    public void checkListLimit(int historyListLimit ){
        if (historyTask.size()>=historyListLimit){
            historyTask.remove(0);
            historyList.remove(0);
        }
    }


    @Override
    public void add(Task task){
        historyTask.add(task);

    }

    @Override
    public  List<Task> getHistory(){
        for (int i = 0; i < historyTask.size(); i++) {
            Task idPrint = historyTask.get(i);
            System.out.println("Список запросов: " + historyList);
            System.out.println("Номер ID в списке: " + idPrint.getIdTask() + " " +  idPrint.getTaskName() + " " + idPrint.getTaskDescription());
        }
        return historyTask;
    }

}


/*



Где пригодится тип списка просмотренных задач
Для списка просмотренных задач нужен тип task.Task. Метод getHistory() должен возвращать список именно такого типа.
В итоге он будет выглядеть так — List<task.Task> getHistory().

Создайте отдельный интерфейс для управления историей просмотров — manager.HistoryManager. У него будет два метода.
Первый add(task.Task task) должен помечать задачи как просмотренные, а второй getHistory() — возвращать их список.
Объявите класс manager.InMemoryHistoryManager и перенесите в него часть кода для работы с историей
из  класса manager.InMemoryTaskManager.  Новый класс manager.InMemoryHistoryManager должен реализовывать интерфейс manager.HistoryManager. */


