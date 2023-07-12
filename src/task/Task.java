package task;

public class Task {
    protected Integer id;
    protected String name;
    protected String description;
    protected TaskStatus status;
    private static int generateId = 0;
    public Task(String name, String description) {
        this.id = getGenerateId();
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    private int getGenerateId() {
        return ++generateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
