import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

public class DirStats {

    static long largestSize = 0;
    static Path largestFile = null;
    static FileTime newestTime = FileTime.fromMillis(0);
    static Path newestFile = null;
    static long totalSize = 0;
    static int fileCount = 0;
    static Map<String, Integer> extCount = new TreeMap<>();

    public static void main(String[] args) {
        String path;
        if (args.length > 0) {
            path = args[0];
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Путь к директории: ");
            path = sc.nextLine().trim();
        }

        Path dir = Paths.get(path);
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            System.out.println("Директория не найдена: " + path);
            return;
        }

        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    fileCount++;
                    totalSize += attrs.size();

                    if (attrs.size() > largestSize) {
                        largestSize = attrs.size();
                        largestFile = file;
                    }

                    if (attrs.lastModifiedTime().compareTo(newestTime) > 0) {
                        newestTime = attrs.lastModifiedTime();
                        newestFile = file;
                    }

                    String name = file.getFileName().toString();
                    String ext = getExtension(name);
                    extCount.merge(ext, 1, Integer::sum);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.out.println("Не удалось прочитать: " + file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("Ошибка обхода: " + e.getMessage());
            return;
        }

        System.out.println("=== Статистика директории: " + path + " ===");
        System.out.println();

        System.out.println("Всего файлов: " + fileCount);
        System.out.println("Суммарный размер: " + formatSize(totalSize));
        System.out.println();

        System.out.println("Файлы по расширениям:");
        for (Map.Entry<String, Integer> entry : extCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();

        if (largestFile != null) {
            System.out.println("Самый большой файл:");
            System.out.println("  " + largestFile + " (" + formatSize(largestSize) + ")");
        }
        System.out.println();

        if (newestFile != null) {
            System.out.println("Самый новый файл:");
            System.out.println("  " + newestFile + " (" + newestTime + ")");
        }
    }

    static String getExtension(String name) {
        int dot = name.lastIndexOf('.');
        if (dot == -1 || dot == name.length() - 1) return "(без расширения)";
        return name.substring(dot + 1).toLowerCase();
    }

    static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024L * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024));
        return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
    }
}
