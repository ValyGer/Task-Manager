package manager;

import task.Task;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    Node first;
    Node last;
    Map<Integer, Node> map = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        if (first == null) {
            return history;
        }
        Node node = first;
        while (node != null) {
            history.add(node.getValue());
            node = node.next;
        }
        return history;
    }
    @Override
    public void addTask(Task task) {
        linkLast(task);
    }
    public void linkLast(Task task) {
        Node node = new Node(task);
        if (first != null) {
            last.next = node;
            node.prev = last;
            last = node;
        } else {
            first = node;
            last = node;
        }
    }
    @Override
    public void remove(int id) {

    }

    static class Node {
        Task value;
        Node next;
        Node prev;

        public Node(Task value) {
            this.value = value;
        }

        public Task getValue() {
            return value;
        }
    }

}
