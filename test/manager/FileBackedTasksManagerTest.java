package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.io.*;

import org.junit.jupiter.api.function.Executable;
import static manager.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    protected File file = new File("src/resources/backup.csv");

    @Override
    public void setTaskManager() {
        taskManager = new FileBackedTasksManager(new File("src/resources/backup.csv"));
    }


    @Test
    public void testSave() throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
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

        String line = "";
        try (FileReader reader = new FileReader("src/resources/backup.csv"); BufferedReader br = new BufferedReader(reader)){
            line = br.readLine();
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        assertEquals("id,type,name,description,status,epic, startTime, duration ", line);
    }

    @Test
    public void testSaveException() {
        File wrongFile = new File("src/resourcesWRONG/backup.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(wrongFile);
        Task task1 = new Task("Позвать гостей", "Обзвонить по списку", StatusTask.NEW);

        final ManagerSaveException exp = assertThrows(
                ManagerSaveException.class,
                () -> fileBackedTasksManager.saveTask(task1)
        );

        assertEquals("Ошибка записи", exp.getMessage());
    }




    @Test
    public void testLoadFromFile() throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        Task task2 = new Task("19.02.2023 12:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        fileBackedTasksManager.saveTask(task2);

        FileBackedTasksManager newFileBackedTasksManager = loadFromFile(file);

        assertEquals(fileBackedTasksManager.getTasksHashMap().size(), newFileBackedTasksManager.getTasksHashMap().size(), "Не загрузились строки");
    }

}