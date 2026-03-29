package model;

import model.enums.Color;
import exceptions.IncorrectInputException;
import interfaces.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    //formater
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

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
    public void setName(String name) throws IncorrectInputException {
        if (name == null || name.isEmpty()) throw new IncorrectInputException("не пустая строка");
        else this.name = name;
    }

    public void setBirthday(String birthday) throws IncorrectInputException {
        if (birthday.equals("Null") || birthday.equals("Nl")) this.birthday = null;
        else {
            try {
                this.birthday = LocalDateTime.parse(birthday, formatter);
            } catch (DateTimeParseException e) {
                throw new IncorrectInputException("dd-MM-yyyy HH:mm:ss");
            }
        }
    }

    public void setHeight(String height) throws IncorrectInputException {
        try {
            this.height = parse(height).floatValue();
            if (this.height <= 0.0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("число больше 0");
        }
    }

    public void setPassportID(String passportID) {
        if (passportID.equals("Null") || passportID.equals("Nl")) this.passportID = null;
        else this.passportID = passportID;
    }

    public void setHairColor(String hairColor) throws IncorrectInputException {
        if (hairColor.equals("Null") || hairColor.equals("Nl")) this.hairColor = null;
        else this.hairColor = Color.getcolor(hairColor);
    }//endregion

    private BigDecimal parse(String input) throws NumberFormatException {
        BigDecimal bd = new BigDecimal(input.replace(',', '.'));
        bd = bd.setScale(5, RoundingMode.HALF_UP);
        return bd;
    }

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
