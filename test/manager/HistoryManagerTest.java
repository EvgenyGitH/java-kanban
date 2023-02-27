package manager;

import manager.FileBackedTasksManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryManagerTest {

    @Test
    void checkHistoryBeforeAdd() { //testAdd() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("src/resources/backup.csv"));
        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task1);
        Task task2 = new Task("19.02.2023 12:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task2);
        Epic epic1 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask1 = new Subtask("19.02.2023 12:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3);
        Subtask subtask2 = new Subtask("19.02.2023 12:30", "14", "Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3);
        Subtask subtask3 = new Subtask("19.02.2023 12:45", "14", "Купить Колу", "Купить 2 литра", StatusTask.NEW, 3);
        fileBackedTasksManager.saveEpic(epic1);
        fileBackedTasksManager.saveSubtask(subtask1);
        fileBackedTasksManager.saveSubtask(subtask2);
        fileBackedTasksManager.saveSubtask(subtask3);
        Epic epic2 = new Epic("Приготовить мороженое", "Купить ингредиенты", StatusTask.NEW);
        fileBackedTasksManager.saveEpic(epic2);

        assertEquals(0, inMemoryHistoryManager.getHistoryMap().size(), "История не пустая! ");


    }
    @Test
    void checkHistoryAfterAdd() { //testAdd() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("src/resources/backup.csv"));
        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task1);
        Task task2 = new Task("19.02.2023 12:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task2);
        Epic epic1 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask1 = new Subtask("19.02.2023 12:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3);
        Subtask subtask2 = new Subtask("19.02.2023 12:30", "14", "Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3);
        Subtask subtask3 = new Subtask("19.02.2023 12:45", "14", "Купить Колу", "Купить 2 литра", StatusTask.NEW, 3);
        fileBackedTasksManager.saveEpic(epic1);
        fileBackedTasksManager.saveSubtask(subtask1);
        fileBackedTasksManager.saveSubtask(subtask2);
        fileBackedTasksManager.saveSubtask(subtask3);
        Epic epic2 = new Epic("Приготовить мороженое", "Купить ингредиенты", StatusTask.NEW);
        fileBackedTasksManager.saveEpic(epic2);

        inMemoryHistoryManager.add(task2);

        assertEquals(1, inMemoryHistoryManager.getHistoryMap().size(), "История не сохранена! ");
    }

    @Test
    void checkHistoryAfterDoubleAddOneTask(){
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("src/resources/backup.csv"));
        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task1);
        Task task2 = new Task("19.02.2023 12:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task2);
        Epic epic1 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask1 = new Subtask("19.02.2023 12:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3);
        Subtask subtask2 = new Subtask("19.02.2023 12:30", "14", "Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3);
        Subtask subtask3 = new Subtask("19.02.2023 12:45", "14", "Купить Колу", "Купить 2 литра", StatusTask.NEW, 3);
        fileBackedTasksManager.saveEpic(epic1);
        fileBackedTasksManager.saveSubtask(subtask1);
        fileBackedTasksManager.saveSubtask(subtask2);
        fileBackedTasksManager.saveSubtask(subtask3);
        Epic epic2 = new Epic("Приготовить мороженое", "Купить ингредиенты", StatusTask.NEW);
        fileBackedTasksManager.saveEpic(epic2);

        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task2);

        assertEquals(1, inMemoryHistoryManager.getHistoryMap().size(), "Дублирование записи в История!");
    }


    @Test
    public void testRemove() {

        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("src/resources/backup.csv"));
        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task1);
        Task task2 = new Task("19.02.2023 12:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task2);

        Epic epic1 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask1 = new Subtask("19.02.2023 12:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3);
        Subtask subtask2 = new Subtask("19.02.2023 12:30", "14", "Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3);
        Subtask subtask3 = new Subtask("19.02.2023 12:45", "14", "Купить Колу", "Купить 2 литра", StatusTask.NEW, 3);
        fileBackedTasksManager.saveEpic(epic1);
        fileBackedTasksManager.saveSubtask(subtask1);
        fileBackedTasksManager.saveSubtask(subtask2);
        fileBackedTasksManager.saveSubtask(subtask3);
        Epic epic2 = new Epic("Приготовить мороженое", "Купить ингредиенты", StatusTask.NEW);
        fileBackedTasksManager.saveEpic(epic2);

        inMemoryHistoryManager.remove(2);

        assertEquals(0, inMemoryHistoryManager.getTasks().size(), "История пустая ошибка удаления!");

        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(epic1);
        inMemoryHistoryManager.add(subtask1);
        inMemoryHistoryManager.add(subtask2);
        inMemoryHistoryManager.add(subtask3);
        inMemoryHistoryManager.add(epic2);

        inMemoryHistoryManager.remove(1);
        assertEquals(6, inMemoryHistoryManager.getTasks().size(), "Ошибка удаления из Истории! начало");

        inMemoryHistoryManager.remove(3);
        assertEquals(5, inMemoryHistoryManager.getTasks().size(), "Ошибка удаления из Истории! середина");

        inMemoryHistoryManager.remove(7);
        assertEquals(4, inMemoryHistoryManager.getTasks().size(), "Ошибка удаления из Истории! конец");

    }

    @Test
    public void getHistoryAndGetTasks() {

        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("src/resources/backup.csv"));
        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task1);
        Task task2 = new Task("19.02.2023 12:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task2);

        Epic epic1 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask1 = new Subtask("19.02.2023 12:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 3);
        Subtask subtask2 = new Subtask("19.02.2023 12:30", "14", "Приготовить лед", "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 3);
        Subtask subtask3 = new Subtask("19.02.2023 12:45", "14", "Купить Колу", "Купить 2 литра", StatusTask.NEW, 3);
        fileBackedTasksManager.saveEpic(epic1);
        fileBackedTasksManager.saveSubtask(subtask1);
        fileBackedTasksManager.saveSubtask(subtask2);
        fileBackedTasksManager.saveSubtask(subtask3);

        Epic epic2 = new Epic("Приготовить мороженое", "Купить ингредиенты", StatusTask.NEW);
        fileBackedTasksManager.saveEpic(epic2);

        assertEquals(0, inMemoryHistoryManager.getHistory().size(), "История пустая, ошибка вызова!");
        assertEquals(0, inMemoryHistoryManager.getTasks().size(), "История пустая, ошибка вызова!");

        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(subtask2);

        assertEquals(3, inMemoryHistoryManager.getHistory().size(), "Ошибка сохранения Истории!");
        assertEquals(3, inMemoryHistoryManager.getTasks().size(), "Ошибка сохранения Истории!");

    }

}