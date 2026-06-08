package commands;

import com.google.gson.JsonObject;
import model.Coordinates;
import model.Person;
import model.Product;
import model.enums.Color;
import model.enums.UnitOfMeasure;

import java.time.LocalDateTime;

/**
 * Вспомогательный класс для сборки объектов Product, Coordinates, Person из JSON.
 * Используется командами чтобы не дублировать код парсинга.
 */
public class ProductParser {

    /**
     * Собирает полный объект Product из трёх JSON-частей.
     * Координаты обязательны, владелец — опционален.
     */
    public static Product buildProduct(JsonObject jProduct, JsonObject jCoordinates, JsonObject jPerson) {
        Product product = new Product();

        Coordinates coordinates = parseCoordinates(jCoordinates);

        product.setName(jProduct.get("name").getAsString());
        product.setManufactureCost(jProduct.get("manufactureCost").getAsFloat());
        product.setUnitOfMeasure(UnitOfMeasure.valueOf(jProduct.get("unitOfMeasure").getAsString().toUpperCase()));
        product.setCoordinates(coordinates);

        if (jProduct.has("price") && !jProduct.get("price").isJsonNull()) {
            product.setPrice(jProduct.get("price").getAsFloat());
        }
        if (jProduct.has("partNumber") && !jProduct.get("partNumber").isJsonNull()) {
            product.setPartNumber(jProduct.get("partNumber").getAsString());
        }

        if (jPerson != null) {
            product.setOwner(parsePerson(jPerson));
        }

        return product;
    }

    /**
     * Парсит координаты из JSON.
     */
    public static Coordinates parseCoordinates(JsonObject jCoordinates) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(jCoordinates.get("x").getAsInt());
        coordinates.setY(jCoordinates.get("y").getAsInt());
        return coordinates;
    }

    /**
     * Парсит владельца из JSON.
     */
    public static Person parsePerson(JsonObject jPerson) {
        Person person = new Person();
        person.setName(jPerson.get("name").getAsString());

        if (jPerson.has("birthday") && !jPerson.get("birthday").isJsonNull()) {
            person.setBirthday(LocalDateTime.parse(
                    jPerson.get("birthday").getAsString(), Person.formatter));
        }

        person.setHeight(jPerson.get("height").getAsFloat());

        if (jPerson.has("passportID") && !jPerson.get("passportID").isJsonNull()) {
            person.setPassportID(jPerson.get("passportID").getAsString());
        }

        if (jPerson.has("hairColor") && !jPerson.get("hairColor").isJsonNull()) {
            person.setHairColor(Color.valueOf(jPerson.get("hairColor").getAsString().toUpperCase()));
        }

        return person;
    }

    /**
     * Обновляет поля существующего продукта из JSON (только переданные поля).
     */
    public static void updateProduct(Product product, JsonObject jProduct,
                                     JsonObject jCoordinates, JsonObject jPerson) {
        if (jCoordinates != null) {
            Coordinates coordinates = product.getCoordinates();
            if (jCoordinates.has("x")) coordinates.setX(jCoordinates.get("x").getAsInt());
            if (jCoordinates.has("y")) coordinates.setY(jCoordinates.get("y").getAsInt());
        }

        if (jProduct.has("name"))            product.setName(jProduct.get("name").getAsString());
        if (jProduct.has("manufactureCost")) product.setManufactureCost(jProduct.get("manufactureCost").getAsFloat());
        if (jProduct.has("unitOfMeasure"))   product.setUnitOfMeasure(UnitOfMeasure.valueOf(jProduct.get("unitOfMeasure").getAsString().toUpperCase()));
        if (jProduct.has("price") && !jProduct.get("price").isJsonNull())
            product.setPrice(jProduct.get("price").getAsFloat());
        if (jProduct.has("partNumber") && !jProduct.get("partNumber").isJsonNull())
            product.setPartNumber(jProduct.get("partNumber").getAsString());

        // ownerAction: "keep" = не трогать, "remove" = удалить, "set" = установить
        String ownerAction = (jProduct != null && jProduct.has("ownerAction") && !jProduct.get("ownerAction").isJsonNull())
                ? jProduct.get("ownerAction").getAsString()
                : "keep"; // по умолчанию не трогаем

        if ("set".equals(ownerAction) && jPerson != null) {
            Person person = product.getOwner() != null ? product.getOwner() : new Person();
            if (jPerson.has("name")) person.setName(jPerson.get("name").getAsString());
            if (jPerson.has("birthday") && !jPerson.get("birthday").isJsonNull())
                person.setBirthday(LocalDateTime.parse(jPerson.get("birthday").getAsString(), Person.formatter));
            if (jPerson.has("height"))     person.setHeight(jPerson.get("height").getAsFloat());
            if (jPerson.has("passportID")) person.setPassportID(jPerson.get("passportID").getAsString());
            if (jPerson.has("hairColor"))  person.setHairColor(Color.valueOf(jPerson.get("hairColor").getAsString().toUpperCase()));
            product.setOwner(person);
        } else if ("remove".equals(ownerAction)) {
            product.setOwner(null);
        }
        // "keep" — ничего не делаем, владелец остаётся как есть
    }
}
