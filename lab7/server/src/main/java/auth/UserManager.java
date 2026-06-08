package auth;

import db.DatabaseManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HexFormat;
import java.util.logging.Logger;

/**
 * Управляет регистрацией и авторизацией пользователей.
 * Пароли хранятся как SHA-384 хэш.
 */
public class UserManager {

    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    private final DatabaseManager db;

    public UserManager(DatabaseManager db) {
        this.db = db;
    }

    /** Хэширует пароль алгоритмом SHA-384, возвращает hex-строку. */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] digest = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-384 not available", e);
        }
    }

    /**
     * Регистрирует нового пользователя.
     * @return true — успешно, false — логин занят
     */
    public boolean register(String login, String password) {
        if (login == null || login.isBlank() || password == null || password.isBlank()) return false;
        try {
            boolean created = db.createUser(login, hashPassword(password));
            if (created) logger.info("User registered: " + login);
            else logger.warning("Registration failed (login taken): " + login);
            return created;
        } catch (SQLException e) {
            logger.severe("DB error on register: " + e.getMessage());
            return false;
        }
    }

    /**
     * Проверяет логин и пароль.
     * @return true — авторизация успешна
     */
    public boolean authenticate(String login, String password) {
        if (login == null || password == null) return false;
        try {
            String stored = db.getPasswordHash(login);
            if (stored == null) return false;
            return stored.equals(hashPassword(password));
        } catch (SQLException e) {
            logger.severe("DB error on authenticate: " + e.getMessage());
            return false;
        }
    }
}
