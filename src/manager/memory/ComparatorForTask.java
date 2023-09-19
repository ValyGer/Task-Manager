package manager.memory;

import task.Task;

import java.util.Comparator;

public class ComparatorForTask implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        if (o1.getId().equals(o2.getId())) {
            return 0;
        } else if ((o1.getStartTime().isAfter(o2.getStartTime())) || (o1.getStartTime().isEqual(o2.getStartTime()))) {
            return 1;
        } else {
            return -1;
        }
    }
}
