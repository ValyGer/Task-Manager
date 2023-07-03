import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    // Обработка task
    public Task createTask(Task task) { // создание задачи и ее сохранение в Map
        taskList.put(task.getId(), task);
        return task;
    }

    public ArrayList<Task> getAllTasks() { // получение списка всех задач
        return new ArrayList(taskList.values());
    }

    public void removeAllTasks() { // удавление списка всех задач
        taskList.clear();
    }

    public Task getTaskById(Integer id) { // получение задачи по идентифекатору
        return taskList.get(id);
    }

    public void removeTaskById(Integer id) { // удаление задачи по идентификатору
        taskList.remove(id);
    }

    public void updateTask(Task task) { // обновление task
        Task saved = taskList.get(task.getId());
        if (saved == null) {
            return;
        }
        taskList.put(task.getId(), task);
    }

    // Обработка epic
    public Epic createEpic(Epic epic) { // создание эпика и ее сохранение в Map
        epicList.put(epic.getId(), epic);
        ArrayList<Subtask> listSubtasks = new ArrayList<>();
        epic.setSubtask(listSubtasks);
        return epic;
    }

    public ArrayList<Epic> getAllEpics() { // получение списка всех эпиков
        return new ArrayList(epicList.values());
    }

    public void removeAllEpics() { // удавление списка всех эпиков
        epicList.clear();
    }

    public Epic getEpicById(Integer id) { // получение эпика по идентифекатору
        return epicList.get(id);
    }

    public void removeEpicById(Integer id) { // удаление задачи по идентификатору
        epicList.remove(id);
    }

    public void updateEpic(Epic epic) { // обновление epic
        Epic saved = epicList.get(epic.getId());
        if (saved == null) {
            return;
        }
        epicList.put(epic.getId(), epic);
    }

    private void updateEpicStatus(Subtask subtask) { // обновление статуса epic
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Subtask> listSubtasks = epic.getSubtask();
        if (listSubtasks.isEmpty()) {
            epic.setStatus("NEW");
        } else {
            int newValue = 0;
            int doneValue = 0;
            for (Subtask subtaskInEpic : listSubtasks) {
                // получили информацию по статусу подзадачи
                if (subtaskInEpic.getStatus().equals("NEW")) {
                    newValue++;
                } else if (subtaskInEpic.getStatus().equals("DONE")) {
                    doneValue++;
                }
            }
            if (newValue == listSubtasks.size()) {
                epic.setStatus("NEW");
            } else if (doneValue == listSubtasks.size()) {
                epic.setStatus("DONE");
            } else {
                epic.setStatus("IN_PROGRESS");
            }
        }
    }

    public ArrayList<Subtask> getListSubtaskInEpic(Integer id) { // получение списка subtask в epic
        Epic epic = epicList.get(id);
        ArrayList<Subtask> listSubtasksInEpic = new ArrayList<>();
        return listSubtasksInEpic;
    }

    // ОБработка subtask
    public Subtask createSubtask(Subtask subtask) { // создание subtask и ее сохранение в Map
        subtaskList.put(subtask.getId(), subtask);
        addSubtaskToEpic(subtask);
        updateEpicStatus(subtask);
        return subtask;
    }

    private void addSubtaskToEpic(Subtask subtask) { // внесение ID subtask в список подзадач Epic
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Subtask> listSubtasks = epic.getSubtask();
        listSubtasks.add(subtask);
        epic.setSubtask(listSubtasks);
    }

    public ArrayList<Subtask> getAllSubtasks() { // получение списка всех subtask
        return new ArrayList(subtaskList.values());
    }

    public void removeAllSubtasks() { // удавление списка всех subtask
        for (Subtask subtask : subtaskList.values()) { // удаление subtask из списков epic
            removeSubtaskFromEpic(subtask.getId());
            updateEpicStatus(subtask);
        }
        subtaskList.clear();
    }

    public void removeSubtaskById(Integer id) { // удаление subtask по идентификатору
        removeSubtaskFromEpic(id);
        updateEpicStatus(subtaskList.get(id));
        subtaskList.remove(id);
    }

    public Subtask getSubtaskById(Integer id) { // получение subtask по идентифекатору
        return subtaskList.get(id);
    }

    private void removeSubtaskFromEpic(Integer id) { // удаление ID subtask из списка подзадач Epic
        Subtask subtask = subtaskList.get(id);
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Subtask> listSubtasks = epic.getSubtask();
        listSubtasks.remove(subtask);
        epic.setSubtask(listSubtasks);
    }

    public void updateSubtask(Subtask subtask) { // Обновление subtask
        Task saved = subtaskList.get(subtask.getId());
        if (saved == null) {
            return;
        }
        subtaskList.put(subtask.getId(), subtask);
        updateEpicStatus(subtask);
    }
}