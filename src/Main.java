import manager.Managers;
import manager.TaskManager;
import manager.http.HttpTaskManager;
import server.HttpTaskServer;
import server.KVServer;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        TaskManager taskManager = Managers.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();

//Создаем задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1"); //id = 1
        task1 = taskManager.createTask(task1);
//Создаем эпик с тремя подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1"); //id = 2
        epic1 = taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId()); //id = 3
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId()); //id = 4
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
//Создаем эпик без подзадач
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2"); //id = 5
        epic2 = taskManager.createEpic(epic2);
// дополнительный вызов 3-х задач
        Task task4 = new Task("Задача 4", "Описание задачи 4"); //id = 6
        task4 = taskManager.createTask(task4);
//Делаем вызов элементов
        taskManager.getSubtaskById(subtask2.getId()); //id = 4
        taskManager.getEpicById(epic1.getId());       //id = 2
        taskManager.getSubtaskById(subtask1.getId()); //id = 3
        taskManager.getEpicById(epic2.getId());       //id = 5
        taskManager.getTaskById(task1.getId());       //id = 1
        taskManager.getTaskById(task4.getId());       //id = 6
//Повторный вызов элементов
        taskManager.getSubtaskById(subtask2.getId()); //id = 4
        taskManager.getEpicById(epic1.getId());       //id = 2
////Удаление элементов
        taskManager.removeTaskById(task1.getId());    //удаление id = 1
        taskManager.removeEpicById(epic1.getId());    //удаление id = 2 (3,4);

//Вывод истории
        System.out.println("Вывод истории просмотров:");
        List<Task> history = taskManager.getHistory();
        for (Task task : history) {
            System.out.println(task);
        }
        //Ожидаем вывод до повторов: 1 8 11 5 3 10 2 4 6 7; после повторов: 7 3 4 6 9 1 8 11 5 10; после удаления: 3 9 8 11 10

//Вывод задач по приоритету
        System.out.println("Вывод задач по приоритету:");
        Set<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        for (Task task : prioritizedTasks) {
            System.out.println(task);
        }
        System.out.println();
        System.out.println("Результат выполнения загрузки данных с сервера");
        HttpTaskManager httpTaskManager = (HttpTaskManager)Managers.getDefault();
        httpTaskManager.load();
        System.out.println(httpTaskManager.getAllTasks());
        System.out.println(httpTaskManager.getAllSubtasks());
        System.out.println(httpTaskManager.getAllEpics());
        System.out.println(httpTaskManager.getHistory());
        System.out.println(httpTaskManager.getPrioritizedTasks());
        httpTaskServer.stop(1);
        kvServer.stop(1);
    }
}