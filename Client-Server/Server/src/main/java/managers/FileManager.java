package managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Product;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * Менеджер файла.
 * Отвечает за загрузку и сохранение коллекции в JSON через GSON.
 */
public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String filePath;
    private final Gson gson;

    public FileManager(String filePath) {
        this.filePath = filePath;
        this.gson = buildGson();
    }

    /**
     * Загружает коллекцию из JSON-файла.
     */
    public PriorityQueue<Product> load() {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.warning("Collection file not found: " + filePath + ". Starting empty.");
            return new PriorityQueue<>();
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            Type type = new TypeToken<PriorityQueue<Product>>() {}.getType();
            PriorityQueue<Product> loaded = gson.fromJson(reader, type);

            if (loaded == null) {
                logger.warning("Collection file is empty or invalid JSON. Starting empty.");
                return new PriorityQueue<>();
            }

            // Фильтруем невалидные объекты
            PriorityQueue<Product> valid = new PriorityQueue<>();
            for (Product p : loaded) {
                if (p.validate()) {
                    valid.add(p);
                } else {
                    logger.warning("Invalid product skipped: id=" + p.getId());
                }
            }

            logger.info("Loaded " + valid.size() + " products from " + filePath);
            return valid;

        } catch (Exception e) {
            logger.severe("Failed to load collection: " + e.getMessage());
            return new PriorityQueue<>();
        }
    }

    /**
     * Сохраняет коллекцию в JSON-файл.
     */
    public void save(PriorityQueue<Product> collection) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {

            gson.toJson(collection, writer);
            logger.info("Collection saved to " + filePath);

        } catch (IOException e) {
            logger.severe("Failed to save collection: " + e.getMessage());
        }
    }

    /**
     * Строит GSON с поддержкой LocalDateTime и других нестандартных типов.
     */
    private Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                                new JsonPrimitive(src.format(FORMATTER)))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                                LocalDateTime.parse(json.getAsString(), FORMATTER))
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
}
