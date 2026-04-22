package managers.json;

import managers.AnnounceManager;
import managers.CollectionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Product;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс, отвечающий за чтение/запись в Json файл.
 */
public class JsonManager {

    //fields
    private final File path;
    private final CollectionManager collection;
    private final Gson gson;
    private final AnnounceManager announceManager= AnnounceManager.getInstance();

    public JsonManager(File path, CollectionManager collection) {
        this.path = path;
        this.collection = collection;
        // Настраиваем GSON: подключаем адаптер для даты и делаем JSON читаемым
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JSONTime())
                .setPrettyPrinting()
                .create();
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

            if(products.removeIf(product -> !product.validate())) {
                announceManager.println("invalid.objects");
                save(products);
            }

            return products;

        } catch (Exception e) {
            announceManager.println("load.exception", e.getMessage());
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
            String jsonString = gson.toJson(collection);
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
            announceManager.println("save.success", path.getAbsolutePath());
        } catch (IOException e) {
            announceManager.println("save.exception");

            // Создаем резервный файл (например, backup_data.json)
            File backupPath = new File("backup_" + path.getName());
            try {
                writeToPath(backupPath, collection);
                announceManager.println("save.success", backupPath.getAbsolutePath());
            } catch (IOException ex) {
                announceManager.println("save.dException");
            }
        }
    }

}