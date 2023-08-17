package manager.file;

import manager.InMemoryTaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    @Override
    public Task createTask(Task task) {
        return super.createTask(task);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
    }

    @Override
    public Task getTaskById(Integer id) {
        return super.getTaskById(id);
    }

    @Override
    public void removeTaskById(Integer id) {
        super.removeTaskById(id);
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
    }

    @Override
    public Epic createEpic(Epic epic) {
        return super.createEpic(epic);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
    }

    @Override
    public Epic getEpicById(Integer id) {
        return super.getEpicById(id);
    }

    @Override
    public void removeEpicById(Integer id) {
        super.removeEpicById(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
    }

    @Override
    public ArrayList<Subtask> getListSubtaskInEpic(Integer id) {
        return super.getListSubtaskInEpic(id);
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        return super.createSubtask(subtask);
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
    }

    @Override
    public void removeSubtaskById(Integer id) {
        super.removeSubtaskById(id);
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        return super.getSubtaskById(id);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }
}
