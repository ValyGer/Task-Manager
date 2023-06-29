import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskManeger;

public class Main {
    public static void main(String[] args) {
        TaskManeger taskManeger = new TaskManeger();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2= new Epic("Эпик 2", "Описание эпика 2");

        task1 = taskManeger.crieteTask(task1);
        task2 = taskManeger.crieteTask(task2);

        epic1 = taskManeger.crieteEpic(epic1);
        epic2 = taskManeger.crieteEpic(epic2);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1","NEW", epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2","NEW", epic1.getId());

        subtask1 = taskManeger.crieteSubtask(subtask1);
        System.out.println(subtask1);
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
        System.out.println(taskManeger.getAllTasks());
        Task apdateTask = taskManeger.getTaskById(task1.getId());
        apdateTask.setStatus("IN_PROGRESS");
        taskManeger.undateTask(apdateTask);
        System.out.println(taskManeger.getAllTasks());
        System.out.println(taskManeger.getTaskById(2));
        taskManeger.removeTaskById(1);
        System.out.println(taskManeger.getAllTasks());
        taskManeger.removeAllTasks();
        System.out.println(taskManeger.getAllTasks());

        System.out.println();

         /*
        Тестирование subtask:
        1. создаем subtask 1 и 2,
        2. показываем список всех subtask,
        3. показываем subtask по id удаляем subtask,
        4. очищаем весь список subtask
        5. Модификация subtask
         */






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
        System.out.println(taskManeger.getAllEpics());

        System.out.println();
                System.out.println(taskManeger.getListSubtaskInEpic(3));
        System.out.println();

        System.out.println(taskManeger.getEpicById(3));
        taskManeger.removeEpicById(4);
        System.out.println(taskManeger.getAllEpics());
        taskManeger.removeAllEpics();
        System.out.println(taskManeger.getAllEpics());


    }
}