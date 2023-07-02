import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int generateId = 0;
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    public int getGenerateId(){
        return ++generateId;
    }

    // Обработка task
    public Task crieteTask(Task task){ // создание задачи и ее сохранение в Map
       int id = getGenerateId();
       task.setId(id);
       taskList.put(id,task);
       return task;
    }
    public ArrayList<Task> getAllTasks(){ // получение списка всех задач
       return new ArrayList(taskList.values());
    }
    public void removeAllTasks(){ // удавление списка всех задач
        taskList.clear();
    }
    public Task getTaskById(Integer id) { // получение задачи по идентифекатору
        return taskList.get(id);
    }
    public void removeTaskById(Integer id) { // удаление задачи по идентификатору
        taskList.remove(id);
    }
    public void updateTask(Task task){ // обновление task
        Task saved = taskList.get(task.getId());
        if (saved == null) {
            return;
        }
            taskList.put(task.getId(), task);
    }

    // Обработка epic
    public Epic crieteEpic(Epic epic){ // создание эпика и ее сохранение в Map
        int id = getGenerateId();
        epic.setId(id);
        epicList.put(id,epic);
        ArrayList<Integer> listSubtaskId = new ArrayList<>();
        epic.setSubtaskId(listSubtaskId);
        return epic;
    }
    public ArrayList<Epic> getAllEpics(){ // получение списка всех эпиков
        return new ArrayList(epicList.values());
    }
    public void removeAllEpics(){ // удавление списка всех эпиков
        epicList.clear();
    }
    public Epic getEpicById(Integer id) { // получение эпика по идентифекатору
        return epicList.get(id);
    }
    public void removeEpicById(Integer id) { // удаление задачи по идентификатору
        epicList.remove(id);
    }
    public void updateEpic(Subtask subtask){ // обновление epic
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Integer> listSubtaskId = epic.getSubtaskId();
        if (listSubtaskId.isEmpty()) {
            epic.setStatus("NEW");
        } else {
            int newValue = 0;
            int doneValue = 0;
            for (int id: listSubtaskId) {
                // получили информацию по статусу подзадачи
                if (subtaskList.get(id).getStatus().equals("NEW")) {
                    newValue++;
                } else if (subtaskList.get(id).getStatus().equals("DONE")) {
                    doneValue++;
                }
            }
            if (newValue == listSubtaskId.size()) {
                epic.setStatus("NEW");
            } else if (doneValue == listSubtaskId.size()) {
                epic.setStatus("DONE");
            } else {
                epic.setStatus("IN_PROGRESS");
            }
        }
    }
    public ArrayList<Subtask> getListSubtaskInEpic(Integer id){ // получение списка subtask в epic
        Epic epic = epicList.get(id);
        ArrayList<Subtask> listSubtaskInEpic = new ArrayList<>();
        for (Integer subtaskId: epic.getSubtaskId()) {
            listSubtaskInEpic.add(subtaskList.get(subtaskId));
        }
        return listSubtaskInEpic;
    }

    // ОБработка subtask
    public Subtask crieteSubtask(Subtask subtask){ // создание subtask и ее сохранение в Map
        int id = getGenerateId();
        subtask.setId(id);
        subtaskList.put(id,subtask);
        addSubtaskToEpic(subtask);
        updateEpic(subtask);
        return subtask;
    }
    public void addSubtaskToEpic (Subtask subtask){ // внесение ID subtask в список подзадач Epic
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Integer> listSubtaskId = epic.getSubtaskId();
        listSubtaskId.add(subtask.getId());
        epic.setSubtaskId(listSubtaskId);
    }
    public ArrayList<Task> getAllSubtasks(){ // получение списка всех subtask
        return new ArrayList(subtaskList.values());
    }
    public void removeAllSubtasks(){ // удавление списка всех subtask
        for (Subtask subtask: subtaskList.values()) { // удаление subtask из списков epic
            removeSubtaskFromEpic(subtask.getId());
            updateEpic(subtask);
        }
        subtaskList.clear();
    }
    public void removeSubtaskById(Integer id) { // удаление subtask по идентификатору
        removeSubtaskFromEpic(id);
        updateEpic(subtaskList.get(id));
        subtaskList.remove(id);
    }
    public Subtask getSubtaskById(Integer id) { // получение subtask по идентифекатору
        return subtaskList.get(id);
    }
    public void removeSubtaskFromEpic(Integer id) { // удаление ID subtask из списка подзадач Epic
        Subtask subtask = subtaskList.get(id);
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Integer> listSubtaskId = epic.getSubtaskId();
        listSubtaskId.remove(subtask.getId());
        epic.setSubtaskId(listSubtaskId);
    }
    public void updateSubtask(Subtask subtask){ // Обновление subtask
        Task saved = subtaskList.get(subtask.getId());
        if (saved == null) {
            return;
        }
        subtaskList.put(subtask.getId(), subtask);
        updateEpic(subtask);
    }
}