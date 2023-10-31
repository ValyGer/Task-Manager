package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class HttpTaskServer {

    public static final int PORT = 8080;
    private HttpServer server;
    private TaskManager manager;
    private Gson gson;


    public HttpTaskServer(TaskManager manager) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);
        this.manager = manager;
        gson = Managers.getGson();

    }

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);
        this.manager = Managers.getDefault();
        gson = Managers.getGson();
    }
    public void handler(HttpExchange httpExchange) {
        try{
            System.out.println("Обрабатывается end-point " + httpExchange.getRequestURI());
            String path = httpExchange.getRequestURI().toString().replaceFirst("/tasks", "");
            String method = httpExchange.getRequestMethod();
            switch (method) {
                case "GET":
                    processingGetRequest(path, httpExchange);
                    break;
                case "POST":
                    processingPostRequest(path, httpExchange);
                    break;
                case "DELETE":
                    processingDeleteRequest(path, httpExchange);
                    break;
                default:
                    System.out.println("Некорректный запрос. Проверьте и повторите попытку.");
                    httpExchange.sendResponseHeaders(405, 0);
            }
        } catch (Exception exception) {
            System.out.println("Произвошла ошибка");
        } finally {
            httpExchange.close();
        }
    }

    private void processingDeleteRequest(String path, HttpExchange httpExchange) throws IOException {
        if (Pattern.matches("^/task$", path)) {   // DELETE/tasks/task
            manager.removeAllTasks();
            System.out.println("Все задачи удалены");
            httpExchange.sendResponseHeaders(200, 0);
        } else if (Pattern.matches("^/task/\\?id=\\d+$", path)) {    // DELETE/tasks/task/?id=1
            String pathId = path.replaceFirst("/task/\\?id=", "");
            int id = parsePathId(pathId);
            if (id != -1) {
                manager.removeTaskById(id);
                System.out.println("Удалена задача id = " + id);
                httpExchange.sendResponseHeaders(200, 0);
            } else {
                System.out.println("Введен не корректный id " + id);
                httpExchange.sendResponseHeaders(405, 0);
            }

        } else if (Pattern.matches("^/subtask$", path)) {   // DELETE/tasks/subtask
            manager.removeAllSubtasks();
            System.out.println("Все сабтаски удалены");
            httpExchange.sendResponseHeaders(200, 0);
        } else if (Pattern.matches("^/subtask/\\?id=\\d+$", path)) {    // DELETE/tasks/subtask/?id=1
            String pathId = path.replaceFirst("/subtask/\\?id=", "");
            int id = parsePathId(pathId);
            if (id != -1) {
                manager.removeSubtaskById(id);
                System.out.println("Удален сабтаск id = " + id);
                httpExchange.sendResponseHeaders(200, 0);
            } else {
                System.out.println("Введен не корректный id " + id);
                httpExchange.sendResponseHeaders(405, 0);
            }

        } else if (Pattern.matches("^/epic$", path)) {   // DELETE/tasks/epic
                manager.removeAllEpics();
                System.out.println("Все эпики удалены");
                httpExchange.sendResponseHeaders(200, 0);
        } else if (Pattern.matches("^/epic/\\?id=\\d+$", path)) {    // DELETE/tasks/epic/?id=1
            String pathId = path.replaceFirst("/epic/\\?id=", "");
            int id = parsePathId(pathId);
            if (id != -1) {
                manager.removeEpicById(id);
                System.out.println("Удален эпик id = " + id);
                httpExchange.sendResponseHeaders(200, 0);
            } else {
                System.out.println("Введен не корректный id " + id);
                httpExchange.sendResponseHeaders(405, 0);
            }
        }
    }

    private void processingPostRequest(String path, HttpExchange httpExchange) throws IOException {
        if (Pattern.matches("^/task/$", path)) {   // POST/tasks/task
            final String json = new String(httpExchange.getRequestBody().readAllBytes());
            final Task task = gson.fromJson(json, Task.class);
            boolean isCheck = false; // проверка на наличие задачи с тем же id что и в теле запроса
            ArrayList<Task> tasks = manager.getAllTasks();
            int numberTask = -1;
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId() == task.getId()) {
                    isCheck = true;
                    numberTask = i;
                }
            }
            if (isCheck) {    // обновление задачи
                manager.updateTask(task);
                if (manager.getAllTasks().get(numberTask).getName().equals(task.getName())
                        && manager.getAllTasks().get(numberTask).getDescription().equals(task.getDescription())
                        && manager.getAllTasks().get(numberTask).getStatus().equals(task.getStatus())
                        && manager.getAllTasks().get(numberTask).getStartTime().equals(task.getStartTime())
                        && manager.getAllTasks().get(numberTask).getType().equals(task.getType())
                        && manager.getAllTasks().get(numberTask).getDuration().equals(task.getDuration())) {
                    System.out.println("Задача " + task.getId() + " успешно обновлена.");
                    httpExchange.sendResponseHeaders(200, 0);
                } else {
                    System.out.println("Не удалось обновить задачу " + task.getId()
                            + ". Возможно время выполнения задачи пересекается с другими задачами.");
                    httpExchange.sendResponseHeaders(405, 0);
                }
            } else {
                manager.createTask(task);    // создание новой задачи в случае ее отсутвия в списке
                System.out.println("Задача добавлена.");
                httpExchange.sendResponseHeaders(200, 0);
            }
        } else
        if (Pattern.matches("^/subtask/$", path)) {   // POST/tasks/subtask
            final String json = new String(httpExchange.getRequestBody().readAllBytes());
            final Subtask subtask = gson.fromJson(json, Subtask.class);
            boolean isCheck = false; // проверка на наличие сабтаска с тем же id что и в теле запроса
            ArrayList<Subtask> subtasks = manager.getAllSubtasks();
            int numberSubtask = -1;
            for (int i = 0; i < subtasks.size(); i++) {
                if (subtasks.get(i).getId() == subtask.getId()) {
                    isCheck = true;
                    numberSubtask = i;
                }
            }
            if (isCheck) {    // обновление сабтаска
                manager.updateSubtask(subtask);
                if (manager.getAllSubtasks().get(numberSubtask).getName().equals(subtask.getName())
                        && manager.getAllSubtasks().get(numberSubtask).getDescription().equals(subtask.getDescription())
                        && manager.getAllSubtasks().get(numberSubtask).getStatus().equals(subtask.getStatus())
                        && manager.getAllSubtasks().get(numberSubtask).getStartTime().equals(subtask.getStartTime())
                        && manager.getAllSubtasks().get(numberSubtask).getType().equals(subtask.getType())
                        && manager.getAllSubtasks().get(numberSubtask).getDuration().equals(subtask.getDuration())
                        && manager.getAllSubtasks().get(numberSubtask).getEpicId() == (subtask.getEpicId())) {
                    System.out.println("Задача " + subtask.getId() + " успешно обновлена.");
                    httpExchange.sendResponseHeaders(200, 0);
                } else {
                    System.out.println("Не удалось обновить задачу " + subtask.getId()
                            + ". Возможно время выполнения задачи пересекается с другими задачами.");
                    httpExchange.sendResponseHeaders(405, 0);
                }
            } else {
                manager.createSubtask(subtask);    // создание нового сабтаска в случае ее отсутвия в списке
                System.out.println("Задача добавлена.");
                httpExchange.sendResponseHeaders(200, 0);
            }
        } else
        if (Pattern.matches("^/epic/$", path)) {   // POST/tasks/epic
            final String json = new String(httpExchange.getRequestBody().readAllBytes());
            final Epic epic = gson.fromJson(json, Epic.class);
            boolean isCheck = false; // проверка на наличие эпика с тем же id что и в теле запроса
            ArrayList<Epic> epics = manager.getAllEpics();
            int numberEpic = -1;
            for (int i = 0; i < epics.size(); i++) {
                if (epics.get(i).getId() == epic.getId()) {
                    isCheck = true;
                    numberEpic = i;
                }
            }
            if (isCheck) {    // обновление задачи
                manager.updateEpic(epic);
                if (manager.getAllEpics().get(numberEpic).getName().equals(epic.getName())
                        && manager.getAllEpics().get(numberEpic).getDescription().equals(epic.getDescription())
                        && manager.getAllEpics().get(numberEpic).getStatus().equals(epic.getStatus())
                        && manager.getAllEpics().get(numberEpic).getType().equals(epic.getType())) {
                    System.out.println("Задача " + epic.getId() + " успешно обновлена.");
                    httpExchange.sendResponseHeaders(200, 0);
                } else {
                    System.out.println("Не удалось обновить задачу " + epic.getId()
                            + ". Возможно время выполнения задачи пересекается с другими задачами.");
                    httpExchange.sendResponseHeaders(405, 0);
                }
            } else {
                manager.createEpic(epic);    // создание новой задачи в случае ее отсутвия в списке
                System.out.println("Задача добавлена.");
                httpExchange.sendResponseHeaders(200, 0);
            }
        } else {
            System.out.println("Не удалось добавить эпик. Некорретный запрос.");
            httpExchange.sendResponseHeaders(405, 0);
        }
    }

    private void processingGetRequest (String path, HttpExchange httpExchange) throws IOException {
        if (Pattern.matches("^$", path)) {      // GET/tasks
            String response = gson.toJson(manager.getPrioritizedTasks());
            sendText(httpExchange, response);
        } else if (Pattern.matches("^/task$", path)) {   // GET/tasks/task
            String response = gson.toJson(manager.getAllTasks());
            sendText(httpExchange, response);
        } else if (Pattern.matches("^/task/\\?id=\\d+$", path)) {    // GET/tasks/task/?id=1
            String pathId = path.replaceFirst("/task/\\?id=", "");
            int id = parsePathId(pathId);
            if (id != -1) {
                String response = gson.toJson(manager.getTaskById(id));
                sendText(httpExchange, response);
            } else {
                System.out.println("Введен не корректный id " + id);
                httpExchange.sendResponseHeaders(405, 0);
            }

        } else if (Pattern.matches("^/subtask$", path)) {   // GET/tasks/subtask
            String response = gson.toJson(manager.getAllSubtasks());
            sendText(httpExchange, response);
        } else if (Pattern.matches("^/subtask/\\?id=\\d+$", path)) {    // GET/tasks/subtask/?id=1
            String pathId = path.replaceFirst("/subtask/\\?id=", "");
            int id = parsePathId(pathId);
            if (id != -1) {
                String response = gson.toJson(manager.getSubtaskById(id));
                sendText(httpExchange, response);
            } else {
                System.out.println("Введен не корректный id " + id);
                httpExchange.sendResponseHeaders(405, 0);
            }
        } else if (Pattern.matches("^/subtask/epic/\\d+$", path)) {       // GET/tasks/subtask/epic/?id=1
            String pathId = path.replaceFirst("/subtask/epic/", "");
            int id = parsePathId(pathId);
            if (id != -1) {
                String response = gson.toJson(manager.getTaskById(id));
                sendText(httpExchange, response);
            } else {
                System.out.println("Введен не корректный id " + id);
                httpExchange.sendResponseHeaders(405, 0);
            }

        } else if (Pattern.matches("^/epic$", path)) {   // GET/tasks/epic
            String response = gson.toJson(manager.getAllEpics());
            sendText(httpExchange, response);
        } else if (Pattern.matches("^/epic/\\?id=\\d+$", path)) {    // GET/tasks/epic/?id=1
            String pathId = path.replaceFirst("/epic/\\?id=", "");
            int id = parsePathId(pathId);
            if (id != -1) {
                String response = gson.toJson(manager.getEpicById(id));
                sendText(httpExchange, response);
            } else {
                System.out.println("Введен не корректный id " + id);
                httpExchange.sendResponseHeaders(405, 0);
            }

        } else if (Pattern.matches("^/history$", path)) {    // GET/tasks/history
            String response = gson.toJson(manager.getHistory());
            sendText(httpExchange, response);
        } else {
            System.out.println("Некорректный запрос. Проверьте и повторите попытку.");
            httpExchange.sendResponseHeaders(405, 0);
        }
    }

    private int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private void sendText(HttpExchange httpExchange, String response) throws IOException {
        byte[] bytes = response.getBytes(Charset.defaultCharset());
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(200, bytes.length);
        httpExchange.getResponseBody().write(bytes);
    }

    public void start() {
        server.start();
        System.out.println("HTTP сервер начал работать c портом " + PORT);
    }

    public void stop(int delay) {
        server.stop(delay);
        System.out.println("HTTP сервер завершил работу");
    }

    public static void main(String[] arg) throws IOException {
        final HttpTaskServer server = new HttpTaskServer();
        server.start();
        server.stop(1);
    }
}
