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
    public void addTask(Task task){
        Node node = new Node(task);
        if (map.get(task.getId()) == null){
            linkLast(node);
            map.put(task.getId(), node);
        } else {
            removeNode(map.get(task.getId()));
        }
    }
    public void linkLast(Node node){
        if (first != null){
            last.next = node;
            node.prev = last;
            last = node;
        } else {
            first = node;
            last = node;
        }
    }
    public void removeNode(Node node) {
        if((node.prev == first) && (node.next == last)){
            first = null;
            last = null;
        } else if((node.next == last) && (node.prev != first)){
            node.prev.next = null;
            last = node.prev;
            node.prev = null;
        } else if((node.prev == first) && (node.next != last)){
            node.next.prev = null;
            first = node.next;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.prev = null;
        }
    }
    @Override
    public void remove(int id){
        Node node = map.get(id);
        removeNode(node);
        map.remove(id);
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
