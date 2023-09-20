package manager.file;

import manager.TaskManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private File workFile;

    @BeforeEach
    void setUpFirst() {
        workFile = new File("./resources/managerForTest.csv");
        taskManager = new FileBackedTasksManager(workFile);
    }

    @AfterEach
    void clearAll() {
        taskManager.getAllTasks().clear();
        taskManager.getAllEpics().clear();
        taskManager.getAllSubtasks().clear();
        taskManager.getHistory().clear();
    }

    @Test
    void saveToFileTest() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 600); //id = 1
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", TaskStatus.NEW, TaskType.TASK, "2023.09.15", 800); //id = 2
        Epic epic1 = new Epic(3, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        Subtask subtask1 = new Subtask(4, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.14", 750, epic1.getId());
        Epic epic2 = new Epic(5, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        task1 = taskManager.createTask(task1);
        task2 = taskManager.createTask(task2);
        epic1 = taskManager.createEpic(epic1);
        subtask1 = taskManager.createSubtask(subtask1);
        epic2 = taskManager.createEpic(epic2);

        FileBackedTasksManager taskManagerForCheck = new FileBackedTasksManager(workFile);
        taskManagerForCheck = FileBackedTasksManager.loadFromFile(workFile);

        assertEquals(2, taskManagerForCheck.getAllTasks().size(), "Длина списка задач не соотвесвтует сохраненной");
        assertEquals(2, taskManagerForCheck.getAllEpics().size(), "Длина списка экпиков не соотвесвтует сохраненной");
        assertEquals(1, taskManagerForCheck.getAllSubtasks().size(), "Длина списка сабтасков не соотвесвтует сохраненной");
        assertEquals(5, taskManagerForCheck.getHistory().size(), "Длина истории не соотвесвтует сохраненной");
    }

    @Test
    void loadFromFileTest() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 600); //id = 1
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", TaskStatus.NEW, TaskType.TASK, "2023.09.15", 800); //id = 2
        Epic epic1 = new Epic(3, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        Subtask subtask1 = new Subtask(4, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.14", 750, epic1.getId());
        Epic epic2 = new Epic(5, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        task1 = taskManager.createTask(task1);
        task2 = taskManager.createTask(task2);
        epic1 = taskManager.createEpic(epic1);
        subtask1 = taskManager.createSubtask(subtask1);
        epic2 = taskManager.createEpic(epic2);

        FileBackedTasksManager taskManagerForCheck = new FileBackedTasksManager(workFile);
        taskManagerForCheck = FileBackedTasksManager.loadFromFile(workFile);

        assertEquals(2, taskManagerForCheck.getAllTasks().size(), "Длина списка задач не соотвесвтует сохраненной");
        assertEquals(2, taskManagerForCheck.getAllEpics().size(), "Длина списка экпиков не соотвесвтует сохраненной");
        assertEquals(1, taskManagerForCheck.getAllSubtasks().size(), "Длина списка сабтасков не соотвесвтует сохраненной");
        assertEquals(5, taskManagerForCheck.getHistory().size(), "Длина истории не соотвесвтует сохраненной");
    }

    @Test
    void loadFromEmptyFileTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Длина списка задач не соотвесвтует сохраненной");
        assertEquals(0, taskManager.getAllEpics().size(), "Длина списка экпиков не соотвесвтует сохраненной");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Длина списка сабтасков не соотвесвтует сохраненной");
        assertEquals(0, taskManager.getHistory().size(), "Длина истории не соотвесвтует сохраненной");
    }

    @Test
    void loadFromWithEmptyHistoryTest() {
        File fileWithoutHistory = new File("./resources/managerForTestWithoutHistory.csv");
        taskManager = FileBackedTasksManager.loadFromFile(fileWithoutHistory);
        assertEquals(2, taskManager.getAllTasks().size(), "Длина списка задач не соотвесвтует сохраненной");
        assertEquals(2, taskManager.getAllEpics().size(), "Длина списка экпиков не соотвесвтует сохраненной");
        assertEquals(2, taskManager.getAllSubtasks().size(), "Длина списка сабтасков не соотвесвтует сохраненной");
        assertEquals(0, taskManager.getHistory().size(), "Длина истории не соотвесвтует сохраненной");
    }
}