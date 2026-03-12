package product;

import coordinates.Coordinates;
import enums.UnitOfMeasure;
import exceptions.IncorrectInputException;
import person.Person;
import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

    //fields
    private static int currentId = 0;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Поле может быть null, Значение поля должно быть больше 0
    private String partNumber; //Строка не может быть пустой, Поле может быть null
    private float manufactureCost;
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле может быть null

    //consctructor
    public Product(String name, Coordinates coordinates, Float price, String partNumber, float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner)
            throws IncorrectInputException {
        this.id = currentId++;

        if (name == null || name.isEmpty()) throw new IncorrectInputException("name");
        else this.name = name;

        if (coordinates == null) throw new IncorrectInputException("coordiantes");
        else this.coordinates = coordinates;

        this.creationDate = LocalDateTime.now();

        if (price <= 0) throw new IncorrectInputException("price");
        else this.price = price;

        if (partNumber == null || partNumber.isEmpty()) throw new IncorrectInputException("part number");
        else this.partNumber = partNumber;

        this.manufactureCost = manufactureCost;

        if (unitOfMeasure == null) throw new IncorrectInputException("unit of measure");
        else this.unitOfMeasure = unitOfMeasure;

        this.owner = owner;
    }

    //equals(), hachCode(), toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && unitOfMeasure == product.unitOfMeasure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unitOfMeasure);
    }

    @Override
    public String toString() {
        String info = "";
        info += "Product №" + id;
        info += " - " + name;
        info += ". Coordinates: " + coordinates;
        info += ". Creation time is " + creationDate;
        info += ". Price is " + price;
        info += ". Part number is " + partNumber;
        info += ". Manufacture cost is" + manufactureCost;
        info += ". Unit of measure is" + unitOfMeasure;
        info += ". Owner - " + owner;
        return info;
    }


}
