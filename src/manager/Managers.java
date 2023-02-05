package manager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}


/*
Утилитарный класс
Со временем в приложении трекера появится несколько реализаций интерфейса manager.TaskManager. Чтобы не зависеть от реализации,
создайте утилитарный класс manager.Managers. На нём будет лежать вся ответственность за создание менеджера задач.
То есть manager.Managers должен сам подбирать нужную реализацию manager.TaskManager и возвращать объект правильного типа.
У manager.Managers будет метод getDefault(). При этом вызывающему неизвестен конкретный класс, только то, что объект,
который возвращает getDefault(), реализует интерфейс manager.TaskManager.

Подсказка про getDefault()
Метод getDefault() будет без параметров. Он должен возвращать объект-менеджер, поэтому типом его возвращаемого значения будет manager.TaskManager.

static manager.HistoryManager getDefaultHistory(){} // Он должен возвращать объект manager.InMemoryHistoryManager — историю просмотров.

Добавьте в служебный класс manager.Managers статический метод manager.HistoryManager getDefaultHistory().
Он должен возвращать объект manager.InMemoryHistoryManager — историю просмотров.
Проверьте, что теперь manager.InMemoryTaskManager обращается к менеджеру истории
через интерфейс manager.HistoryManager и использует реализацию, которую возвращает метод getDefaultHistory().

Проверьте, что теперь manager.InMemoryTaskManager обращается к менеджеру истории через интерфейс manager.HistoryManager
и использует реализацию, которую возвращает метод getDefaultHistory().
*/
