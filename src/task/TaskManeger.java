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

    public Task getById(Integer id) { // получение задачи по идентифекатору
        return taskList.get(id);
    }
    public void removeById(Integer id) { // удаление задачи по идентификатору
        taskList.remove(id);
    }




}
