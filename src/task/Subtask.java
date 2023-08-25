package task;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(Integer id, String name, String description, TaskStatus status, TaskType type, int epicId) {
        super(id, name, description, status, type);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
