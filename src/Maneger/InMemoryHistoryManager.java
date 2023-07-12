package Maneger;

import task.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public static final int HISTORY_SIZE = 10;
    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        } else if (history.size() >= HISTORY_SIZE) {
            history.removeFirst();
            history.add(task);
        } else {
            history.add(task);
        }
    }
}
