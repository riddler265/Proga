package product;

import coordinates.Coordinates;
import enums.UnitOfMeasure;
import exceptions.IncorrectInputException;
import person.Person;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, элементами которого управляет коллекция.
 */
public class Product implements Comparable<Product>{

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

    /**
     * Конструктор.
     * @param name имя. Не null и не "".
     * @param coordinates объект класса {@link Coordinates}. Не null.
     * @param price объект класса Float. Null либо значение > 0.0.
     * @param partNumber номер партии. Не пустая строка.
     * @param manufactureCost стоимость производства.
     * @param unitOfMeasure единицы измерения. Не null.
     * @param owner объект класса {@link Person}.
     * @throws IncorrectInputException если формат ввода не соответствует.
     */
    public Product(String name, Coordinates coordinates, Float price, String partNumber,
                   float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner)
            throws IncorrectInputException {

        if (name == null || name.isEmpty()) throw new IncorrectInputException("name");
        else this.name = name;

        if (coordinates == null) throw new IncorrectInputException("coordinates");
        else this.coordinates = coordinates;

        if (price != null && price <= 0) throw new IncorrectInputException("price");
        else this.price = price;

        if (partNumber != null && partNumber.isEmpty()) throw new IncorrectInputException("part number");
        else this.partNumber = partNumber;

        this.manufactureCost = manufactureCost;

        if (unitOfMeasure == null) throw new IncorrectInputException("unit of measure");
        else this.unitOfMeasure = unitOfMeasure;

        this.owner = owner;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public float getManufactureCost() {
        return manufactureCost;
    }

    public Float getPrice() {
        return price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public String getPartNumber() {
        return partNumber;
    }

    //setters
    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setCoordinates(Integer x, Integer y) {
        this.coordinates.setX(x).setY(y);
        return this;
    }

    public Product setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Product setPartNumber(String partNumber) {
        this.partNumber = partNumber;
        return this;
    }

    public Product setManufactureCost(float manufactureCost) {
        this.manufactureCost = manufactureCost;
        return this;
    }

    public Product setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }

    public Product setOwner(Person owner) {
        this.owner = owner;
        return this;
    }

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
    }

    //comparable
    @Override
    public int compareTo(Product o) {
        String NameI = name;
        String NameII = o.name;
        return NameI.compareTo(NameII);
    }

    //equals(), hachCode(), toString()
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
        info += "Product №" + id;
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
    }
}
