import Maneger.InMemoryTaskManager;
import Maneger.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        task1 = taskManager.createTask(task1);
        task2 = taskManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic1 = taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId());
        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        epic2 = taskManager.createEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epic2.getId());
        subtask3 = taskManager.createSubtask(subtask3);

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


        System.out.println();
        System.out.println();
        LinkedList<Task> history = taskManager.getHistory();
        for (Task task: history) {
            System.out.println(task);
        }

    }

    public static void printListOfTasks(TaskManager taskManager) {
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
    }
}