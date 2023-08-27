package manager.memory;

import manager.TaskManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }
    @AfterEach
    void clearAll() {
        taskManager.getAllTasks().clear();
        taskManager.getAllEpics().clear();
        taskManager.getAllSubtasks().clear();
        taskManager.getHistory().clear();
    }
}