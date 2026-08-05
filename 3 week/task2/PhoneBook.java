import java.io.*;
import java.util.*;

public class PhoneBook {

    // имя -> список номеров
    private Map<String, List<String>> contacts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    // Добавить контакт
    public void addContact(String name) {
        contacts.putIfAbsent(name, new ArrayList<>());
    }

    // Добавить номер к контакту
    public void addNumber(String name, String number) {
        contacts.computeIfAbsent(name, k -> new ArrayList<>()).add(number);
    }

    // Удалить контакт
    public void removeContact(String name) {
        if (contacts.remove(name) != null)
            System.out.println("  Контакт '" + name + "' удалён.");
        else
            System.out.println("  Контакт '" + name + "' не найден.");
    }

    // Удалить номер у контакта
    public void removeNumber(String name, String number) {
        List<String> nums = contacts.get(name);
        if (nums != null && nums.remove(number))
            System.out.println("  Номер " + number + " удалён у " + name);
        else
            System.out.println("  Номер не найден.");
    }

    // Поиск по имени
    public List<String> searchByName(String name) {
        return contacts.getOrDefault(name, Collections.emptyList());
    }

    // Обратный поиск по номеру
    public String searchByNumber(String number) {
        for (var entry : contacts.entrySet()) {
            if (entry.getValue().contains(number))
                return entry.getKey();
        }
        return null;
    }

    // Вывод в алфавитном порядке (TreeMap уже сортирует)
    public void printAll() {
        contacts.forEach((name, nums) ->
                System.out.println("  " + name + " -> " + nums));
    }

    // Сохранение в файл
    public void saveToFile(String filename) throws IOException {
        try (PrintWriter w = new PrintWriter(new FileWriter(filename))) {
            contacts.forEach((name, nums) ->
                    w.println(name + ";" + String.join(",", nums)));
        }
        System.out.println("  Сохранено в " + filename);
    }

    // Загрузка из файла
    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(";", 2);
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String[] nums = parts[1].split(",");
                    for (String n : nums)
                        addNumber(name, n.trim());
                }
            }
        }
        System.out.println("  Загружено из " + filename);
    }

    // ─── Demo ──────────────────────────────────────────────
    public static void main(String[] args) throws IOException {
        PhoneBook pb = new PhoneBook();

        pb.addNumber("Alice",   "+1-555-1001");
        pb.addNumber("Alice",   "+1-555-1002");
        pb.addNumber("Bob",     "+1-555-2001");
        pb.addNumber("Charlie", "+1-555-3001");
        pb.addNumber("Diana",   "+1-555-4001");

        System.out.println("=== Все контакты (алфавитный порядок) ===");
        pb.printAll();

        System.out.println("\n=== Поиск по имени: Alice ===");
        System.out.println("  " + pb.searchByName("Alice"));

        System.out.println("\n=== Обратный поиск: +1-555-3001 ===");
        String found = pb.searchByNumber("+1-555-3001");
        System.out.println("  Номер принадлежит: " + found);

        System.out.println("\n=== Добавляем номер Bob ===");
        pb.addNumber("Bob", "+1-555-2002");
        pb.printAll();

        System.out.println("\n=== Удаляем номер Alice ===");
        pb.removeNumber("Alice", "+1-555-1001");
        pb.printAll();

        System.out.println("\n=== Удаляем контакт Charlie ===");
        pb.removeContact("Charlie");
        pb.printAll();

        // Сохраняем и загружаем
        String file = "phonebook.txt";
        pb.saveToFile(file);

        PhoneBook pb2 = new PhoneBook();
        pb2.loadFromFile(file);
        System.out.println("\n=== Загруженная книга ===");
        pb2.printAll();
    }
}
