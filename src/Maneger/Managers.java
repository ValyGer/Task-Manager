package Maneger;

public final class Managers {

    private Managers(){}

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getHistoryDefault() {
        return new InMemoryHistoryManager();
    }
}
