package jsonmanager;

import collectionManager.CollectionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import product.Product;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс, отвечающий за чтение/запись в Json файл.
 */
public class JsonManager {

    //fields
    private final String filePath;
    private final CollectionManager collection;
    private final Gson gson;

    public JsonManager(String filePath, CollectionManager collection) {
        this.filePath = filePath;
        this.collection = collection;
        // Настраиваем GSON: подключаем адаптер для даты и делаем JSON читаемым
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JSONTime())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Загружает коллекцию из файла и синхронизирует счетчик id.
     */
    private List<Product> loadProducts() {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Файла не существует!");
            System.exit(1);
        }

        // Используем try-with-resources для Scanner
        try (Scanner fileScanner = new Scanner(file)) {

            // 1. Читаем всё содержимое файла в одну строку
            StringBuilder jsonContent = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                jsonContent.append(fileScanner.nextLine());
            }

            // 2. Описываем тип для GSON
            var listType = new TypeToken<ArrayList<Product>>(){}.getType();

            // 3. Десериализация из накопленной строки
            List<Product> products = gson.fromJson(jsonContent.toString(), listType);

            if (products == null) {
                return new ArrayList<>();
            }

            // 4. Синхронизация ID (твоя логика остается прежней)
            if (!products.isEmpty()) {
                int maxId = products.stream()
                        .mapToInt(Product::getId)
                        .max()
                        .orElse(0);
                Product.updateCurrentId(maxId);
            }

            return products;

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Ошибка при чтении или парсинге: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Загрузка продуктов из файла в коллекцию.
     */
    public void load() {
        for (Product product : loadProducts()) {
            collection.addToCollection(product);
        }
    }

    /**
     * Сохранение продуктов из коллекции в файл.
     */
    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new JSONTime())
                .create();

        // 2. Используем PrintWriter для записи в файл
        // try-with-resources сам закроет файл после записи
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath))) {

            // 3. Сериализуем коллекцию в строку
            String jsonString = gson.toJson(collection.getCollection());

            // 4. Записываем строку в буфер
            writer.write(jsonString);

            // ВАЖНО: При использовании BufferedWriter данные могут "застрять" в буфере.
            // Метод close() (который вызовется сам благодаря try-with-resources)
            // автоматически вызовет flush() и все допишет.

            System.out.println("Данные успешно сохранены в файл через BufferedWriter.\n");

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

}