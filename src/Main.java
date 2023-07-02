import task.Epic;
import task.Subtask;
import task.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        task1 = taskManager.crieteTask(task1);
        task2 = taskManager.crieteTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic1 = taskManager.crieteEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1","NEW", epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2","NEW", epic1.getId());
        subtask1 = taskManager.crieteSubtask(subtask1);
        subtask2 = taskManager.crieteSubtask(subtask2);

        Epic epic2= new Epic("Эпик 2", "Описание эпика 2");
        epic2 = taskManager.crieteEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3","NEW", epic2.getId());
        subtask3 = taskManager.crieteSubtask(subtask3);


        printListOfTasks(taskManager);
        Subtask modifiedSubtask = taskManager.getSubtaskById(subtask3.getId());
        modifiedSubtask.setStatus("IN_PROGRESS");
        taskManager.updateSubtask(modifiedSubtask);
        printListOfTasks(taskManager);

        taskManager.removeTaskById(task1.getId());
        taskManager.removeSubtaskById(subtask3.getId());
        printListOfTasks(taskManager);

    }
    public static void printListOfTasks (TaskManager taskManager) {
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
    }

}