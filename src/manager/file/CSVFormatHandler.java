package manager.file;

import manager.HistoryManager;
import task.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static task.TaskType.EPIC;
import static task.TaskType.TASK;

public class CSVFormatHandler {
    private static final String DELIMITER = ",";
    static String getFirstString() {
        return "id,type,name,status,description,starttime,duration,epic";
    }
    static String toString(Task task) {
        String result = task.getId() + DELIMITER +
               task.getType() + DELIMITER +
               task.getName() + DELIMITER +
               task.getStatus() + DELIMITER +
               task.getDescription() + DELIMITER +
               task.getStartTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + DELIMITER +
               task.getDuration().toMinutes();
        if (task.getType() == TaskType.SUBTASK) {
            result = result + DELIMITER + ((Subtask) task).getEpicId();
        }
        return result;
    }
    static Task fromString(String row) {
        row = row.trim(); // удоляем пробелы из строки
        String[] parts = row.split(",");  // разбиваем строку на массив строк по элементу ","
        int id = Integer.parseInt(parts[0]);                          // поле id
        TaskType taskType = TaskType.valueOf(parts[1].toUpperCase()); // поле type
        String taskName = parts[2];                                   // поле name
        TaskStatus taskStatus = TaskStatus.valueOf(parts[3]);         // поле status
        String description = parts[4];                                // поле description
        String startTime = parts[5];                                  // поле starttime
        Integer minutes = Integer.parseInt(parts[6]);                 // поле duration
        int epicId = 0;                                               // проверка наличия поля epic
        if (parts.length == 8) {
            epicId = Integer.parseInt(parts[7]);
        }
        if (taskType.equals(TASK)) {
            return new Task(id, taskName, description, taskStatus, taskType, startTime, minutes);
        } else if (taskType.equals(EPIC)) {
            return new Epic(id, taskName, description, taskStatus, taskType);
        } else {
            return new Subtask(id, taskName, description, taskStatus, taskType, startTime, minutes, epicId);
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
        if (rowHistory == null) {
            return new ArrayList<>();
        } else {
            rowHistory = rowHistory.trim(); // удоляем пробелы из строки
            String[] parts = rowHistory.split(",");
            Integer[] numberTask = new Integer[parts.length];
            for (int i = 0; i < parts.length; i++) {
                numberTask[i] = Integer.parseInt(parts[i]);
            }
            return new ArrayList<>(Arrays.asList(numberTask));
        }
    }
}