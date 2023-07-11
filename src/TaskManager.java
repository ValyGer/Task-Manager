import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

public interface TaskManager {

    // Обработка task
    public Task createTask(Task task);
    public ArrayList<Task> getAllTasks();
    public void removeAllTasks();
    public Task getTaskById(Integer id);
    public void removeTaskById(Integer id);
    public void updateTask(Task task);

    // Обработка epic
    public Epic createEpic(Epic epic);
    public ArrayList<Epic> getAllEpics();
    public void removeAllEpics();
    public Epic getEpicById(Integer id);
    public void removeEpicById(Integer id);
    public void updateEpic(Epic epic);

    // ОБработка subtask
    public Subtask createSubtask(Subtask subtask);
    public ArrayList<Subtask> getAllSubtasks();
    public void removeAllSubtasks();
    public void removeSubtaskById(Integer id);
    public Subtask getSubtaskById(Integer id);
    public void updateSubtask(Subtask subtask);
}