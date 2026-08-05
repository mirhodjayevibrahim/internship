import java.util.*;
import java.util.stream.Collectors;

public class TextAnalysis {

    private String text;
    private Map<String, Integer> wordFreq;

    public TextAnalysis(String text) {
        this.text = text;
        this.wordFreq = new HashMap<>();
        analyze();
    }

    private void analyze() {
        // Убираем знаки препинания, разбиваем по пробелам
        String[] words = text.toLowerCase()
                .replaceAll("[^a-zA-Zа-яА-ЯёЁ0-9\\s]", "")
                .split("\\s+");

        for (String w : words) {
            if (!w.isEmpty())
                wordFreq.merge(w, 1, Integer::sum);
        }
    }

    // Частота каждого слова
    public Map<String, Integer> wordFrequency() {
        return new HashMap<>(wordFreq);
    }

    // Топ-10 самых частых слов
    public List<Map.Entry<String, Integer>> top10Words() {
        return wordFreq.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    // Уникальные слова (встречаются ровно 1 раз)
    public Set<String> uniqueWords() {
        return wordFreq.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    // Средняя длина слова
    public double averageWordLength() {
        return wordFreq.keySet().stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);
    }

    // Самое длинное слово
    public String longestWord() {
        return wordFreq.keySet().stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    // ─── Demo ──────────────────────────────────────────────
    public static void main(String[] args) {
        String text = """
                Java is a popular programming language. Java is used for web development,
                mobile development, and desktop applications. Java is known for its
                portability across platforms. Programming in Java is fun and powerful.
                Many developers choose Java because Java is versatile and well documented.
                The language supports object oriented programming paradigms.
                Developers love Java for its rich ecosystem and strong community support.
                Learning Java opens doors to many career opportunities in software development.
                """;

        TextAnalysis analysis = new TextAnalysis(text);

        System.out.println("=== Частота слов ===");
        analysis.wordFrequency().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.printf("  %-15s %d%n", e.getKey(), e.getValue()));

        System.out.println("\n=== Топ-10 слов ===");
        analysis.top10Words().forEach(e ->
                System.out.printf("  %-15s %d%n", e.getKey(), e.getValue()));

        System.out.println("\n=== Уникальные слова ===");
        System.out.println("  " + analysis.uniqueWords());

        System.out.printf("%n=== Средняя длина слова: %.1f символов ===%n", analysis.averageWordLength());

        System.out.println("\n=== Самое длинное слово ===");
        System.out.println("  " + analysis.longestWord());
    }
}
