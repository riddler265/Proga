package jsonmanager;

import collectionManager.CollectionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import product.Product;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        // 1. Проверяем, существует ли файл вообще
        if (!file.exists()) {
            System.out.println("File don`t exist!");
            System.exit(1);
        }

        // Используем try-with-resources для автоматического закрытия потока чтения
        try (Reader reader = new FileReader(file)) {

            // 2. Описываем тип коллекции для GSON (т.к. это List<Product>, а не просто объект)
            Type listType = new TypeToken<ArrayList<Product>>(){}.getType();

            // 3. Десериализация
            List<Product> products = gson.fromJson(reader, listType);

            // 4. Важный момент: если файл пустой, GSON вернет null
            if (products == null) {
                return new ArrayList<>();
            }

            // 5. Синхронизация ID
            if (!products.isEmpty()) {
                // Находим максимальный ID в загруженном списке
                int maxId = 0;
                for (Product p : products) {
                    if (p.getId() > maxId) {
                        maxId = p.getId();
                    }
                }
                // Передаем этот ID в класс Product, чтобы счетчик стартовал с maxId + 1
                Product.updateCurrentId(maxId);
            }

            return products;

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге JSON (возможно, файл поврежден): " + e.getMessage());
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