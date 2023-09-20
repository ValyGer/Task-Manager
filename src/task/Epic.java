package task;

import java.time.LocalDate;
import java.util.ArrayList;

public class Epic extends Task {

    private LocalDate endTime;
    private ArrayList<Subtask> subtasks;
    //Откорректирован список сабтасков. В списке сохраняем сами самбатаски а не их ID

    public Epic(String name, String description) {
        super(name, description);
        this.type = TaskType.EPIC;
    }

    public Epic(Integer id, String name, String description, TaskStatus status, TaskType type) {
        super(id, name, description, status, type, "2999.01.01", 0);
    }

    public ArrayList<Subtask> getSubtask() {
        return subtasks;
    }

    public void setSubtask(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
