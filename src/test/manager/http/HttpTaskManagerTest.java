package manager.http;

import manager.Managers;
import manager.TaskManager;
import manager.TaskManagerTest;
import manager.file.FileBackedTasksManager;
import org.junit.jupiter.api.*;
import server.HttpTaskServer;
import server.KVServer;
import task.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    public static KVServer server;
    public static HttpTaskServer taskServerTest;
    @BeforeAll
    static void start() throws IOException {
        server = new KVServer();
        server.start();
    }
    @BeforeEach
    void startService() throws IOException {
        taskManager = (HttpTaskManager) Managers.getDefault();
        taskServerTest = new HttpTaskServer(taskManager);
        taskServerTest.start();
    }

    @Test
    void checkingTheDownloadFromTheServer() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        Epic epic1 = new Epic(2, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        Subtask subtask1 = new Subtask(3, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.15", 600, epic1.getId());
        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        HttpTaskManager httpTaskManager = (HttpTaskManager)Managers.getDefault();
        httpTaskManager.load();

        assertEquals(3, httpTaskManager.getPrioritizedTasks().size());
        assertEquals(3, httpTaskManager.getHistory().size());
    }

    @Test
    void checkingTheDownloadFromTheServerTask() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        taskManager.createTask(task1);
        HttpTaskManager httpTaskManager = (HttpTaskManager)Managers.getDefault();
        httpTaskManager.load();

        assertNotNull(taskManager.getTaskById(1), "Задача не найдена");
        assertEquals(task1.getId(), taskManager.getTaskById(1).getId(), "Id задач не совпадают");
        assertEquals(task1.getStatus(), taskManager.getTaskById(1).getStatus(), "Статусы задач не совпадают");
        assertEquals(task1.getName(), taskManager.getTaskById(1).getName(), "Имена задач не совпадают");
        assertEquals(task1.getType(), taskManager.getTaskById(1).getType(), "Тип задач не сорвпадает");
        assertEquals(task1.getDescription(), taskManager.getTaskById(1).getDescription(), "Описание задачи не совпадает");
        assertEquals(task1.getStartTime(), taskManager.getTaskById(1).getStartTime(), "Время начала выполнения задачи не совподает");
        assertEquals(task1.getDuration(), taskManager.getTaskById(1).getDuration(), "Продолжительность выполнения задачи не совподает");
    }

    @Test
    void checkingTheDownloadFromTheServerEpic() {
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        HttpTaskManager httpTaskManager = (HttpTaskManager)Managers.getDefault();
        httpTaskManager.load();

        assertNotNull(taskManager.getEpicById(1), "Эпик не найден");
        assertEquals(epic1.getId(), taskManager.getEpicById(1).getId(), "Id эпиков не совпадет");
        assertEquals(epic1.getStatus(), taskManager.getEpicById(1).getStatus(), "Статус эпиков не совпадает");
        assertEquals(epic1.getName(), taskManager.getEpicById(1).getName(), "Имена эпиков не совпадают");
        assertEquals(epic1.getType(), taskManager.getEpicById(1).getType(), "Тип эпиков не сорвпадает");
        assertEquals(epic1.getDescription(), taskManager.getEpicById(1).getDescription(), "Описание эпиков не совпадает");
        assertEquals(epic1.getStartTime(), taskManager.getEpicById(1).getStartTime(), "Время начала выполнения эпиков не совподает");
        assertEquals(epic1.getDuration(), taskManager.getEpicById(1).getDuration(), "Продолжительность выполнения эпиков не совподает");
    }

    @Test
    void checkingTheDownloadFromTheServerSubtask() {
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW,
                TaskType.SUBTASK, "2023.09.14", 600, epic1.getId());
        taskManager.createSubtask(subtask1);
        HttpTaskManager httpTaskManager = (HttpTaskManager)Managers.getDefault();
        httpTaskManager.load();

        assertNotNull(taskManager.getSubtaskById(2), "Сабтаск не найден");
        assertEquals(subtask1.getId(), taskManager.getSubtaskById(2).getId(), "Id сабтасков не совпадет");
        assertEquals(subtask1.getStatus(), taskManager.getSubtaskById(2).getStatus(), "Статус сабтаска не совпадает");
        assertEquals(subtask1.getName(), taskManager.getSubtaskById(2).getName(), "Имена сабтасков не совпадают");
        assertEquals(subtask1.getType(), taskManager.getSubtaskById(2).getType(), "Тип сабтасков не сорвпадает");
        assertEquals(subtask1.getDescription(), taskManager.getSubtaskById(2).getDescription(), "Описание сабтасков не совпадает");
        assertEquals(subtask1.getStartTime(), taskManager.getSubtaskById(2).getStartTime(), "Время начала выполнения сабтасков не совподает");
        assertEquals(subtask1.getDuration(), taskManager.getSubtaskById(2).getDuration(), "Продолжительность выполнения сабтасков не изменено");

        assertEquals(taskManager.getSubtaskById(2).getStartTime(), epic1.getStartTime(), "Начальное время выполнения эпика не обновлено");
        assertEquals(taskManager.getSubtaskById(2).getDuration(), epic1.getDuration(), "Продолжительрность выполнения эпика не обновлена");
        assertEquals(taskManager.getSubtaskById(2).getStatus(), epic1.getStatus(), "Статус эпика не совпадает");
    }

    @AfterEach
    void stopService() {
        taskServerTest.stop(1);
    }

    @AfterAll
    static void stop() {
        server.stop(1);
    }
}