package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;
    //Откорректирован список сабтасков. В списке сохраняем сами самбатаски а не их ID

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getSubtask() {
        return subtasks;
    }

    public void setSubtask(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
