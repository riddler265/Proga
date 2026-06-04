package commands;

import com.google.gson.JsonObject;
import enums.Commands;
import json.JsonManager;
import managers.CollectionManager;
import model.Coordinates;
import model.Person;
import model.Product;
import model.enums.Color;
import model.enums.UnitOfMeasure;
import network.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Модуль обработки полученных команд.
 * Разбирает Request, вызывает нужный метод CollectionManager, возвращает Response.
 */
public class CommandProcessor {

    private static final Logger logger = Logger.getLogger(CommandProcessor.class.getName());

    private final CollectionManager collectionManager;

    public CommandProcessor(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Обрабатывает запрос и возвращает ответ.
     */
    public Response process(String request) {
        Commands command = JsonManager.getCommand(request);
        logger.info("Processing command: " + command);

        Product product = new Product();
        Coordinates coordinates = new Coordinates();
        Person person = new Person();

        JsonObject jProduct = JsonManager.getProduct(request);
        JsonObject jCoordinates = null;
        if (jProduct.has("coordinates") && !jProduct.get("coordinates").isJsonNull()) {
            jCoordinates = jProduct.getAsJsonObject("coordinates");
        }
        JsonObject jPerson = null;
        if (jProduct.has("owner") && !jProduct.get("owner").isJsonNull()) {
            jPerson = jProduct.getAsJsonObject("owner");
        }

        try {
            switch (command) {
                case INFO -> {
                    return new Response();
                }
                case SHOW -> {
                    System.out.println("a");
                    return new Response(true, "sadasd", new ArrayList<>(collectionManager.getCollection()));
                }
                case ADD -> {
                    coordinates.setX(jCoordinates.get("x").getAsInt());
                    coordinates.setY(jCoordinates.get("y").getAsInt());

                    if (jPerson != null) {
                        person.setName(jPerson.get("name").getAsString());
                        person.setBirthday(LocalDateTime.parse(jPerson.get("birthday").getAsString(), Person.formatter));
                        person.setHeight(jPerson.get("height").getAsFloat());
                        person.setPassportID(jPerson.get("passportID").getAsString());
                        person.setHairColor(Color.valueOf(jPerson.get("hairColor").getAsString().toUpperCase()));
                    } else person = null;


                    product.setName(jProduct.get("name").getAsString());
                    product.setPrice(jProduct.get("price").getAsFloat());
                    product.setPartNumber(jProduct.get("partNumber").getAsString());
                    product.setManufactureCost(jProduct.get("manufactureCost").getAsFloat());
                    product.setUnitOfMeasure(UnitOfMeasure.valueOf(jProduct.get("unitOfMeasure").getAsString().toUpperCase()));
                    product.setCoordinates(coordinates);
                    product.setOwner(person);

                    collectionManager.add(product);

                    System.out.println("a");

                    return new Response(true, "idi nahui");

                }
                case UPDATE_ID -> {
                    int id = JsonManager.getParameter(request).getAsInt();

                    product = collectionManager.getProductById(id);
                    person = product.getOwner();
                    coordinates = product.getCoordinates();

                    if (product != null) {

                        if (jCoordinates.has("x")) {
                            coordinates.setX(jCoordinates.get("x").getAsInt());
                        }
                        if (jCoordinates.has("y")) {
                            coordinates.setY(jCoordinates.get("y").getAsInt());
                        }

                        if (jPerson != null) {
                            if (jPerson.has("name") && !jPerson.get("name").getAsString().equals("detach everything as it is")) {

                                // Только в этом случае считываем новые данные и перезаписываем владельца
                                person.setName(jPerson.get("name").getAsString());

                                if (jPerson.has("birthday")) {
                                    person.setBirthday(LocalDateTime.parse(jPerson.get("birthday").getAsString(), Person.formatter));
                                }
                                if (jPerson.has("height")) {
                                    person.setHeight(jPerson.get("height").getAsFloat());
                                }
                                if (jPerson.has("passportID")) {
                                    person.setPassportID(jPerson.get("passportID").getAsString());
                                }
                                if (jPerson.has("hairColor")) {
                                    person.setHairColor(Color.valueOf(jPerson.get("hairColor").getAsString().toUpperCase()));
                                }
                            }
                        } product.setOwner(null);


                        if (jProduct.has("name")) {
                            product.setName(jProduct.get("name").getAsString());
                        }
                        if (jProduct.has("price")) {
                            product.setPrice(jProduct.get("price").getAsFloat());
                        }
                        if (jProduct.has("partNumber")) {
                            product.setPartNumber(jProduct.get("partNumber").getAsString());
                        }
                        if (jProduct.has("manufactureCost")) {
                            product.setManufactureCost(jProduct.get("manufactureCost").getAsFloat());
                        }
                        if (jProduct.has("unitOfMeasure")) {
                            product.setUnitOfMeasure(UnitOfMeasure.valueOf(jProduct.get("unitOfMeasure").getAsString().toUpperCase()));
                        }
                    }
                }

                case REMOVE_BY_ID -> {
                    int parameter = JsonManager.getParameter(request).getAsInt();

                    product = collectionManager.getProductById(parameter);

                    if (product != null) {
                        collectionManager.remove(product);
                        return new Response();
                    } else return new Response();
                }

                case CLEAR -> {
                    collectionManager.clear();
                    return new Response();
                }
                case ADD_IF_MIN -> {
                    coordinates.setX(jCoordinates.get("x").getAsInt());
                    coordinates.setY(jCoordinates.get("y").getAsInt());

                    person.setName(jPerson.get("name").getAsString());
                    person.setBirthday(LocalDateTime.parse(jPerson.get("birthday").getAsString(), Person.formatter));
                    person.setHeight(jPerson.get("height").getAsFloat());
                    person.setPassportID(jPerson.get("passportID").getAsString());
                    person.setHairColor(Color.valueOf(jPerson.get("hairColor").getAsString().toUpperCase()));


                    product.setName(jProduct.get("name").getAsString());
                    product.setPrice(jProduct.get("price").getAsFloat());
                    product.setPartNumber(jProduct.get("partNumber").getAsString());
                    product.setManufactureCost(jProduct.get("manufactureCost").getAsFloat());
                    product.setUnitOfMeasure(UnitOfMeasure.valueOf(jProduct.get("unitOfMeasure").getAsString().toUpperCase()));
                    product.setCoordinates(coordinates);
                    product.setOwner(person);

                    if (product.compareTo(collectionManager.getLowestProduct()) < 0) {
                        collectionManager.add(product);
                        return new Response(true, "idi nahui id min");
                    } else return new Response(false, "asdsad");
                }
                case REMOVE_GREATER -> {
                    int id = JsonManager.getParameter(request).getAsInt();

                    product = collectionManager.getProductById(id);

                    if (product != null) {
                        final Product targetProduct = product;
                        collectionManager.getCollection().removeIf(Cproduct -> Cproduct.compareTo(targetProduct) > 0);
                        return new Response(true, "asd");
                    } else return new Response();
                }
                case REMOVE_ALL_BY_PRICE -> {
                    final float price = JsonManager.getParameter(request).getAsFloat();

                    collectionManager.getCollection().removeIf(cProduct ->
                            cProduct.getPrice() != null && cProduct.getPrice() == price
                    );

                    return new Response();
                }
                case FILTER_LESS_THAN_MANUFACTURE_COST -> {
                    final float manufactureCost = JsonManager.getParameter(request).getAsFloat();

                    return new Response(true, "sd", collectionManager.getCollection().stream()
                            // Оставляем только те продукты, у которых стоимость производства меньше заданной
                            .filter(Cproduct -> Cproduct.getManufactureCost() < manufactureCost)
                            // Собираем результат в изменяемый список List<Product>
                            .collect(Collectors.toList()));
                }
                case FILTER_GREATER_THAN_MANUFACTURE_COST -> {
                    final float manufactureCost = JsonManager.getParameter(request).getAsFloat();

                    return new Response(true, "sd", collectionManager.getCollection().stream()
                            // Оставляем только те продукты, у которых стоимость производства меньше заданной
                            .filter(Cproduct -> Cproduct.getManufactureCost() > manufactureCost)
                            // Собираем результат в изменяемый список List<Product>
                            .collect(Collectors.toList()));
                }
                default -> {
                    return null;
                }
            };
        } catch (Exception e) {
            logger.warning("Error processing command '" + command + "': " + e.getMessage());
            return error("Ошибка при выполнении команды: " + e.getMessage());
        }
        return new Response(false, "pfgos");
    }


    private Response ok(String message) {
        return new Response(true, message);
    }

    private Response error(String message) {
        return new Response(false, message);
    }
}
