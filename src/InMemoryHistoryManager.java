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
            System.out.println("Номер ID в списке: " + idPrint.idTask + " " +  idPrint.taskName + " " + idPrint.taskDescription );
        }
        return historyTask;
    }

}


/*

-----------------
HistoryManager historyManager = Managers.getDefaultHistory();
Это и называется "обращается через интерфейс HistoryManager"

это значит, что менеджер тасков будет общаться с менеджером истории через методы, объявленные в интерфесе МенеджерИстории.
То есть через add() getHistory(). А "использует реализацию, которую возвращает метод getDefaultHistory()"
значит как раз использование объекта, который возвращает статический метод из первого сообщения
-----------------

Где пригодится тип списка просмотренных задач
Для списка просмотренных задач нужен тип Task. Метод getHistory() должен возвращать список именно такого типа.
В итоге он будет выглядеть так — List<Task> getHistory().

Создайте отдельный интерфейс для управления историей просмотров — HistoryManager. У него будет два метода.
Первый add(Task task) должен помечать задачи как просмотренные, а второй getHistory() — возвращать их список.
Объявите класс InMemoryHistoryManager и перенесите в него часть кода для работы с историей
из  класса InMemoryTaskManager.  Новый класс InMemoryHistoryManager должен реализовывать интерфейс HistoryManager. */


