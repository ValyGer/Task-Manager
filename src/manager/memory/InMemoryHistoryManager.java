package manager.memory;

import manager.HistoryManager;
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
        Node node = last; // first
        while (node != null) {
            history.add(node.getValue());
            node = node.prev;  //next
        }
        return history;
    }
    @Override
    public void addTask(Task task){
        checkingLengthHistory(task); // перед добавлением новой записи в историю проверяем ее длину
        if (map.get(task.getId()) == null){
            Node node = new Node(task);
            linkLast(node);
            map.put(task.getId(), node);
        } else {
            removeNode(map.get(task.getId()));
            linkLast(map.get(task.getId()));
        }
    }
    public void checkingLengthHistory (Task task){ // функция проверки длины истории
        if ((map.size() >= 10) && ((map.get(task.getId()) != null))){
            map.remove(first.value.getId());
            removeNode(first);
        }
    }

    public void linkLast(Node node){
        if (first != null){
            last.next = node;
            node.prev = last;
        } else {
            first = node;
        }
        last = node;
    }
    public void removeNode(Node node) {
        if((node.prev == null) && (node.next == null)){
            first = null;
            last = null;
        } else if((node.next == null) && (node.prev != null)){
            node.prev.next = null;
            last = node.prev;
            node.prev = null;
        } else if((node.prev == null) && (node.next != null)){
            node.next.prev = null;
            first = node.next;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
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
