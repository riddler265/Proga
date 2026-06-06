package model;

import interfaces.Validate;
import model.enums.UnitOfMeasure;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс продукта. Serializable для передачи по сети.
 *
 * ВАЖНО: id и creationDate назначаются СЕРВЕРОМ автоматически.
 * Клиент передаёт объект без id (или с id=0), сервер присваивает его при add.
 */
public class Product implements Comparable<Product>, Validate {

    // Серверный счётчик id (volatile — однопоточный сервер, но для надёжности)
    private static volatile int currentId = 1;

    private int id;                          // автогенерируемый сервером
    private String name;                     // not null, not empty
    private Coordinates coordinates;         // not null
    private LocalDateTime creationDate;      // автогенерируемый сервером
    private Float price;                     // nullable, > 0
    private String partNumber;               // nullable, not empty
    private float manufactureCost;
    private UnitOfMeasure unitOfMeasure;     // not null
    private Person owner;                    // nullable

    // ==================== АВТОГЕНЕРАЦИЯ ПОЛЕЙ СЕРВЕРОМ ====================

    /**
     * Назначает серверные поля новому объекту (вызывается при add).
     * id и creationDate не должны приходить от клиента.
     */
    public void assignServerFields() {
        this.id = currentId++;
        this.creationDate = LocalDateTime.now();
    }

    public static int getCurrentId()            { return currentId; }
    public static void updateCurrentId(int max) {
        if (max >= currentId) currentId = max + 1;
    }

    // ==================== GETTERS ====================

    public int getId()                  { return id; }
    public String getName()             { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public Float getPrice()             { return price; }
    public String getPartNumber()       { return partNumber; }
    public float getManufactureCost()   { return manufactureCost; }
    public UnitOfMeasure getUnitOfMeasure() { return unitOfMeasure; }
    public Person getOwner()            { return owner; }

    // ==================== SETTERS ====================

    public Product setName(String name) {
        this.name = name;
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

    public Product setManufactureCost(Float manufactureCost) {
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

    public Product setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    // Прямая установка для десериализации из файла
    public void setId(int id)                       { this.id = id; }
    public void setCreationDate(LocalDateTime date) { this.creationDate = date; }

    // ==================== COMPARABLE / VALIDATE ====================

    @Override
    public int compareTo(Product o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.trim().isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (price != null && price <= 0) return false;
        if (partNumber != null && partNumber.trim().isEmpty()) return false;
        return unitOfMeasure != null;
    }

    // ==================== equals / hashCode / toString ====================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product p = (Product) o;
        return id == p.id && Objects.equals(creationDate, p.creationDate);
    }

    @Override
    public int hashCode() { return Objects.hash(id, creationDate); }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", coordinates=" + coordinates +
               ", creationDate=" + (creationDate != null ? creationDate.format(Person.formatter) : "null") +
               ", price=" + price +
               ", partNumber='" + partNumber + '\'' +
               ", manufactureCost=" + manufactureCost +
               ", unitOfMeasure=" + unitOfMeasure +
               ", owner=" + Optional.ofNullable(owner).map(Person::getName).orElse("none") +
               '}';
    }
}
