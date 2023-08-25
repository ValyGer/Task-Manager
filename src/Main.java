import manager.Managers;
import manager.TaskManager;
import manager.file.FileBackedTasksManager;
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
// дополнительный вызов 3-х задач
        Task task4 = new Task("Задача 4", "Описание задачи 4"); //id = 9
        Task task5 = new Task("Задача 5", "Описание задачи 5"); //id = 10
        Task task6 = new Task("Задача 6", "Описание задачи 6"); //id = 11
        task4 = taskManager.createTask(task4);
        task5 = taskManager.createTask(task5);
        task6 = taskManager.createTask(task6);
//Делаем вызов элементов
        taskManager.getSubtaskById(subtask3.getId()); //id = 7
        taskManager.getSubtaskById(subtask2.getId()); //id = 6
        taskManager.getEpicById(epic1.getId());       //id = 4
        taskManager.getTaskById(task2.getId());       //id = 2
        taskManager.getTaskById(task5.getId());       //id = 10 НОВАЯ!!
        taskManager.getTaskById(task3.getId());       //id = 3
        taskManager.getSubtaskById(subtask1.getId()); //id = 5
        taskManager.getTaskById(task6.getId());       //id = 11 НОВАЯ!!
        taskManager.getEpicById(epic2.getId());       //id = 8
        taskManager.getTaskById(task1.getId());       //id = 1
        taskManager.getTaskById(task4.getId());       //id = 9 НОВАЯ!!
//Повторный вызов элементов
        taskManager.getSubtaskById(subtask2.getId()); //id = 6
        taskManager.getEpicById(epic1.getId());       //id = 4
        taskManager.getTaskById(task3.getId());       //id = 3
        taskManager.getSubtaskById(subtask3.getId()); //id = 7
//Удаление элементов
       /* taskManager.removeTaskById(task1.getId());    //удаление id = 1
        taskManager.removeEpicById(epic1.getId());    //удаление id = 4 (5,6,7);
        System.out.println();
        System.out.println();

        */
//Вывод истории
        List<Task> history = taskManager.getHistory();
        for (Task task : history) {
            System.out.println(task);
        }
        //Ожидаем вывод до повторов: 1 8 11 5 3 10 2 4 6 7; после повторов: 7 3 4 6 9 1 8 11 5 10; после удаления: 3 9 8 11 10
    }

}