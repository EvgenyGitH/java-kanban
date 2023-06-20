package manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import server.KVTaskClient;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    private KVTaskClient taskClient;
    private KVServer kvServer;
    @BeforeEach
    public void setUp() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        taskManager = new HttpTaskManager("http://localhost:8078");

    }

    @AfterEach
    public void afterEach() {
        kvServer.stop();
    }

    @Test
    public void testSaveManager() throws IOException, InterruptedException {
        taskClient = taskManager.getTaskClient();

        Task task = new Task("20.02.2023 12:00", "14", "Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        taskManager.saveTask(task);
        Epic epic = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        taskManager.saveEpic(epic);
        Subtask subtask = new Subtask("23.02.2023 12:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, 2);
        taskManager.saveSubtask(subtask);

        String savedTasks = taskClient.load("tasks");
        String savedEpics = taskClient.load("epics");
        String savedSubtasks = taskClient.load("subtasks");
        String savedHistory = taskClient.load("history");

        assertNotEquals(0, savedTasks.length(), "Задачи не сохранены на сервер.");
        assertNotEquals(0, savedEpics.length(), "Задачи не сохранены на сервер.");
        assertNotEquals(0, savedSubtasks.length(), "Задачи не сохранены на сервер.");
        assertNotEquals(0, savedHistory.length(), "Задачи не сохранены на сервер.");
    }

    @Test
    public void testLoadFromServer() throws IOException, InterruptedException {

        Task task1 = new Task("20.02.2023 12:00", "14", "Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        taskManager.saveTask(task1);
        Epic epic2 = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        taskManager.saveEpic(epic2);
        Subtask subtask3 = new Subtask("23.02.2023 12:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, epic2.getIdTask());
        taskManager.saveSubtask(subtask3);

        HttpTaskManager loadManager = HttpTaskManager.loadFromServer("http://localhost:8078");
        taskClient = loadManager.getTaskClient();

        String savedTasks = taskClient.load("tasks");
        String savedEpics = taskClient.load("epics");
        String savedSubtasks = taskClient.load("subtasks");
        String savedHistory = taskClient.load("history");

        assertNotEquals(0, savedTasks.length(), "Задачи не сохранены на сервер.");
        assertNotEquals(0, savedEpics.length(), "Задачи не сохранены на сервер.");
        assertNotEquals(0, savedSubtasks.length(), "Задачи не сохранены на сервер.");
        assertNotEquals(0, savedHistory.length(), "Задачи не сохранены на сервер.");
    }

}
