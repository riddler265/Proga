package model;

import com.google.gson.JsonObject;
import exceptions.IncorrectInputException;
import interfaces.Validate;
import managers.AnnounceManager;
import model.enums.UnitOfMeasure;
import util.NumbParser;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс, элементами которого управляет коллекция.
 */
public class Product implements Comparable<Product>, Validate {

    //fields
    private static int currentId = 1;
    private final int id = currentId++; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate = LocalDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Поле может быть null, Значение поля должно быть больше 0
    private String partNumber; //Строка не может быть пустой, Поле может быть null
    private float manufactureCost;
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле может быть null

    //region getters
    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public Coordinates getCoordinates () {
        return coordinates;
    }

    public float getManufactureCost () {
        return manufactureCost;
    }

    public Float getPrice () {
        return price;
    }

    public LocalDateTime getCreationDate () {
        return creationDate;
    }

    public UnitOfMeasure getUnitOfMeasure () {
        return unitOfMeasure;
    }

    public Person getOwner () {
        return owner;
    }

    public String getPartNumber () {
        return partNumber;
    }//endregion



    //region id
    /**
     * Узнать свободное id.
     * @return целочисленное, еще не занятое никаким объектом id/
     */
    public static int getCurrentId() {return currentId;}


    /**
     * Обновляет счетчик свободного id.
     * @param maxFFile последний id из файла.
     */
    public static void updateCurrentId(int maxFFile) {
        if(maxFFile >= currentId) {
            currentId = maxFFile + 1;
        }
    }//endregion
    
    @Override
    public int compareTo(Product o) {
        String NameI = name;
        String NameII = o.name;
        return NameI.compareTo(NameII);
    }

    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.trim().isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (price != null && price <= 0) return false;
        if (partNumber != null && partNumber.trim().isEmpty()) return false;
        if (unitOfMeasure == null) return false;
        return true;
    }

    //region equals(), hachCode(), toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && creationDate.equals(product.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate);
    }

    @Override
    public JsonObject toString() {

        return AnnounceManager.getInstance().cTCL("product.info", Integer.toString(id), name,
                coordinates.toString(), creationDate.format(Person.formatter),
                Float.toString(price), partNumber, Float.toString(manufactureCost),
                unitOfMeasure.toString(),
                Optional.ofNullable(owner)
                        .map(Person::getName)
                        .orElse(AnnounceManager.getInstance().cTCL("no.owner")));
    }//endregion
}
