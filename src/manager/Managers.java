package manager;

import manager.file.FileBackedTasksManager;
import manager.memory.InMemoryHistoryManager;
import manager.memory.InMemoryTaskManager;

public final class Managers {

    private Managers(){}

    public static TaskManager getDefault() {
       // return new InMemoryTaskManager();  // Выключаем реализацию метода InMemoryTaskManager
        return new FileBackedTasksManager();   // Подключаем реализацию метода FileBackedTasksManager
    }

    public static HistoryManager getHistoryDefault() {
        return new InMemoryHistoryManager();
    }
}
