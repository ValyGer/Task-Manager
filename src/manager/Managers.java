package manager;

import manager.file.FileBackedTasksManager;

public final class Managers {

    private Managers(){}

    public static TaskManager getDefault() {
        // return new InMemoryTaskManager();   Выключаем реализацию метода InMemoryTaskManager
        return new FileBackedTasksManager();   // ПОдключаем реализацию метода FileBackedTasksManager
    }

    public static HistoryManager getHistoryDefault() {
        return new InMemoryHistoryManager();
    }
}
