package person;

import enums.Color;
import exceptions.IncorrectInputException;
import product.Product;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс человека.
 */
public class Person {
    //fields
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private float height; //Значение поля должно быть больше 0
    private String passportID; //Поле может быть null
    private Color hairColor; //Поле может быть null

    //constructor
    public Person(String name, float height, String passportID, Color hairColor)  {
        this.name = name;

        this.birthday = LocalDateTime.now();

        this.height = height;

        this.passportID = passportID;
        this.hairColor = hairColor;
    }

    //getters
    public String getName() {
        return name;
    }

    public float getHeight() {
        return height;
    }

    //setters
    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Person setHeight(float height) {
        this.height = height;
        return this;
    }

    //equals(), hachCode(), toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(birthday, person.birthday) && height == person.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, height);
    }

    @Override
    public String toString() {
        String info = "";
        info += name;
        info += ". Birthday is " + birthday;
        info += ". Height: " + height;
        return info;
    }
}
