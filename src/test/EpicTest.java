package test;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.StatusTask;
import task.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {


    TaskManager taskManager = Managers.getDefault();

    @Test // Epic status NEW если нет Subtask-ов
    public void epicStatusShouldBeNewIfSubtaskIsAbsent (){
        StatusTask epicStatus=null;
        Epic epic = new Epic("Приготовить коктейль",
                "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        taskManager.saveEpic(epic);
        if (epic.getSubTaskGroup().isEmpty()){
            epicStatus = epic.getTaskStatus();
        }
        System.out.println(epic.getTaskStatus());

        assertEquals(StatusTask.NEW, epicStatus, "Статус не соответствует! NEW стр 29");
    }

    @Test // Epic status NEW если у всех Subtask-ов статус NEW
    public void epicStatusShouldBeNewIfSubtaskStatusNew (){

        Epic epic = new Epic("Приготовить коктейль",
                "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask1 = new Subtask("19.02.2023 12:00", "14","Купить Ром/Колу",
                "Купить 1 литр", StatusTask.NEW, 1);
        Subtask subtask2 = new Subtask("19.02.2023 12:15", "14","Приготовить лед",
                "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 1);
        taskManager.saveEpic(epic);
        taskManager.saveSubtask(subtask1);
        taskManager.saveSubtask(subtask2);

        StatusTask epicStatus = epic.getTaskStatus();
        System.out.println(epic.getTaskStatus());

        assertEquals(StatusTask.NEW, epicStatus, "Статус не соответствует! NEW стр 45");
    }

    @Test // Epic status DONE если у всех Subtask-ов статус DONE
    public void epicStatusShouldBeDoneIfSubtaskStatusDone (){

        Epic epic1 = new Epic("Приготовить коктейль",
                "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask2 = new Subtask("19.02.2023 12:00", "14","Купить Ром/Колу",
                "Купить 1 литр", StatusTask.NEW, 1);
        Subtask subtask3 = new Subtask("19.02.2023 12:15", "14","Приготовить лед",
                "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 1);
        taskManager.saveEpic(epic1);
        taskManager.saveSubtask(subtask2);
        taskManager.saveSubtask(subtask3);

        Subtask subtask2up = new Subtask("19.02.2023 12:00", "14","Купить Ром/Колу",
                "Купить 1 литр", StatusTask.DONE, 1);
        Subtask subtask3up = new Subtask("19.02.2023 12:15", "14","Приготовить лед",
                "Воду налить в форму и поставить в морозилку", StatusTask.DONE, 1);
        taskManager.updateById(2, subtask2up);
        taskManager.updateById(3, subtask3up);

        StatusTask epicStatus = epic1.getTaskStatus();
        System.out.println(epic1.getTaskStatus());

        assertEquals(StatusTask.DONE, epicStatus, "Статус не соответствует! DONE стр 66");
    }

    @Test // Epic status IN_PROGRESS если Subtask-ов статус DONE + NEW
    public void epicStatusShouldBeDoneIfSubtaskStatusDoneNew (){

        Epic epic1 = new Epic("Приготовить коктейль",
                "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        Subtask subtask2 = new Subtask("19.02.2023 12:00", "14","Купить Ром/Колу",
                "Купить 1 литр", StatusTask.NEW, 1);
        Subtask subtask3 = new Subtask("19.02.2023 12:15", "14","Приготовить лед",
                "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 1);
        taskManager.saveEpic(epic1);
        taskManager.saveSubtask(subtask2);
        taskManager.saveSubtask(subtask3);

        Subtask subtask2up = new Subtask("19.02.2023 12:00", "14","Купить Ром/Колу",
                "Купить 1 литр", StatusTask.DONE, 1);
        Subtask subtask3up = new Subtask("19.02.2023 12:15", "14","Приготовить лед",
                "Воду налить в форму и поставить в морозилку", StatusTask.NEW, 1);
        taskManager.updateById(2, subtask2up);
        taskManager.updateById(3, subtask3up);

        StatusTask epicStatus = epic1.getTaskStatus();
        System.out.println(epic1.getTaskStatus());

        assertEquals(StatusTask.IN_PROGRESS, epicStatus, "Статус не соответствует! IN_PROGRESS стр 87");
    }



}