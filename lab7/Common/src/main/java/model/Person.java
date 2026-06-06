package model;

import model.enums.Color;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс человека. Serializable для передачи по сети.
 */
public class Person {


    private String name;           // not null, not empty
    private LocalDateTime birthday; // nullable
    private float height;          // > 0
    private String passportID;     // nullable
    private Color hairColor;
    private String password;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public String getName()           { return name; }
    public LocalDateTime getBirthday(){ return birthday; }
    public float getHeight()          { return height; }
    public String getPassportID()     { return passportID; }
    public Color getHairColor()       { return hairColor; }

    public void setName(String name) {
        this.name = name;

    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;

    }

    public void setHeight(Float height) {
        this.height = height;

    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;

    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;

    }

    public void setPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            this.password = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 not available", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person p = (Person) o;
        return Objects.equals(name, p.name) && Objects.equals(password, p.password);
    }

    @Override
    public int hashCode() { return Objects.hash(birthday, height); }

    @Override
    public String toString() {
        return name;
    }
}
