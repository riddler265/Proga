package model;

import interfaces.Validate;
import model.enums.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс человека. Serializable для передачи по сети.
 */
public class Person implements Validate {


    private String name;           // not null, not empty
    private LocalDateTime birthday; // nullable
    private float height;          // > 0
    private String passportID;     // nullable
    private Color hairColor;       // nullable

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public String getName()           { return name; }
    public LocalDateTime getBirthday(){ return birthday; }
    public float getHeight()          { return height; }
    public String getPassportID()     { return passportID; }
    public Color getHairColor()       { return hairColor; }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Person setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public Person setHeight(Float height) {
        this.height = height;
        return this;
    }

    public Person setPassportID(String passportID) {
        this.passportID = passportID;
        return this;
    }

    public Person setHairColor(Color hairColor) {
        this.hairColor = hairColor;
        return this;
    }

    @Override
    public boolean validate() {
        if (name == null || name.trim().isEmpty()) return false;
        if (height <= 0) return false;
        if (passportID != null && passportID.trim().isEmpty()) return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person p = (Person) o;
        return Objects.equals(birthday, p.birthday) && height == p.height;
    }

    @Override
    public int hashCode() { return Objects.hash(birthday, height); }

    @Override
    public String toString() {
        return "Person{name=" + name +
               ", birthday=" + (birthday != null ? birthday.format(DateTimeFormatter.ISO_LOCAL_DATE) : "null") +
               ", height=" + height +
               ", passportID=" + passportID +
               ", hairColor=" + hairColor + "}";
    }
}
