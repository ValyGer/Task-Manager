package manager;

import org.junit.jupiter.api.Test;
import task.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    // тесты проверки работы методов с task
    @Test
    void createTaskTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач должен быть пустым.");
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 600); //id = 1
        taskManager.createTask(task1);
        assertNotNull(taskManager.getTaskById(1), "Задача не создана");
        assertEquals(task1, taskManager.getTaskById(1), "Создается задача с иным содержанием");
        Set<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        assertNotNull(prioritizedTasks, "Список задач по приоритетам не создан");
        assertEquals(1, prioritizedTasks.size(), "Список задач по приоритетам имеет неверную длину");
    }

    @Test
    void getAllTaskTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач должен быть пустым.");
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 600); //id = 1
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач не пустой");
        taskManager.createTask(task1);
        List<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(task1, tasks.get(0), "Задача в списке не совпадает с созданной");
    }

    @Test
    void removeAllTasksTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач должен быть пустым.");
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 1000); //id = 2
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.removeAllTasks();
        List<Task> tasks = taskManager.getAllTasks();
        assertTrue(tasks.isEmpty(), "Не все задачи были удалены");
    }

    @Test
    void getTaskByIdTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач должен быть пустым.");
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        taskManager.createTask(task1);
        assertNotNull(taskManager.getTaskById(1), "Задача не получена, возвращен null");
        assertEquals(taskManager.getTaskById(1), task1, "Задачи не соответсвуют друг другу");
    }

    @Test
    void removeTaskByIdTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач должен быть пустым.");
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        taskManager.createTask(task1);
        assertEquals(1, taskManager.getAllTasks().size(), "Количество задач в списке более чем 1");
        taskManager.removeTaskById(1);
        assertTrue(taskManager.getAllTasks().isEmpty(), "Задача не была удалена из списка");
    }

    @Test
    void updateTaskTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач должен быть пустым.");
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        taskManager.createTask(task1);
        taskManager.updateTask(new Task(1, "Задача 1", "Описание задачи 1 изменено", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600));
        assertNotNull(taskManager.getTaskById(1), "Задача не найдена");
        assertEquals(task1.getId(), taskManager.getTaskById(1).getId(), "Id задач не совпадаю");
        assertEquals(task1.getStatus(), taskManager.getTaskById(1).getStatus(), "Статус задачи не обновлен");
        assertEquals(task1.getName(), taskManager.getTaskById(1).getName(), "Имена задач не совпадают");
        assertEquals(task1.getType(), taskManager.getTaskById(1).getType(), "Тип задач не сорвпадает");
        assertNotEquals(task1.getDescription(), taskManager.getTaskById(1).getDescription(), "Описание задачи не изменено");
        assertEquals(task1.getStartTime(), taskManager.getTaskById(1).getStartTime(), "Время начала выполнения задачи не совподает");
        assertEquals(task1.getDuration(), taskManager.getTaskById(1).getDuration(), "Продолжительность выполнения задачи не совподает");
        assertEquals("Описание задачи 1 изменено", taskManager.getTaskById(1).getDescription(), "Описнаие задачи обновлено не корректно");
    }


    // тесты проверки работы методов с epic
    @Test
    void createEpicTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        assertNotNull(taskManager.getEpicById(1), "Эпик не создана");
        assertEquals(epic1, taskManager.getEpicById(1), "Создается эпик с иным содержанием");
    }

    @Test
    void getAllEpicTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков не пустой");
        taskManager.createEpic(epic1);
        List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "Эпики не возвращаются");
        assertEquals(1, epics.size(), "Неверное количество эпиков");
        assertEquals(epic1, epics.get(0), "Эпик в списке не совпадает с созданным");
    }

    @Test
    void removeAllEpicsTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        Epic epic2 = new Epic(2, "Эпик 2", "Описание эпика 2", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.removeAllEpics();
        List<Epic> epics = taskManager.getAllEpics();
        assertTrue(epics.isEmpty(), "Не все задачи были удалены");
    }

    @Test
    void getEpicByIdTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        Epic epic = taskManager.getEpicById(1);
        assertNotNull(epic, "Эпик не получен");
        assertEquals(epic1, epic, "Эпики не соотвутсвуют друг другу");
    }

    @Test
    void removeEpicByIdTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        assertEquals(1, taskManager.getAllEpics().size(), "Количество эпиков в списке более чем 1");
        taskManager.removeEpicById(1);
        assertTrue(taskManager.getAllEpics().isEmpty(), "Эпик не был удален из списка");
    }

    @Test
    void getListSubtaskInEpicTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        ArrayList<Subtask> subtasksInEpic = taskManager.getListSubtaskInEpic(epic1.getId());
        assertNotNull(subtasksInEpic, "Список сабтасков не возвращен");
        assertTrue(subtasksInEpic.isEmpty(), "Список сабтасоков не пустой");
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.15", 600, epic1.getId());
        taskManager.createSubtask(subtask1);
        subtasksInEpic = taskManager.getListSubtaskInEpic(epic1.getId());
        assertEquals(1, subtasksInEpic.size(), "Дллина списка подзадач не соотвествтует");
        assertEquals(subtask1, subtasksInEpic.get(0), "Подзадачи не совпадают");
    }

    @Test
    void updateEpicTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        taskManager.updateEpic(new Epic(1, "Эпик 1", "Описание эпика 1 изменено", TaskStatus.NEW, TaskType.EPIC));
        assertNotNull(taskManager.getEpicById(1), "Эпик не найден");
        assertEquals(epic1.getId(), taskManager.getEpicById(1).getId(), "Id эпиков не совпадет");
        assertEquals(epic1.getStatus(), taskManager.getEpicById(1).getStatus(), "Статус эпика не обновлен");
        assertEquals(epic1.getName(), taskManager.getEpicById(1).getName(), "Имена эпиков не совпадают");
        assertEquals(epic1.getType(), taskManager.getEpicById(1).getType(), "Тип эпиков не сорвпадает");
        assertNotEquals(epic1.getDescription(), taskManager.getEpicById(1).getDescription(), "Описание эпиков не изменено");
        assertEquals(epic1.getStartTime(), taskManager.getEpicById(1).getStartTime(), "Время начала выполнения эпиков не совподает");
        assertEquals(epic1.getDuration(), taskManager.getEpicById(1).getDuration(), "Продолжительность выполнения эпиков не совподает");
        assertEquals("Описание эпика 1 изменено", taskManager.getEpicById(1).getDescription(), "Описнаие эпиков обновлено не корректно");
    }

    @Test
    void updateEpicTimeTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.15", 600, epic1.getId());
        taskManager.createSubtask(subtask1);
        assertEquals(subtask1.getStartTime(), epic1.getStartTime(), "Время не обнавлено");
    }

    // тесты проверки работы методов с subtask
    @Test
    void createSubtaskTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.15", 600, epic1.getId());
        taskManager.createSubtask(subtask1);
        assertNotNull(taskManager.getSubtaskById(2), "Задача не создана");
        assertEquals(subtask1, taskManager.getSubtaskById(2), "Создается задача с иным содержанием");
        Set<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        assertNotNull(prioritizedTasks, "Список задач по приоритетам не создан");
        assertEquals(1, prioritizedTasks.size(), "Список задач по приоритетам имеет неверную длину");
    }

    @Test
    void getAllSubtasksTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков не пустой");
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.14", 600, epic1.getId());
        taskManager.createSubtask(subtask1);
        List<Subtask> subtasks = taskManager.getAllSubtasks();
        assertNotNull(subtasks, "Сабтаски не возвращаются");
        assertEquals(1, subtasks.size(), "Неверное количество сабтасков");
        assertEquals(subtask1, subtasks.get(0), "Сабтаск в списке не совпадает с созданным");
    }

    @Test
    void removeAllSubtasksTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.14", 600, epic1.getId());
        Subtask subtask2 = new Subtask(3, "Сабтаск 2", "Описание сабтаска 2", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.15", 800, epic1.getId());
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.removeAllSubtasks();
        List<Subtask> subtasks = taskManager.getAllSubtasks();
        assertTrue(subtasks.isEmpty(), "Не все задачи были удалены");
    }

    @Test
    void removeSubtaskByIdTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков не пустой");
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.14", 600, epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.removeSubtaskById(2);
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков не пустой");
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Сабтаск не был удален из списка");
    }

    @Test
    void getSubtaskByIdTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW, TaskType.SUBTASK, "2023.09.14", 600, epic1.getId());
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        assertNotNull(taskManager.getSubtaskById(2), "Сабтаск не получен, возвращен null");
        assertEquals(taskManager.getSubtaskById(2), subtask1, "Сабтаски не соответсвуют друг другу");
    }

    @Test
    void updateSubtaskTest() {
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        Epic epic1 = new Epic(1, "Эпик 1", "Описание эпика 1", TaskStatus.NEW, TaskType.EPIC);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask(2, "Сабтаск 1", "Описание сабтаска 1", TaskStatus.NEW,
                TaskType.SUBTASK, "2023.09.14", 600, epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.updateSubtask(new Subtask(2, "Сабтаск 1", "Описание сабтаска 1 изменено",
                TaskStatus.IN_PROGRESS, TaskType.SUBTASK, "2023.09.12", 800, epic1.getId()));

        assertNotNull(taskManager.getSubtaskById(2), "Сабтаск не найден");
        assertEquals(subtask1.getId(), taskManager.getSubtaskById(2).getId(), "Id сабтасков не совпадет");
        assertNotEquals(subtask1.getStatus(), taskManager.getSubtaskById(2).getStatus(), "Статус сабтаска не обновлен");
        assertEquals(subtask1.getName(), taskManager.getSubtaskById(2).getName(), "Имена сабтасков не совпадают");
        assertEquals(subtask1.getType(), taskManager.getSubtaskById(2).getType(), "Тип сабтасков не сорвпадает");
        assertNotEquals(subtask1.getDescription(), taskManager.getSubtaskById(2).getDescription(), "Описание сабтасков не изменено");
        assertNotEquals(subtask1.getStartTime(), taskManager.getSubtaskById(2).getStartTime(), "Время начала выполнения сабтасков не совподает");
        assertNotEquals(subtask1.getDuration(), taskManager.getSubtaskById(2).getDuration(), "Продолжительность выполнения сабтасков не изменено");
        assertEquals("Описание сабтаска 1 изменено", taskManager.getSubtaskById(2).getDescription(), "Описнаие сабтасков обновлено не корректно");

        assertEquals(taskManager.getSubtaskById(2).getStartTime(), epic1.getStartTime(), "Начальное время выполнения эпика не обновлено");
        assertEquals(taskManager.getSubtaskById(2).getDuration(), epic1.getDuration(), "Продолжительрность выполнения эпика не обновлена");
        assertEquals(taskManager.getSubtaskById(2).getStatus(), epic1.getStatus(), "Статус эпика не обновлен");
    }

    @Test
    void getHistoryTest() {
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач должен быть пустым.");
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков должен быть пустым.");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Список сабтасков должен быть пустым.");
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 1000); //id = 2
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTaskById(1);
        assertNotNull(taskManager.getHistory(), "История не возвращается");
        assertEquals(2, taskManager.getHistory().size(), "Длина истории не соотвесвтует ожидаемой");
    }

    @Test
    void getPrioritizedTasks() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 600); //id = 1
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 1000); //id = 2
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Set<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        assertNotNull(prioritizedTasks, "Список задач по приоритетам не создан");
        assertEquals(2, prioritizedTasks.size(), "Список задач по приоритетам имеет неверную длину");
    }
}