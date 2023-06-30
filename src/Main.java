import task.Epic;
import task.Subtask;
import task.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2= new Epic("Эпик 2", "Описание эпика 2");

        task1 = taskManager.crieteTask(task1);
        task2 = taskManager.crieteTask(task2);

        epic1 = taskManager.crieteEpic(epic1);
        epic2 = taskManager.crieteEpic(epic2);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1","NEW", epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2","NEW", epic1.getId());

        subtask1 = taskManager.crieteSubtask(subtask1);
        subtask2 = taskManager.crieteSubtask(subtask2);


        /*
        Тестирование task:
        1. создаем задачи 1 и 2,
        2. показываем список всех задач,
        3. показываем задачу по id удаляем задачу,
        4. очищаем весь список задач
        5. Модификация задачи
         */
        System.out.println(task1);
        System.out.println(task2);
        System.out.println(taskManager.getAllTasks());
        Task apdateTask = taskManager.getTaskById(task1.getId());
        apdateTask.setStatus("IN_PROGRESS");
        taskManager.updateTask(apdateTask);
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getTaskById(2));
        taskManager.removeTaskById(1);
        System.out.println(taskManager.getAllTasks());
        taskManager.removeAllTasks();
        System.out.println(taskManager.getAllTasks());

        System.out.println();

         /*
        Тестирование subtask:
        1. создаем subtask 1 и 2,
        2. показываем список всех subtask,
        3. показываем subtask по id удаляем subtask,
        4. очищаем весь список subtask
        5. Модификация subtask
         */
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getSubtaskById(subtask1.getId()));
        Subtask apdateSubtask = taskManager.getSubtaskById(subtask1.getId());
        apdateSubtask.setStatus("IN_PROGRESS");
        taskManager.updateSubtask(apdateSubtask);
        System.out.println(taskManager.getAllSubtasks());
        taskManager.removeSubtaskById(subtask2.getId());
        System.out.println(taskManager.getAllSubtasks());
        taskManager.removeAllSubtasks();
        System.out.println(taskManager.getAllSubtasks());

        System.out.println();

        /*
        Тестирование epic:
        1. создаем epic 1 и 2,
        2. показываем список всех epic,
        3. показываем epic по id удаляем epic,
        4. очищаем весь список epic
        5. Модификация epic
         */
        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(taskManager.getAllEpics());

        System.out.println();
                System.out.println(taskManager.getListSubtaskInEpic(epic1.getId()));
        System.out.println();

        System.out.println(taskManager.getEpicById(epic1.getId()));
        taskManager.removeEpicById(epic2.getId());
        System.out.println(taskManager.getAllEpics());
        taskManager.removeAllEpics();
        System.out.println(taskManager.getAllEpics());


    }
}