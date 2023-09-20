package manager;

import manager.file.FileBackedTasksManager;
import manager.memory.InMemoryHistoryManager;
import manager.memory.InMemoryTaskManager;

import java.io.File;

public final class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        boolean isChangeWorkWithFile = true;
        if (isChangeWorkWithFile) {
            return new FileBackedTasksManager(new File("./resources/manager.csv"));  // Подключаем реализацию метода FileBackedTasksManager
        } else {
            return new InMemoryTaskManager();  // Выключаем реализацию метода InMemoryTaskManager
        }
    }

    public static HistoryManager getHistoryDefault() {
        return new InMemoryHistoryManager();
    }
}
