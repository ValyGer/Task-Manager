package manager.file;

import manager.memory.InMemoryTaskManager;
import task.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class FileBackedTasksManager extends InMemoryTaskManager {
    static private File file = new File("./resources/manager.csv");

    public FileBackedTasksManager() {
    }
    public FileBackedTasksManager(File file) {
        this.file = file;
    }
    static private CSVFormatHandler handler = new CSVFormatHandler();

    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        //Создаем задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1"); //id = 1
        Task task2 = new Task("Задача 2", "Описание задачи 2"); //id = 2
        task1 = fileBackedTasksManager.createTask(task1);
        task2 = fileBackedTasksManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1"); //id = 3
        epic1 = fileBackedTasksManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId()); //id = 4
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId()); //id = 5
        subtask1 = fileBackedTasksManager.createSubtask(subtask1);
        subtask2 = fileBackedTasksManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2"); //id = 6
        epic2 = fileBackedTasksManager.createEpic(epic2); //Создаем эпик без подзадач
        //Делаем вызов элементов
        fileBackedTasksManager.getSubtaskById(subtask2.getId()); //id = 5
        fileBackedTasksManager.getEpicById(epic1.getId());       //id = 3
        fileBackedTasksManager.getTaskById(task2.getId());       //id = 2
        fileBackedTasksManager.getSubtaskById(subtask1.getId()); //id = 4
        fileBackedTasksManager.getEpicById(epic2.getId());       //id = 6
        fileBackedTasksManager.getTaskById(task1.getId());       //id = 1
        //Повторный вызов элементов
        fileBackedTasksManager.getSubtaskById(subtask2.getId()); //id = 5
        fileBackedTasksManager.getEpicById(epic1.getId());       //id = 3
        //Удаление элементов
        fileBackedTasksManager.removeTaskById(task1.getId());    //удаление id = 1
//        fileBackedTasksManager.removeEpicById(epic1.getId());    //удаление id = 3 (4,5);


        loadFromFile(file); // загрузка приложения из файла
        // добавление задачи для проверки счетчика id
        Task task3 = new Task("Задача 3", "Описание задачи 3"); //id = 7
        task3 = fileBackedTasksManager.createTask(task3);
        fileBackedTasksManager.getTaskById(task3.getId());       //id = 7
    }  // main метод тестирования работвы с файлами

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
            throw new ManagerSaveException("Невозможно прочитать файл!");
        }
    } // метод автосохранения
    static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        String fileName = "./resources/" + file.getName();
        List<Integer> listOfId = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // пропускаем первую строку
            while (!((line = reader.readLine()).isEmpty())) {
                Task task = handler.fromString(line);
                listOfId.add(task.getId());
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
            for (int id: numberTaskInHistory) {
                if (manager.taskList.containsKey(id)) {
                    Task task = manager.taskList.get(id);
                    manager.historyManager.addTask((task));
                } else if (manager.epicList.containsKey(id)) {
                    Epic epic = manager.epicList.get(id);
                    manager.historyManager.addTask((epic));
                } else {
                    Subtask subtask = manager.subtaskList.get(id);
                    manager.historyManager.addTask((subtask));
                }
            }
            Task.setGenerateId(Collections.max(listOfId)); // обновляем информацию по счетчику id
        } catch (IOException e) {
            throw new ManagerSaveException("Невозможно прочитать файл с менеджером. Возможно, файл отсутствует в нужной директории.");
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
