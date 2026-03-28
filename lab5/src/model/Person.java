package model;

import model.enums.Color;
import exceptions.IncorrectInputException;
import interfaces.Validate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс человека.
 */
public class Person implements Validate {

    //fields
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private float height; //Значение поля должно быть больше 0
    private String passportID; //Поле может быть null
    private Color hairColor; //Поле может быть null

    //region getters
    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public float getHeight() {
        return height;
    }

    public String getPassportID() {
        return passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }//endregion

    //region setters
    public Person setName(String name) throws IncorrectInputException {
        if (name == null || name.isEmpty()) throw new IncorrectInputException("name");
        else this.name = name;
        return this;
    }

    public Person setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public Person setHeight(float height) throws IncorrectInputException {
        if (height <= 0.0) throw new IncorrectInputException("height");
        else this.height = height;
        return this;
    }

    public Person setPassportID(String passportID) throws IncorrectInputException {
        if (passportID == null) throw new IncorrectInputException("passport id");
        else this.passportID = passportID;
        return this;
    }

    public Person setHairColor(Color hairColor) {
        this.hairColor = hairColor;
        return this;
    }//endregion

    @Override
    public boolean validate() {
        if (this.name == null || this.name.trim().isEmpty()) return false;
        if (this.height <= 0) return false;
        if (this.passportID != null && this.passportID.trim().isEmpty()) return false;
        return true;
    }

    //region equals(), hachCode(), toString()
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
        return name +
                "\nBirthday: " + birthday.format(DateTimeFormatter.ISO_LOCAL_DATE) +
                "\nHeight: " + height +
                "\nPassport id: " + passportID +
                "\nHair color: " + hairColor;
    }//endregion
}
