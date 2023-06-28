import task.Epic;
import task.Task;
import task.TaskManeger;

public class Main {
    public static void main(String[] args) {
        TaskManeger taskManeger = new TaskManeger();

        Task task1 = new Task("Переезд", "Сбор вещей, погрузка, выгрузка, разбор");
        Task task2 = new Task("Переезд", "Сбор вещей, погрузка, выгрузка, разбор");

        Epic epic1 = new Epic("Ремонт", "Описание эпика 1");

        task1 = taskManeger.crieteTask(task1);
        task2 = taskManeger.crieteTask(task2);

        /*
        Тестирование:
        1. создаем задачи 1 и 2,
        2. показываем список всех задач,
        3. показываем задачу по id удаляем задачу,
        4. очищаем весь список задач
        5. Модификация задачи
         */
        System.out.println(task1);
        System.out.println(task2);
        System.out.println(taskManeger.getAllTasks());
        Task apdateTask = taskManeger.getById(task1.getId());
        apdateTask.setStatus("IN_PROGRESS");
        System.out.println(taskManeger.getAllTasks());
        System.out.println(taskManeger.getById(2));
        taskManeger.removeById(1);
        System.out.println(taskManeger.getAllTasks());
        taskManeger.removeAllTasks();
        System.out.println(taskManeger.getAllTasks());

    }
}