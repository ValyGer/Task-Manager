package manager;

import task.Task;

import java.util.LinkedList;

public interface HistoryManager {
    LinkedList<Task> getHistory();

    void addTask(Task task);
}
