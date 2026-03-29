package model;

import model.enums.UnitOfMeasure;
import exceptions.IncorrectInputException;
import interfaces.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

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

    //region setters
    public void setName(String name) throws IncorrectInputException {
        if (name == null || name.isEmpty()) throw new IncorrectInputException("\n\tне пустая строка");
        else this.name = name;
    }

    public void setPrice(String price) throws IncorrectInputException {
        if (price.equalsIgnoreCase("Null") || price.equalsIgnoreCase("Nl")) this.price = null;
        else {
            try {
                this.price = parse(price).floatValue();
                if (this.price <= 0.0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new IncorrectInputException("Null, число больше 0");
            }
        }
    }

    public void setPartNumber(String partNumber) throws IncorrectInputException {
        if (partNumber.equals("Null") || partNumber.equals("Nl")) this.partNumber = null;
        if (partNumber.isEmpty()) throw new IncorrectInputException("не пустая строка, Null");
        else this.partNumber = partNumber;
    }

    public void setManufactureCost(String manufactureCost) {
        try {
            this.manufactureCost = parse(manufactureCost).floatValue();
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("число");
        }
    }

    public void setUnitOfMeasure(String unitOfMeasure) throws IncorrectInputException {
        this.unitOfMeasure = UnitOfMeasure.getUnit(unitOfMeasure);
    }

    public Product setOwner(Person owner) {
        this.owner = owner;
        return this;
    }

    public Product setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }//endregion

    private BigDecimal parse(String input) throws NumberFormatException {
        BigDecimal bd = new BigDecimal(input.replace(',', '.'));
        bd = bd.setScale(5, RoundingMode.HALF_EVEN);
        return bd;
    }

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
        if (owner == null) {
            return "Продукт №" + id +
                    " - " + name +
                    ".\nКоординаты: " + coordinates +
                    ".\nВремя создания: " + creationDate +
                    ".\nЦена: " + price +
                    ".\nНомер партии: " + partNumber +
                    ".\nЦена производства: " + manufactureCost +
                    ".\nЕдиница измерения: " + unitOfMeasure + ".";
        } else {
            return "Продукт №" + id +
                    " - " + name +
                    ".\nКоординаты: " + coordinates +
                    ".\nВремя создания: " + creationDate +
                    ".\nЦена: " + price +
                    ".\nНомер партии: " + partNumber +
                    ".\nЦена производства: " + manufactureCost +
                    ".\nЕдиница измерения: " + unitOfMeasure +
                    ".\nВладелец: " + owner.getName() + ".";
        }
    }//endregion
}
