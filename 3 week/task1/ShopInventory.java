import java.util.*;
import java.util.stream.Collectors;

public class ShopInventory {

    private List<Product> products = new ArrayList<>();

    public void add(Product p) {
        products.add(p);
    }

    // Поиск по имени (частичное совпадение, без учёта регистра)
    public List<Product> searchByName(String keyword) {
        String lower = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    // Фильтрация по категории
    public List<Product> filterByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Сортировка по цене (по возрастанию)
    public List<Product> sortByPrice() {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    // Группировка по категориям
    public Map<String, List<Product>> groupByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    // Топ-3 самых дорогих
    public List<Product> top3MostExpensive() {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    // ─── Demo ──────────────────────────────────────────────
    public static void main(String[] args) {
        ShopInventory shop = new ShopInventory();

        shop.add(new Product(1, "Laptop",       "Electronics", 1200.00, 5));
        shop.add(new Product(2, "Smartphone",   "Electronics",  800.00, 12));
        shop.add(new Product(3, "Headphones",   "Electronics",  150.00, 30));
        shop.add(new Product(4, "Desk Chair",   "Furniture",    350.00, 8));
        shop.add(new Product(5, "Bookshelf",    "Furniture",    200.00, 4));
        shop.add(new Product(6, "Coffee Mug",   "Kitchen",       15.00, 50));
        shop.add(new Product(7, "Blender",      "Kitchen",      120.00, 10));
        shop.add(new Product(8, "Running Shoes", "Sports",      180.00, 20));

        System.out.println("=== All products ===");
        shop.products.forEach(System.out::println);

        System.out.println("\n=== Search by name: 'book' ===");
        shop.searchByName("book").forEach(System.out::println);

        System.out.println("\n=== Filter: Electronics ===");
        shop.filterByCategory("Electronics").forEach(System.out::println);

        System.out.println("\n=== Sorted by price ===");
        shop.sortByPrice().forEach(System.out::println);

        System.out.println("\n=== Grouped by category ===");
        shop.groupByCategory().forEach((cat, items) -> {
            System.out.println(cat + ":");
            items.forEach(p -> System.out.println("  " + p));
        });

        System.out.println("\n=== Top 3 most expensive ===");
        shop.top3MostExpensive().forEach(System.out::println);
    }
}
