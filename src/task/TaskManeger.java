package task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManeger {
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
    public void undateTask(Task task){
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
        ArrayList<Integer> listSutaskId = new ArrayList<>();
        epic.setSubtaskId(listSutaskId);
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
    public void undateEpic(Epic epic){
        Epic saved = epicList.get(epic.getId());
        if (saved == null) {
            return;
        }
        epicList.put(epic.getId(), epic);
    }
    public ArrayList<Subtask> getListSubtaskInEpic(Integer id){
        Epic epic = epicList.get(id);
        ArrayList<Subtask> listSutaskInEpic = new ArrayList<>();
        for (Integer subtaskId: epic.getSubtaskId()) {
            listSutaskInEpic.add(subtaskList.get(subtaskId));
        }
        return listSutaskInEpic;
    }


    // ОБработка Задач subtask
    public Subtask crieteSubtask(Subtask subtask){ // создание задачи и ее сохранение в Map
        int id = getGenerateId();
        subtask.setId(id);
        subtaskList.put(id,subtask);
        Epic epic = epicList.get(subtask.getEpicId());
        ArrayList<Integer> listSutaskId = epic.getSubtaskId();
        listSutaskId.add(id);
        epic.setSubtaskId(listSutaskId);
        return subtask;
    }
//    public ArrayList<Task> getAllTasks(){ // получение списка всех задач
//        return new ArrayList(taskList.values());
//    }
//    public void removeAllTasks(){ // удавление списка всех задач
//        taskList.clear();
//    }
//    public Task getTaskById(Integer id) { // получение задачи по идентифекатору
//        return taskList.get(id);
//    }
//    public void removeTaskById(Integer id) { // удаление задачи по идентификатору
//        taskList.remove(id);
//    }
//    public void undateTask(Task task){
//        Task saved = taskList.get(task.getId());
//        if (saved == null) {
//            return;
//        }
//        taskList.put(task.getId(), task);
//    }


}
