package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.file.FileBackedTasksManager;
import manager.http.HttpTaskManager;
import manager.memory.InMemoryHistoryManager;
import manager.memory.InMemoryTaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;

public final class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        // Для выбора типа реализации укажите 0 - реализация в памяти, 1, реализация в файле, 2 - реаизация на сервере
        byte typeOfRealisation = 2;
        if (typeOfRealisation == 0) {
            return new InMemoryTaskManager();  // Выключаем реализацию метода InMemoryTaskManager
        } else if (typeOfRealisation == 1) {
            return new FileBackedTasksManager(new File("./resources/manager.csv"));  // Подключаем реализацию метода FileBackedTasksManager
        } else if (typeOfRealisation == 2) {
            return new HttpTaskManager(8078);  // Подключаем реализацию метода HttpTaskManager
        } else {
            System.out.println("Некорректный код для выбора типа реализации");
            return null;
        }
    }

    public static HistoryManager getHistoryDefault() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Task.class, new Task.TaskSerializer());
        gsonBuilder.registerTypeAdapter(Task.class, new Task.TaskDeSerializer());
        gsonBuilder.registerTypeAdapter(Epic.class, new Task.EpicSerializer());
        gsonBuilder.registerTypeAdapter(Epic.class, new Task.EpicDeSerializer());
        gsonBuilder.registerTypeAdapter(Subtask.class, new Task.SubtaskDeSerializer());
        gsonBuilder.registerTypeAdapter(Subtask.class, new Task.SubtaskSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new Task.LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new Task.DurationAdapter());
        return gsonBuilder.create();
    }
}
