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

    // ОБработка Задач task
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
    public void updateTask(Task task){
        Task saved = taskList.get(task.getId());
        if (saved == null) {
            return;
        }
            taskList.put(task.getId(), task);
    }

    // Обработка Задач epic
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
    public void updateEpic(Subtask subtask){
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Integer> listSubtaskId = epic.getSubtaskId();
        boolean isChangeStatus = false;
        for (int id: listSubtaskId) {
            String status = subtaskList.get(id).getStatus();
            // получили информацию по статусу подзадачи
            //if (status.equals("NEW")
        }


//        if (saved == null) {
//            return;
//        }
//        epicList.put(epic.getId(), epic);
    }
    public ArrayList<Subtask> getListSubtaskInEpic(Integer id){
        Epic epic = epicList.get(id);
        ArrayList<Subtask> listSubtaskInEpic = new ArrayList<>();
        for (Integer subtaskId: epic.getSubtaskId()) {
            listSubtaskInEpic.add(subtaskList.get(subtaskId));
        }
        return listSubtaskInEpic;
    }


    // ОБработка Задач subtask
    public Subtask crieteSubtask(Subtask subtask){ // создание subtask и ее сохранение в Map
        int id = getGenerateId();
        subtask.setId(id);
        subtaskList.put(id,subtask);
        addSubtaskToEpicSubtaskList(subtask);
        return subtask;
    }
    public void addSubtaskToEpicSubtaskList (Subtask subtask){ // внесение ID subtask в список подзадач Epic
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Integer> listSubtaskId = epic.getSubtaskId();
        listSubtaskId.add(subtask.getId());
        epic.setSubtaskId(listSubtaskId);
    }
    public ArrayList<Task> getAllSubtasks(){ // получение списка всех subtask
        return new ArrayList(subtaskList.values());
    }
    public void removeAllSubtasks(){ // удавление списка всех subtask
        subtaskList.clear();
    }
    public Subtask getSubtaskById(Integer id) { // получение subtask по идентифекатору
        return subtaskList.get(id);
    }
    public void removeSubtaskById(Integer id) { // удаление subtask по идентификатору
        subtaskList.remove(id);
    }
    public void updateSubtask(Subtask subtask){
        Task saved = subtaskList.get(subtask.getId());
        if (saved == null) {
            return;
        }
        subtaskList.put(subtask.getId(), subtask);
        updateEpic(subtask);
    }
}

