package manager.memory;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> taskList = new HashMap<>();
    protected HashMap<Integer, Epic> epicList = new HashMap<>();
    protected HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    protected final Set<Task> prioritizedTasks = new TreeSet(new ComparatorForTask());
    protected final HistoryManager historyManager = Managers.getHistoryDefault();

    // возвращение списка приоритетных задач
    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    // Обработка task
    @Override
    public Task createTask(Task task) { // создание задачи и ее сохранение в Map
        taskList.put(task.getId(), task);
        historyManager.addTask(task);
        prioritizedTasks.add(task);
        return task;
    }

    @Override
    public ArrayList<Task> getAllTasks() { // получение списка всех задач
        return new ArrayList(taskList.values());
    }

    @Override
    public void removeAllTasks() { // удавление списка всех задач
        for (Task task : taskList.values()) {
            historyManager.remove(task.getId());
            prioritizedTasks.remove(task);
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
        prioritizedTasks.remove(taskList.get(id));
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
        historyManager.addTask(epic);
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
        for (Task epic : epicList.values()) {
            ArrayList<Subtask> listSubtasksInEpic = getListSubtaskInEpic(epic.getId());
            if (listSubtasksInEpic == null) {
                historyManager.remove(epic.getId());
            } else {
                for (Subtask subtask : listSubtasksInEpic) {
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
    public void removeEpicById(Integer id) { // удаление эпика по идентификатору!!!
        ArrayList<Subtask> listSubtasksInEpic = getListSubtaskInEpic(id);
        if (listSubtasksInEpic == null) {
            historyManager.remove(id);
        } else {
            for (Subtask subtask : listSubtasksInEpic) {
                historyManager.remove(subtask.getId());
                subtaskList.remove(subtask.getId());
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

    @Override
    public void updateEpicStatus(Subtask subtask) { // обновление статуса epic
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

    @Override
    public void updateEpicTime(Subtask subtask) {
        Epic epic = epicList.get(subtask.getEpicId());
        getStartTime(epic);
        getDuration(epic);

    }

    public void getStartTime(Epic epic) {
        ArrayList<Subtask> subtasks = getListSubtaskInEpic(epic.getId());
        LocalDate startTime = Task.START_TIME_TASK;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        if ((subtasks.isEmpty()) || (subtasks == null)) {
            epic.setStartTime(startTime.format(formatter));
        } else {
            for (Subtask subtask : subtasks) {
                if (subtask.getStartTime().isBefore(startTime)) {
                    startTime = subtask.getStartTime();
                }
            }
        }
        epic.setStartTime(startTime.format(formatter));
    }

    public void getDuration(Epic epic) {
        ArrayList<Subtask> subtasks = getListSubtaskInEpic(epic.getId());
        Duration durationOfEpic = Task.DURATION_TASK;
        if ((subtasks.isEmpty()) || (subtasks == null)) {
            epic.setDuration((int) durationOfEpic.toMinutes());
        } else {
            for (Subtask subtask : subtasks) {
                durationOfEpic = durationOfEpic.plus(subtask.getDuration());
            }
        }
        epic.setDuration((int) durationOfEpic.toMinutes());
    }

    public LocalDate setEndTime(Epic epic) {
        ArrayList<Subtask> subtasks = getListSubtaskInEpic(epic.getId());
        if (subtasks.isEmpty()) {
            return Task.START_TIME_TASK;
        } else {
            LocalDate endTime = epic.getStartTime();
            for (Subtask subtask : subtasks) {
                if (subtask.getEndTime() == Task.START_TIME_TASK) {
                    return Task.START_TIME_TASK;
                } else {
                    if (subtask.getEndTime().isAfter(endTime)) {
                        endTime = subtask.getStartTime();
                    }
                }
            }
            return endTime;
        }
    }

    // ОБработка subtask
    @Override
    public Subtask createSubtask(Subtask subtask) { // создание subtask и ее сохранение в Map
        subtaskList.put(subtask.getId(), subtask);
        prioritizedTasks.add(subtask);
        historyManager.addTask(subtask);
        addSubtaskToEpic(subtask);
        updateEpicStatus(subtask);
        updateEpicTime(subtask);
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
        for (Task subtask : subtaskList.values()) {
            historyManager.remove(subtask.getId());
            prioritizedTasks.remove(subtask);
        }
        for (Subtask subtask : subtaskList.values()) { // удаление subtask из списков epic
            removeSubtaskFromEpic(subtask.getId());
            updateEpicStatus(subtask);
            updateEpicTime(subtask);
        }
        subtaskList.clear();
    }

    @Override
    public void removeSubtaskById(Integer id) { // удаление subtask по идентификатору
        historyManager.remove(id);
        removeSubtaskFromEpic(id);
        prioritizedTasks.remove(subtaskList.get(id));
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
        updateEpicTime(subtask);
    }

    @Override
    public void updateSubtask(Subtask subtask) { // Обновление subtask
        Task saved = subtaskList.get(subtask.getId());
        if (saved == null) {
            return;
        }
        subtaskList.put(subtask.getId(), subtask);
        getListSubtaskInEpic(subtask.getEpicId()).set(getListSubtaskInEpic(subtask.getEpicId()).indexOf(saved), subtask);
        updateEpicStatus(subtask);
        updateEpicTime(subtask);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}