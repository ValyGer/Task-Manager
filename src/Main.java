import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
//Создаем задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1"); //id = 1
        Task task2 = new Task("Задача 2", "Описание задачи 2"); //id = 2
        Task task3 = new Task("Задача 3", "Описание задачи 3"); //id = 3
        task1 = taskManager.createTask(task1);
        task2 = taskManager.createTask(task2);
        task3 = taskManager.createTask(task3);
//Создаем эпик с тремя подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1"); //id = 4
        epic1 = taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId()); //id = 5
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId()); //id = 6
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epic1.getId()); //id = 7
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        subtask3 = taskManager.createSubtask(subtask3);
//Создаем эпик без подзадач
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2"); //id = 8
        epic2 = taskManager.createEpic(epic2);

//Делаем вызов элементов
        taskManager.getSubtaskById(subtask3.getId()); //id = 7
        taskManager.getSubtaskById(subtask2.getId()); //id = 6
        taskManager.getEpicById(epic1.getId());       //id = 4
        taskManager.getTaskById(task2.getId());       //id = 2
        taskManager.getTaskById(task3.getId());       //id = 3
        taskManager.getSubtaskById(subtask1.getId()); //id = 5
        taskManager.getEpicById(epic2.getId());       //id = 8
        taskManager.getTaskById(task1.getId());       //id = 1
// вызов с повторами
        taskManager.getSubtaskById(subtask2.getId()); //id = 2
        taskManager.getEpicById(epic1.getId());       //id = 4
        taskManager.getTaskById(task3.getId());       //id = 3
        taskManager.getSubtaskById(subtask3.getId()); //id = 7

        taskManager.removeTaskById(task1.getId());    //удаление id = 2
        taskManager.removeEpicById(epic1.getId());    //удаление id = 4 (5,6,7);

        System.out.println();
        System.out.println();

        List<Task> history = taskManager.getHistory();
        for (Task task : history) {
            System.out.println(task);
        }
        // до повторов: 76423581 после повторов: 65812437 после удаления: 813
    }
}