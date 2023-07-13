import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

//      Создаем объекты Задачи, Эпики, Подзадачи
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        Task task3 = new Task("Задача 3", "Описание задачи 3");
        task1 = taskManager.createTask(task1);
        task2 = taskManager.createTask(task2);
        task3 = taskManager.createTask(task3);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic1 = taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epic1.getId());
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        subtask3 = taskManager.createSubtask(subtask3);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        epic2 = taskManager.createEpic(epic2);
        Subtask subtask4 = new Subtask("Подзадача 4", "Описание подзадачи 4", epic2.getId());
        Subtask subtask5 = new Subtask("Подзадача 5", "Описание подзадачи 5", epic2.getId());
        subtask4 = taskManager.createSubtask(subtask4);
        subtask5 = taskManager.createSubtask(subtask5);

        Epic epic3 = new Epic("Эпик 3", "Описание эпика 3");
        epic3 = taskManager.createEpic(epic3);
        Subtask subtask6 = new Subtask("Подзадача 6", "Описание подзадачи 6", epic3.getId());

//      Выполнение методов TaskManager
        printListOfTasks(taskManager);
        Subtask modifiedSubtask = taskManager.getSubtaskById(subtask3.getId());
        modifiedSubtask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(modifiedSubtask);
        printListOfTasks(taskManager);

        taskManager.removeTaskById(task1.getId());
        taskManager.removeSubtaskById(subtask3.getId());
        printListOfTasks(taskManager);

        Epic newEpic2 = taskManager.getEpicById(epic2.getId());
        newEpic2.setName("Новый Эпик 2");
        newEpic2.setDescription("Описание нового эпика 2");
        taskManager.updateEpic(newEpic2);
        printListOfTasks(taskManager);

        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getSubtaskById(subtask4.getId());
        taskManager.getSubtaskById(subtask5.getId());
        taskManager.getSubtaskById(subtask6.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getTaskById(task2.getId());  // 11-ый по счету просмотр задачи.

        System.out.println();
        System.out.println();

        LinkedList<Task> history = taskManager.getHistory();
        for (Task task : history) {
            System.out.println(task);
        }
//      Изменен порядок заполннения истории.
//      Ожидаем в истории "Задача 2" - 1 элемент, "Подзадача 3" - 10 элемент
    }

    public static void printListOfTasks(TaskManager taskManager) {
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
    }
}