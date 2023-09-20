package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;
import task.TaskType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private HistoryManager historyManager;
    private static Task task1 = new Task(1, "Задача 1", "Описание задачи 1", TaskStatus.NEW, TaskType.TASK, "2023.09.10", 600); //id = 1
    private static Task task2 = new Task(2, "Задача 2", "Описание задачи 2", TaskStatus.NEW, TaskType.TASK, "2023.09.12", 200); //id = 2
    private static Task task3 = new Task(3, "Задача 3", "Описание задачи 3", TaskStatus.NEW, TaskType.TASK, "2023.09.13", 500); //id = 3
    private static Task task4 = new Task(4, "Задача 4", "Описание задачи 4", TaskStatus.NEW, TaskType.TASK, "2023.09.14", 100); //id = 4

    @BeforeEach
    void setUp() {
        historyManager = Managers.getHistoryDefault();
    }

    @Test
    void getEmptyHistory() {
        List<Task> emptyHistory = historyManager.getHistory();
        assertNotNull(emptyHistory, "Список истории не создан, возвращен null");
        assertTrue(emptyHistory.isEmpty(), "Список истории не пустой");
    }

    @Test
    void historyShouldContainOneRecordOneTaskHasBeenCalledTwoTime() {
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        List<Task> newHistory = historyManager.getHistory();
        assertEquals(1, newHistory.size(), "В истории дублируется запись задач");
    }

    private void createNewTestHistory() {
        //создаем историю просмотров
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.addTask(task1);
        historyManager.addTask(task4);
    }

    private List<Integer> listID(List<Task> history) {
        List<Integer> listID = new ArrayList<>();
        for (Task task : history) {
            listID.add(task.getId());
        }
        return listID;
    }

    @Test
    void deletingFirstItemFromHistory() {
        createNewTestHistory();// создаем историю, после выполнения имеем: 4, 1, 3, 2
        historyManager.remove(4); // удаляем элемент с id 4, стоящий на пером месте в списке истории,
        // ожидавемая история 1, 3, 2
        List<Integer> listIDInHistory = listID(historyManager.getHistory());
        assertEquals(3, listIDInHistory.size(), "Элемент не удален");
        assertArrayEquals(new Integer[]{1, 3, 2}, listIDInHistory.toArray(), "Удален неверный элемент из истории");
    }

    @Test
    void deletingMiddleItemFromHistory() {
        createNewTestHistory();// создаем историю, после выполнения имеем: 4, 1, 3, 2
        historyManager.remove(3); // удаляем элемент с id 3
        List<Task> newHistory = historyManager.getHistory();
        List<Integer> listIDInHistory = listID(historyManager.getHistory());
        assertEquals(3, listIDInHistory.size(), "Элемент не удален");
        assertArrayEquals(new Integer[]{4, 1, 2}, listIDInHistory.toArray(), "Удален неверный элемент из истории");
    }

    @Test
    void deletingLastItemFromHistory() {
        createNewTestHistory();// создаем историю, после выполнения имеем: 4, 1, 3, 2
        historyManager.remove(2); // удаляем элемент с id 2, стоящий на последнем месте в списке истории,
        List<Task> newHistory = historyManager.getHistory();
        List<Integer> listIDInHistory = listID(historyManager.getHistory());
        assertEquals(3, listIDInHistory.size(), "Элемент не удален");
        assertArrayEquals(new Integer[]{4, 1, 3}, listIDInHistory.toArray(), "Удален неверный элемент из истории");
    }
}