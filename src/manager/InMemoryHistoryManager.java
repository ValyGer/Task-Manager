package manager;

import task.Task;

import java.util.LinkedList;


public class InMemoryHistoryManager implements HistoryManager {
    public static final int HISTORY_SIZE = 10;
    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }

    @Override
    public void addTask(Task task) {
        if (task != null) {
            if (history.size() >= HISTORY_SIZE) {
                history.removeLast();
                history.addFirst(task);
            } else {
                history.addFirst(task);
            }
        }
    }
}
