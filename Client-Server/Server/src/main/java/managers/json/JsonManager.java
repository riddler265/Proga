package managers.json;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import json.ServerResponse;
import managers.CollectionManager;
import model.Product;
import util.ToOutQueue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class JsonManager extends json.JsonManager {

    private final File path;
    private final CollectionManager collection;

    public JsonManager(File path, CollectionManager collection) {
        GSON = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JSONTime())
                .setPrettyPrinting()
                .create();
        this.path = path;
        this.collection = collection;
        // Настраиваем GSON: подключаем адаптер для даты и делаем JSON читаемым
    }

    private void announce(String key, String ... parameters) {
        ToOutQueue.addToOutQueue(null, key, parameters);
    }

    /**
     * Загружает коллекцию из файла и синхронизирует счетчик id.
     * @return {@link List} {@link Product}.
     */
    private List<Product> loadProducts() {
        try (Scanner fileScanner = new Scanner(path)) {

            // 1. Читаем всё содержимое файла в одну строку
            StringBuilder jsonContent = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                jsonContent.append(fileScanner.nextLine());
            }

            // 2. Описываем тип для GSON
            var listType = new TypeToken<ArrayList<Product>>(){}.getType();

            // 3. Десериализация из накопленной строки
            List<Product> products = GSON.fromJson(jsonContent.toString(), listType);

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

            if(products.removeIf(product -> !product.validate())) {
                announce("invalid.objects");
                save(products);
            }

            return products;

        } catch (Exception e) {
            announce("load.exception");
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

    private void writeToPath(File targetPath, Collection<Product> collection) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetPath))) {
            String jsonString = GSON.toJson(collection);
            writer.write(jsonString);
        }
    }
    /**
     * Сохранение продуктов из коллекции в файл.
     */
    public void save(Collection<Product> collection) {
        try {
            // Пытаемся сохранить по основному пути
            writeToPath(this.path, collection);
            announce("save.success");
        } catch (IOException e) {
            announce("save.exception");

            // Создаем резервный файл (например, backup_data.json)
            File backupPath = new File("backup_" + path.getName());
            try {
                writeToPath(backupPath, collection);
                announce("save.success");
            } catch (IOException ex) {
                announce("save.dException");
            }
        }
    }
}
