package task;

import manager.TaskManager;
import manager.memory.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    private Epic epic;
    private TaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    void createEpicStart() {
        epic = new Epic("Эпик", "Описание эпика");
        epic = taskManager.createEpic(epic);
    }

    @Test
    void emptyListOfSubtask() {
        ArrayList<Subtask> listSubtask = taskManager.getListSubtaskInEpic(epic.getId());
        assertNotNull(listSubtask, "Список не создан, возвращен null");
        assertTrue(listSubtask.isEmpty(), "Список не пустой");
    }

    @Test
    void returnStatusEpicNewIfSubtasksHaveStatusNew() {
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, epic.getId());
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        taskManager.updateEpic(epic);
        assertEquals(TaskStatus.NEW, epic.status, "Статус Эпика не обновлен");
    }

    @Test
    void returnStatusEpicDoneIfSubtasksHaveStatusDone() {
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.DONE, epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", TaskStatus.DONE, epic.getId());
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        taskManager.updateEpic(epic);
        assertEquals(TaskStatus.DONE, epic.status, "Статус Эпика не обновлен");
    }

    @Test
    void returnStatusEpicInProgressIfSubtasksHaveStatusNewAndDone() {
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.DONE, epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, epic.getId());
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        taskManager.updateEpic(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.status, "Статус Эпика не обновлен");
    }

    @Test
    void returnStatusEpicInProgressIfSubtasksHaveStatusInProgress() {
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.IN_PROGRESS, epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", TaskStatus.IN_PROGRESS, epic.getId());
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        taskManager.updateEpic(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.status, "Статус Эпика не обновлен");
    }
}