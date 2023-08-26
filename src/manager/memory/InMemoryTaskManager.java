package manager.memory;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> taskList = new HashMap<>();
    protected HashMap<Integer, Epic> epicList = new HashMap<>();
    protected HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    protected final HistoryManager historyManager = Managers.getHistoryDefault();

    // Обработка task
    @Override
    public Task createTask(Task task) { // создание задачи и ее сохранение в Map
        taskList.put(task.getId(), task);
        return task;
    }

    @Override
    public ArrayList<Task> getAllTasks() { // получение списка всех задач
        return new ArrayList(taskList.values());
    }

    @Override
    public void removeAllTasks() { // удавление списка всех задач
        for(Task task: taskList.values()){
            historyManager.remove(task.getId());
        }
        taskList.clear();
    }

    @Override
    public Task getTaskById(Integer id) { // получение задачи по идентифекатору
        Task task = taskList.get(id);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public void removeTaskById(Integer id) { // удаление задачи по идентификатору
        historyManager.remove(id);
        taskList.remove(id);
    }

    @Override
    public void updateTask(Task task) { // обновление task
        Task saved = taskList.get(task.getId());
        if (saved == null) {
            return;
        }
        taskList.put(task.getId(), task);
    }


    // Обработка epic
    @Override
    public Epic createEpic(Epic epic) { // создание эпика и ее сохранение в Map
        epicList.put(epic.getId(), epic);
        ArrayList<Subtask> listSubtasks = new ArrayList<>();
        epic.setSubtask(listSubtasks);
        return epic;
    }

    @Override
    public ArrayList<Epic> getAllEpics() { // получение списка всех эпиков
        return new ArrayList(epicList.values());
    }

    @Override
    public void removeAllEpics() { // удавление списка всех эпиков
        for(Task epic: epicList.values()){
            ArrayList<Subtask> listSubtasksInEpic = getListSubtaskInEpic(epic.getId());
            if (listSubtasksInEpic == null) {
                historyManager.remove(epic.getId());
            } else {
                for (Subtask subtask: listSubtasksInEpic){
                    historyManager.remove(subtask.getId());
                }
            }
            historyManager.remove(epic.getId());
        }
        epicList.clear();
    }

    @Override
    public Epic getEpicById(Integer id) { // получение эпика по идентифекатору
        Epic epic = epicList.get(id);
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public void removeEpicById(Integer id) { // удаление эпика по идентификатору!!! проверять
        ArrayList<Subtask> listSubtasksInEpic = getListSubtaskInEpic(id);
        if (listSubtasksInEpic == null) {
            historyManager.remove(id);
        } else {
           for (Subtask subtask: listSubtasksInEpic){
               historyManager.remove(subtask.getId());
           }
        }
        historyManager.remove(id);
        epicList.remove(id);
    }

    @Override
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
            epic.setStatus(TaskStatus.NEW);
        } else {
            int newValue = 0;
            int doneValue = 0;
            for (Subtask subtaskInEpic : listSubtasks) {
                // получили информацию по статусу подзадачи
                if (subtaskInEpic.getStatus().equals(TaskStatus.NEW)) {
                    newValue++;
                } else if (subtaskInEpic.getStatus().equals(TaskStatus.DONE)) {
                    doneValue++;
                }
            }
            if (newValue == listSubtasks.size()) {
                epic.setStatus(TaskStatus.NEW);
            } else if (doneValue == listSubtasks.size()) {
                epic.setStatus(TaskStatus.DONE);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }

    @Override
    public ArrayList<Subtask> getListSubtaskInEpic(Integer id) { // получение списка subtask в epic
        Epic epic = epicList.get(id);
        ArrayList<Subtask> listSubtasksInEpic = epic.getSubtask();
        return listSubtasksInEpic;
    }


    // ОБработка subtask
    @Override
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

    @Override
    public ArrayList<Subtask> getAllSubtasks() { // получение списка всех subtask
        return new ArrayList(subtaskList.values());
    }

    @Override
    public void removeAllSubtasks() { // удавление списка всех subtask
        for(Task subtask: subtaskList.values()){
            historyManager.remove(subtask.getId());
        }
        for (Subtask subtask : subtaskList.values()) { // удаление subtask из списков epic
            removeSubtaskFromEpic(subtask.getId());
            updateEpicStatus(subtask);
        }
        subtaskList.clear();
    }

    @Override
    public void removeSubtaskById(Integer id) { // удаление subtask по идентификатору
        historyManager.remove(id);
        removeSubtaskFromEpic(id);
        updateEpicStatus(subtaskList.get(id));
        subtaskList.remove(id);
    }

    @Override
    public Subtask getSubtaskById(Integer id) { // получение subtask по идентифекатору
        Subtask subtask = subtaskList.get(id);
        historyManager.addTask(subtask);
        return subtask;
    }

    private void removeSubtaskFromEpic(Integer id) { // удаление ID subtask из списка подзадач Epic
        Subtask subtask = subtaskList.get(id);
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Subtask> listSubtasks = epic.getSubtask();
        listSubtasks.remove(subtask);
        epic.setSubtask(listSubtasks);
    }

    @Override
    public void updateSubtask(Subtask subtask) { // Обновление subtask
        Task saved = subtaskList.get(subtask.getId());
        if (saved == null) {
            return;
        }
        subtaskList.put(subtask.getId(), subtask);
        updateEpicStatus(subtask);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}