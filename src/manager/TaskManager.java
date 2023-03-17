package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    HashMap<Integer, Task> getTasksHashMap();
    HashMap<Integer, Epic> getEpicsHashMap();
    HashMap<Integer, Subtask> getSubtasksHashMap();
    void saveTask(Task task);


    void saveEpic(Epic epic);

    void saveSubtask(Subtask subtask);

    Set getPrioritizedTasks();

    //Получение списка всех задач.
    void printAllTask();

    //Удаление всех задач.
    void removeAllTask();

    //Получение по идентификатору.
    Task getTaskById(Integer iDnumber);
    //Создание. Сам объект должен передаваться в качестве параметра.


    //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateById(int idUpdate, Task updateDataById);

    //Удаление по идентификатору.
    void removeTaskById(Integer iDnumber);

    //Получение списка всех подзадач определённого эпика.
    void printEpicSubtask(int epicIdNumber);

    //Управление статусами осуществляется по следующему правилу
    void statusUpdate();


    //отметка о просмотре
    void getTask(Task task);

    void getEpic(Task task);

    void getSubtask(Task task);


    //получение Истории
    List<Task> getArrayHistory();


}




/*


Менеджер
        Кроме классов для описания задач, вам нужно реализовать класс для объекта-менеджера. Он будет запускаться на старте программы и управлять всеми задачами. В нём должны быть реализованы следующие функции:

        Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
        Методы для каждого из типа задач(Задача/Эпик/Подзадача):
        Получение списка всех задач.
        Удаление всех задач.
        Получение по идентификатору.
        Создание. Сам объект должен передаваться в качестве параметра.
        Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        Удаление по идентификатору.
        Дополнительные методы:
        Получение списка всех подзадач определённого эпика.
        Управление статусами осуществляется по следующему правилу:
        Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
        Для эпиков:
        если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
        если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
        во всех остальных случаях статус должен быть IN_PROGRESS*/
