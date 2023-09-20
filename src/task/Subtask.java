package task;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(String name, String description, TaskStatus status, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.status = status;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(String name, String description, String startTime, int duration, int epicId) {
        super(name, description, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(Integer id, String name, String description, TaskStatus status, TaskType type, String startTime, int minutes, int epicId) {
        super(id, name, description, status, type, startTime, minutes);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", epic='" + epicId + '\'' +
                '}';
    }
}
