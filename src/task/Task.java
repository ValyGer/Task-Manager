package task;

import com.google.gson.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    public static class TaskDeSerializer implements JsonDeserializer<Task> {
        @Override
        public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonObject jsonObject = json.getAsJsonObject();

            Task task = new Task(
                    jsonObject.get("id").getAsInt(),
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    TaskStatus.valueOf(jsonObject.get("status").getAsString()),
                    TaskType.valueOf(jsonObject.get("type").getAsString()),
                    localDataToString(jsonObject),
                    durationToInteger(jsonObject)
            );
            return task;
        }
    }
    public static String localDataToString(JsonObject jsonObject) {
        JsonObject dataTime = jsonObject.get("startTime").getAsJsonObject();
        String startTimeResult = dataTime.get("year").getAsString();
        Integer startTimeMonth = dataTime.get("month").getAsInt();
        Integer startTimeDay = dataTime.get("day").getAsInt();
            if (startTimeMonth <= 9) {
                startTimeResult = startTimeResult + ".0" + startTimeMonth.toString();
            } else {
                startTimeResult = startTimeResult + "." + startTimeMonth.toString();
            }
            if (startTimeDay <= 9) {
                startTimeResult = startTimeResult + ".0" + startTimeDay.toString();
            } else {
                startTimeResult = startTimeResult + "." + startTimeDay.toString();
            }
        return startTimeResult;
    }
    public static int durationToInteger(JsonObject jsonObject) {
        JsonObject duration = jsonObject.get("duration").getAsJsonObject();
        return duration.get("seconds").getAsInt() / 60;
    }
    public static class TaskSerializer implements JsonSerializer<Task> {
        @Override
        public JsonElement serialize(Task task, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();

            result.add("id", context.serialize(task.getId()));
            result.add("name", context.serialize(task.getName()));
            result.add("description", context.serialize(task.getDescription()));
            result.add("status", context.serialize(task.getStatus()));
            result.add("type", context.serialize(task.getType()));
            result.add("startTime", context.serialize(task.getStartTime()));
            result.add("duration", context.serialize(task.getDuration()));
            return result;
        }
    }
    public static class EpicDeSerializer implements JsonDeserializer<Epic> {
        @Override
        public Epic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonObject jsonObject = json.getAsJsonObject();

            Epic epic = new Epic(
                    jsonObject.get("id").getAsInt(),
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    TaskStatus.valueOf(jsonObject.get("status").getAsString()),
                    TaskType.valueOf(jsonObject.get("type").getAsString())
            );
            return epic;
        }
    }
    public static class EpicSerializer implements JsonSerializer<Epic> {
        @Override
        public JsonElement serialize(Epic epic, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();

            result.add("id", context.serialize(epic.getId()));
            result.add("name", context.serialize(epic.getName()));
            result.add("description", context.serialize(epic.getDescription()));
            result.add("status", context.serialize(epic.getStatus()));
            result.add("type", context.serialize(epic.getType()));
            return result;
        }
    }
    public static class SubtaskDeSerializer implements JsonDeserializer<Subtask> {
        @Override
        public Subtask deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonObject jsonObject = json.getAsJsonObject();

            Subtask subtask = new Subtask(
                    jsonObject.get("id").getAsInt(),
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    TaskStatus.valueOf(jsonObject.get("status").getAsString()),
                    TaskType.valueOf(jsonObject.get("type").getAsString()),
                    localDataToString(jsonObject),
                    durationToInteger(jsonObject),
                    jsonObject.get("epicId").getAsInt()
            );
            return subtask;
        }
    }
    public static class SubtaskSerializer implements JsonSerializer<Subtask> {
        @Override
        public JsonElement serialize(Subtask subtask, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();

            result.add("id", context.serialize(subtask.getId()));
            result.add("name", context.serialize(subtask.getName()));
            result.add("description", context.serialize(subtask.getDescription()));
            result.add("status", context.serialize(subtask.getStatus()));
            result.add("type", context.serialize(subtask.getType()));
            result.add("startTime", context.serialize(subtask.getStartTime()));
            result.add("duration", context.serialize(subtask.getDuration()));
            result.add("epicId", context.serialize(subtask.getEpicId()));
            return result;
        }
    }
    public static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        @Override
        public void write(final JsonWriter jsonWriter, LocalDate localDate) throws IOException {
            jsonWriter.value(localDate.format(formatter));
        }
        @Override
        public LocalDate read(final JsonReader jsonReader) throws IOException {
            return LocalDate.parse(jsonReader.nextString(), formatter);
        }
    }
    public static class DurationAdapter extends TypeAdapter<Duration> {
        @Override
        public void write(final JsonWriter jsonWriter, final Duration duration) throws IOException {
            jsonWriter.value(duration.toSeconds() / 60);
        }
        @Override
        public Duration read(final JsonReader jsonReader) throws IOException {
            return Duration.ofSeconds((jsonReader.nextInt()) * 60);
        }
    }
}
