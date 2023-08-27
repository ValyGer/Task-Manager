package manager.file;

import manager.HistoryManager;
import task.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static task.TaskType.EPIC;
import static task.TaskType.TASK;

public class CSVFormatHandler {
    private static final String DELIMITER = ",";
    static String getFirstString() {
        return "id,type,name,status,description,epic";
    }
    static String toString(Task task) {
        String result = task.getId() + DELIMITER +
               task.getType() + DELIMITER +
               task.getName() + DELIMITER +
               task.getStatus() + DELIMITER +
               task.getDescription();
        if (task.getType() == TaskType.SUBTASK) {
            result = result + DELIMITER + ((Subtask) task).getEpicId();
        }
        return result;
    }
    static Task fromString(String row) {
        row = row.trim(); // удоляем пробелы из строки
        String[] parts = row.split(",");  // разбиваем строку на массив строк по элементу ","
        int id = Integer.parseInt(parts[0]);     //  присваиваем переменным элементы массива
        TaskType taskType = TaskType.valueOf(parts[1].toUpperCase());
        String taskName = parts[2];
        TaskStatus taskStatus = TaskStatus.valueOf(parts[3]);
        String description = parts[4];
        int epicId = 0;
        if (parts.length == 6) {
            epicId = Integer.parseInt(parts[5]);
        }
        if (taskType.equals(TASK)) {
            return new Task(id, taskName, description, taskStatus, taskType);
        } else if (taskType.equals(EPIC)) {
            return new Epic(id, taskName, description, taskStatus, taskType);
        } else {
            return new Subtask(id, taskName, description, taskStatus, taskType, epicId);
        }
    }
    static String historyToString(HistoryManager historyManager) {
        List<String> result = new ArrayList<>();
        for (Task task: historyManager.getHistory()) {
            result.add(String.valueOf(task.getId()));
        }
        return String.join(DELIMITER, result);
    }
    static List<Integer> historyFromString(String rowHistory) {
        rowHistory = rowHistory.trim(); // удоляем пробелы из строки
        String[] parts = rowHistory.split(",");
        Integer[] numberTask = new Integer[parts.length];
        for (int i = 0; i < parts.length; i++) {
            numberTask[i] = Integer.parseInt(parts[i]);
        }
        return new ArrayList<>(Arrays.asList(numberTask));
    }
}