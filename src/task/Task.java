package task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    protected Integer id;
    protected String name;
    protected String description;
    protected TaskStatus status;
    protected TaskType type;
    private static int generateId = 0;
    protected Duration duration; //продолжительность задачи
    protected LocalDate startTime; //дата, когда предполагается приступить к выполнению задачи

    public static final Duration DURATION_TASK = Duration.ofMinutes(0);
    public static final LocalDate START_TIME_TASK = LocalDate.of(2999, 01, 01);

    public Task(String name, String description) {
        this.id = getGenerateId();
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.type = TaskType.TASK;
        this.duration = DURATION_TASK;
        this.startTime = START_TIME_TASK;
    }

    public Task(String name, String description, String startTime, int minutes) {
        this.id = getGenerateId();
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.type = TaskType.TASK;
        this.duration = Duration.ofMinutes(minutes);
        this.startTime = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public Task(Integer id, String name, String description, TaskStatus status, TaskType type, String startTime, int minutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.duration = Duration.ofMinutes(minutes);
        this.startTime = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    private int getGenerateId() {
        return ++generateId;
    }

    public static void setGenerateId(int generateId) {
        Task.generateId = generateId;
    }

    public TaskType getType() {
        return type;
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

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = Duration.ofMinutes(duration);
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public LocalDate getEndTime() {
        return startTime.plusDays(duration.toMinutes());
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
