package manager.http;

import client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import manager.file.FileBackedTasksManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient client;
    private Gson gson = new Gson();


    public HttpTaskManager(int port) {
        super(null);
        this.client = new KVTaskClient(port);
    }

    @Override
    public void save() {
        client.save("task", gson.toJson(taskList.values()));
        client.save("subtask", gson.toJson(subtaskList.values()));
        client.save("epic", gson.toJson(epicList.values()));
        client.save("tasks", gson.toJson(getPrioritizedTasks()));
        List<Integer> historyIds = historyManager.getHistory().stream()
                .map(Task::getId)
                .collect(Collectors.toList());
        client.save("history", gson.toJson(historyIds));
    }

    public void load() {
        loadTask();
        loadSubtask();
        loadEpic();
        loadHistory();
    }

    private void loadTask() {
        JsonElement jsonElement = JsonParser.parseString(client.load("task"));
        JsonArray jsonTasksArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonTasksArray) {
            Task task = gson.fromJson(element.getAsJsonObject(), Task.class);
            taskList.put(task.getId(), task);
            prioritizedTasks.add(task);
        }
    }

    private void loadSubtask() {
        JsonElement jsonElement = JsonParser.parseString(client.load("subtask"));
        JsonArray jsonTasksArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonTasksArray) {
            Subtask subtask = gson.fromJson(element.getAsJsonObject(), Subtask.class);
            taskList.put(subtask.getId(), subtask);
            prioritizedTasks.add(subtask);
        }
    }

    private void loadEpic() {
        JsonElement jsonElement = JsonParser.parseString(client.load("epic"));
        JsonArray jsonTasksArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonTasksArray) {
            Epic epic = gson.fromJson(element.getAsJsonObject(), Epic.class);
            taskList.put(epic.getId(), epic);
            prioritizedTasks.add(epic);
        }
    }

    private void loadHistory() {
        JsonElement jsonElement = JsonParser.parseString(client.load("history"));
        JsonArray jsonHistoryArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonHistoryArray) {
            int id = element.getAsInt();
            if (taskList.containsKey(id)) {
                historyManager.addTask(taskList.get(id));
            } else if (epicList.containsKey(id)) {
                historyManager.addTask(epicList.get(id));
            } else if (subtaskList.containsKey(id)) {
                historyManager.addTask(subtaskList.get(id));
            }
        }
    }

}
