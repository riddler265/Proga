package model;

import model.enums.UnitOfMeasure;
import exceptions.IncorrectInputException;
import interfaces.Validate;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, элементами которого управляет коллекция.
 */
public class Product implements Comparable<Product>, Validate {

    /**
     * Поля класса.
     */
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

    //region setters
    public void setName(String name) throws IncorrectInputException {
        if (name == null || name.isEmpty()) throw new IncorrectInputException("name");
        else this.name = name;
    }

    public void setCoordinates(Integer x, Integer y) {
        this.coordinates.setX(x).setY(y);
    }

    public void setPrice(Float price) throws IncorrectInputException {
        if (price != null && price <= 0) throw new IncorrectInputException("price");
        else this.price = price;
    }

    public void setPartNumber(String partNumber) throws IncorrectInputException {
        if (partNumber != null && partNumber.isEmpty()) throw new IncorrectInputException("part number");
        else this.partNumber = partNumber;
    }

    public void setManufactureCost(float manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) throws IncorrectInputException {
        if (unitOfMeasure == null) throw new IncorrectInputException("Unit of measure");
        else this.unitOfMeasure = unitOfMeasure;
    }

    public void setOwner(Person owner) throws IncorrectInputException {
        if (owner == null) throw new IncorrectInputException("owner");
        this.owner = owner;
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
    public String toString() {
        String info = "";
        info += "Продукт №" + id;
        info += " - " + name;
        info += ". " + coordinates;
        info += ". Creation time is " + creationDate;
        info += ". Price is " + price;
        info += ". Part number is " + partNumber;
        info += ". Manufacture cost is " + manufactureCost;
        info += ". Unit of measure is " + unitOfMeasure;
        if (owner != null) info += ". Owner - " +  owner.getName() + ".";
        else info += ".";
        return info;
    }//endregion
}
