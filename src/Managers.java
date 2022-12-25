import java.util.ArrayList;

public class Managers  {
    private static TaskManager taskManager ;

    static TaskManager getDefault(){
        return taskManager;
    }

    static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

}


/*
Утилитарный класс
Со временем в приложении трекера появится несколько реализаций интерфейса TaskManager. Чтобы не зависеть от реализации,
создайте утилитарный класс Managers. На нём будет лежать вся ответственность за создание менеджера задач.
То есть Managers должен сам подбирать нужную реализацию TaskManager и возвращать объект правильного типа.
У Managers будет метод getDefault(). При этом вызывающему неизвестен конкретный класс, только то, что объект,
который возвращает getDefault(), реализует интерфейс TaskManager.

Подсказка про getDefault()
Метод getDefault() будет без параметров. Он должен возвращать объект-менеджер, поэтому типом его возвращаемого значения будет TaskManager.

static HistoryManager getDefaultHistory(){} // Он должен возвращать объект InMemoryHistoryManager — историю просмотров.

Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory().
Он должен возвращать объект InMemoryHistoryManager — историю просмотров.
Проверьте, что теперь InMemoryTaskManager обращается к менеджеру истории
через интерфейс HistoryManager и использует реализацию, которую возвращает метод getDefaultHistory().

Проверьте, что теперь InMemoryTaskManager обращается к менеджеру истории через интерфейс HistoryManager
и использует реализацию, которую возвращает метод getDefaultHistory().
*/
