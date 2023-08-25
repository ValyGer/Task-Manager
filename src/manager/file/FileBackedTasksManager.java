package manager.file;

import manager.InMemoryTaskManager;
import task.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    static private File file = new File("./resources/manager.csv");

    public FileBackedTasksManager() {
    }
    public FileBackedTasksManager(File file) {
        this.file = file;
    }
    static private CSVFormatHandler handler = new CSVFormatHandler();

    public static void main(String[] args) {
        //File file1 = new File("./resources/manager.csv");
        loadFromFile(file);
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // верхняя строка
            writer.write(handler.getFirstString());
            writer.newLine();
                // запись таксов
            for (Task task: taskList.values()){
                writer.write(handler.toString(task));
                writer.newLine();
            }
                // запись эпиков
            for (Epic epic: epicList.values()){
                writer.write(handler.toString(epic));
                writer.newLine();
            }
                // запись сабтасков
            for (Subtask subtask: subtaskList.values()){
                writer.write(handler.toString(subtask));
                writer.newLine();
            }
            // пишем пустую строку
            writer.newLine();
            // запись истории просмотров
            writer.write(handler.historyToString(historyManager));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл!");
        }
    } // метод автосохранения
    static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        String fileName = "./resources/" + file.getName();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // пропускаем первую строку
            while (!((line = reader.readLine()).equals(""))) {
                Task task = handler.fromString(line);
                switch (task.getType()) {
                    case TASK:
                        manager.taskList.put(task.getId(), task);
                        break;
                    case EPIC:
                        manager.epicList.put(task.getId(), (Epic)task);
                        break;
                    case SUBTASK:
                        manager.subtaskList.put(task.getId(), (Subtask)task);
                        break;
                }
            }
            line = reader.readLine();
            List<Integer> numberTaskInHistory = handler.historyFromString(line);
//            for (Integer id: numberTaskInHistory) {
//                manager.historyManager.addTask(task.getTaskById(id));
//            }
            List<Task> history = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с менеджером. Возможно, файл отсутствует в нужной директории.");
        }
    return manager;
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTask = super.getAllTasks();
        save();
        return allTask;
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public void removeTaskById(Integer id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpic = super.getAllEpics();
        save();
        return allEpic;
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public void removeEpicById(Integer id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public ArrayList<Subtask> getListSubtaskInEpic(Integer id) {
        ArrayList<Subtask> listSubtaskInEpic = super.getListSubtaskInEpic(id);
        save();
        return listSubtaskInEpic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
        return subtask;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtask = super.getAllSubtasks();
        save();
        return allSubtask;
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeSubtaskById(Integer id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> listOfHistory = super.getHistory();
        save();
        return listOfHistory;
    }
}
