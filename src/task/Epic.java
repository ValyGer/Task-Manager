package task;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> subtaskId;

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }
}
