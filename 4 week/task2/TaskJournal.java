import java.io.*;
import java.util.*;

public class TaskJournal {

    static class TaskNotFoundException extends Exception {
        TaskNotFoundException(String msg) { super(msg); }
    }

    static class InvalidTaskException extends Exception {
        InvalidTaskException(String msg) { super(msg); }
    }

    static class Task {
        int id;
        String title;
        String description;
        boolean completed;

        Task(int id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.completed = false;
        }

        @Override
        public String toString() {
            return "[" + id + "] " + (completed ? "[X]" : "[ ]") + " " + title + " — " + description;
        }
    }

    static List<Task> tasks = new ArrayList<>();
    static int nextId = 1;
    static final String FILE = "tasks.json";

    public static void main(String[] args) {
        loadTasks();

        Scanner sc = new Scanner(System.in);
        System.out.println("Журнал задач. Команды: add, list, complete, delete, exit");

        while (true) {
            System.out.print("> ");
            if (!sc.hasNextLine()) break;
            String cmd = sc.nextLine().trim().toLowerCase();

            try {
                switch (cmd) {
                    case "add":
                        addTask(sc);
                        break;
                    case "list":
                        listTasks();
                        break;
                    case "complete":
                        completeTask(sc);
                        break;
                    case "delete":
                        deleteTask(sc);
                        break;
                    case "exit":
                        saveTasks();
                        System.out.println("Сохранено. Выход.");
                        return;
                    default:
                        System.out.println("Неизвестная команда");
                }
            } catch (TaskNotFoundException | InvalidTaskException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        saveTasks();
    }

    static void addTask(Scanner sc) throws InvalidTaskException {
        System.out.print("Название: ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) throw new InvalidTaskException("Название не может быть пустым");

        System.out.print("Описание: ");
        String desc = sc.nextLine().trim();

        tasks.add(new Task(nextId++, title, desc));
        System.out.println("Задача добавлена");
    }

    static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Задач нет");
            return;
        }
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

    static void completeTask(Scanner sc) throws TaskNotFoundException, InvalidTaskException {
        System.out.print("ID задачи: ");
        int id = readId(sc);
        Task t = findById(id);
        t.completed = true;
        System.out.println("Задача #" + id + " выполнена");
    }

    static void deleteTask(Scanner sc) throws TaskNotFoundException, InvalidTaskException {
        System.out.print("ID задачи: ");
        int id = readId(sc);
        Task t = findById(id);
        tasks.remove(t);
        System.out.println("Задача #" + id + " удалена");
    }

    static int readId(Scanner sc) throws InvalidTaskException {
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new InvalidTaskException("Невалидный ID: " + line);
        }
    }

    static Task findById(int id) throws TaskNotFoundException {
        for (Task t : tasks) {
            if (t.id == id) return t;
        }
        throw new TaskNotFoundException("Задача с ID " + id + " не найдена");
    }

    static void saveTasks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            pw.println("[");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                String line = "  {\"id\":" + t.id
                        + ",\"title\":\"" + escape(t.title) + "\""
                        + ",\"description\":\"" + escape(t.description) + "\""
                        + ",\"completed\":" + t.completed + "}";
                if (i < tasks.size() - 1) line += ",";
                pw.println(line);
            }
            pw.println("]");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    static void loadTasks() {
        File f = new File(FILE);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{")) {
                    line = line.replace("{", "").replace("}", "").replace("[", "").replace("]", "").replace(",", "");
                    if (line.endsWith(",")) line = line.substring(0, line.length() - 1);

                    int id = extractInt(line, "\"id\":");
                    String title = extractString(line, "\"title\":\"");
                    String desc = extractString(line, "\"description\":\"");
                    boolean completed = line.contains("\"completed\":true");

                    Task t = new Task(id, title, desc);
                    t.completed = completed;
                    tasks.add(t);
                    if (id >= nextId) nextId = id + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
    }

    static int extractInt(String line, String key) {
        int start = line.indexOf(key) + key.length();
        int end = start;
        while (end < line.length() && Character.isDigit(line.charAt(end))) end++;
        return Integer.parseInt(line.substring(start, end));
    }

    static String extractString(String line, String key) {
        int start = line.indexOf(key) + key.length();
        int end = line.indexOf("\"", start);
        return line.substring(start, end);
    }

    static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
