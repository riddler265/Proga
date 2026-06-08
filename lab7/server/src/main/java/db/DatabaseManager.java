package db;

import model.Coordinates;
import model.Person;
import model.Product;
import model.enums.Color;
import model.enums.UnitOfMeasure;
import subscription.Subscription;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * Менеджер базы данных.
 * Все операции с PostgreSQL сосредоточены здесь.
 * Подключение через env-переменные: DB_HOST, DB_NAME, DB_USER, DB_PASSWORD.
 */
public class DatabaseManager {

    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());

    private final Connection connection;

    public DatabaseManager() throws SQLException {
        String host     = getEnv("DB_HOST",     "pg");
        String dbName   = getEnv("DB_NAME",     "studs");
        String user     = getEnv("DB_USER",     "");
        String password = getEnv("DB_PASSWORD", "");

        String url = "jdbc:postgresql://" + host + "/" + dbName;
        logger.info("Connecting to DB: " + url + " as " + user);
        this.connection = DriverManager.getConnection(url, user, password);
        this.connection.setAutoCommit(true);
        logger.info("DB connection established");
        initSchema();
    }

    private static String getEnv(String name, String def) {
        String v = System.getenv(name);
        return (v != null && !v.isBlank()) ? v : def;
    }

    // =====================================================================
    // SCHEMA INIT
    // =====================================================================

    private void initSchema() throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id       SERIAL PRIMARY KEY,
                    login    VARCHAR(64)  NOT NULL UNIQUE,
                    password VARCHAR(128) NOT NULL
                )
            """);
            st.execute("""
                CREATE SEQUENCE IF NOT EXISTS products_id_seq
                    START WITH 1 INCREMENT BY 1
            """);
            st.execute("""
                CREATE TABLE IF NOT EXISTS products (
                    id               INTEGER      PRIMARY KEY DEFAULT nextval('products_id_seq'),
                    name             VARCHAR(255) NOT NULL,
                    coord_x          INTEGER      NOT NULL,
                    coord_y          INTEGER      NOT NULL,
                    creation_date    TIMESTAMP    NOT NULL,
                    price            FLOAT,
                    part_number      VARCHAR(255),
                    manufacture_cost FLOAT        NOT NULL,
                    unit_of_measure  VARCHAR(32)  NOT NULL,
                    owner_name       VARCHAR(255),
                    owner_birthday   TIMESTAMP,
                    owner_height     FLOAT,
                    owner_passport   VARCHAR(64),
                    owner_hair_color VARCHAR(32),
                    owner_login      VARCHAR(64)  REFERENCES users(login) ON DELETE SET NULL
                )
            """);
            st.execute("""
                CREATE TABLE IF NOT EXISTS subscriptions (
                    id           SERIAL PRIMARY KEY,
                    user_login   VARCHAR(64) NOT NULL REFERENCES users(login) ON DELETE CASCADE,
                    field        VARCHAR(64) NOT NULL,
                    operator     VARCHAR(8)  NOT NULL,
                    threshold    FLOAT       NOT NULL,
                    created_at   TIMESTAMP   NOT NULL DEFAULT now()
                )
            """);
        }
        logger.info("DB schema initialized");
    }

    // =====================================================================
    // USERS
    // =====================================================================

    /** Создаёт нового пользователя. Возвращает false если логин занят. */
    public boolean createUser(String login, String passwordHash) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users(login, password) VALUES(?, ?)")) {
            ps.setString(1, login);
            ps.setString(2, passwordHash);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) return false; // unique violation
            throw e;
        }
    }

    /** Возвращает хэш пароля для логина, или null если не найден. */
    public String getPasswordHash(String login) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT password FROM users WHERE login = ?")) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("password") : null;
        }
    }

    // =====================================================================
    // PRODUCTS — LOAD
    // =====================================================================

    /** Загружает все продукты из БД в PriorityQueue. */
    public PriorityQueue<Product> loadAll() throws SQLException {
        PriorityQueue<Product> queue = new PriorityQueue<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM products ORDER BY id")) {
            while (rs.next()) {
                queue.add(mapProduct(rs));
            }
        }
        logger.info("Loaded " + queue.size() + " products from DB");
        return queue;
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setManufactureCost(rs.getFloat("manufacture_cost"));
        p.setUnitOfMeasure(UnitOfMeasure.valueOf(rs.getString("unit_of_measure")));

        Coordinates coords = new Coordinates();
        coords.setX(rs.getInt("coord_x"));
        coords.setY(rs.getInt("coord_y"));
        p.setCoordinates(coords);

        Timestamp createdAt = rs.getTimestamp("creation_date");
        if (createdAt != null) p.setCreationDate(createdAt.toLocalDateTime());

        float price = rs.getFloat("price");
        if (!rs.wasNull()) p.setPrice(price);

        String partNumber = rs.getString("part_number");
        if (partNumber != null) p.setPartNumber(partNumber);

        String ownerName = rs.getString("owner_name");
        if (ownerName != null) {
            Person person = new Person();
            person.setName(ownerName);
            Timestamp bday = rs.getTimestamp("owner_birthday");
            if (bday != null) person.setBirthday(bday.toLocalDateTime());
            float height = rs.getFloat("owner_height");
            if (!rs.wasNull()) person.setHeight(height);
            String passport = rs.getString("owner_passport");
            if (passport != null) person.setPassportID(passport);
            String hairColor = rs.getString("owner_hair_color");
            if (hairColor != null) person.setHairColor(Color.valueOf(hairColor));
            p.setOwner(person);
        }

        p.setOwnerLogin(rs.getString("owner_login"));
        return p;
    }

    // =====================================================================
    // PRODUCTS — INSERT
    // =====================================================================

    /**
     * Добавляет продукт в БД.
     * Возвращает присвоенный id (из sequence) и creationDate.
     */
    public int insertProduct(Product p, String ownerLogin) throws SQLException {
        String sql = """
            INSERT INTO products(
                name, coord_x, coord_y, creation_date, price, part_number,
                manufacture_cost, unit_of_measure,
                owner_name, owner_birthday, owner_height, owner_passport, owner_hair_color,
                owner_login
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            RETURNING id, creation_date
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, p.getName());
            ps.setInt(i++,    p.getCoordinates().getX());
            ps.setInt(i++,    p.getCoordinates().getY());
            ps.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.now()));
            if (p.getPrice() != null) ps.setFloat(i++, p.getPrice()); else ps.setNull(i++, Types.FLOAT);
            if (p.getPartNumber() != null) ps.setString(i++, p.getPartNumber()); else ps.setNull(i++, Types.VARCHAR);
            ps.setFloat(i++,  p.getManufactureCost());
            ps.setString(i++, p.getUnitOfMeasure().name());

            Person owner = p.getOwner();
            if (owner != null) {
                ps.setString(i++, owner.getName());
                ps.setTimestamp(i++, owner.getBirthday() != null ? Timestamp.valueOf(owner.getBirthday()) : null);
                ps.setFloat(i++,  owner.getHeight());
                if (owner.getPassportID() != null) ps.setString(i++, owner.getPassportID()); else ps.setNull(i++, Types.VARCHAR);
                if (owner.getHairColor() != null) ps.setString(i++, owner.getHairColor().name()); else ps.setNull(i++, Types.VARCHAR);
            } else {
                ps.setNull(i++, Types.VARCHAR);
                ps.setNull(i++, Types.TIMESTAMP);
                ps.setNull(i++, Types.FLOAT);
                ps.setNull(i++, Types.VARCHAR);
                ps.setNull(i++, Types.VARCHAR);
            }
            ps.setString(i, ownerLogin);

            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            p.setId(id);
            p.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
            p.setOwnerLogin(ownerLogin);
            return id;
        }
    }

    // =====================================================================
    // PRODUCTS — UPDATE
    // =====================================================================

    public boolean updateProduct(Product p) throws SQLException {
        String sql = """
            UPDATE products SET
                name=?, coord_x=?, coord_y=?, price=?, part_number=?,
                manufacture_cost=?, unit_of_measure=?,
                owner_name=?, owner_birthday=?, owner_height=?, owner_passport=?, owner_hair_color=?
            WHERE id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, p.getName());
            ps.setInt(i++,    p.getCoordinates().getX());
            ps.setInt(i++,    p.getCoordinates().getY());
            if (p.getPrice() != null) ps.setFloat(i++, p.getPrice()); else ps.setNull(i++, Types.FLOAT);
            if (p.getPartNumber() != null) ps.setString(i++, p.getPartNumber()); else ps.setNull(i++, Types.VARCHAR);
            ps.setFloat(i++,  p.getManufactureCost());
            ps.setString(i++, p.getUnitOfMeasure().name());

            Person owner = p.getOwner();
            if (owner != null) {
                ps.setString(i++, owner.getName());
                ps.setTimestamp(i++, owner.getBirthday() != null ? Timestamp.valueOf(owner.getBirthday()) : null);
                ps.setFloat(i++,  owner.getHeight());
                if (owner.getPassportID() != null) ps.setString(i++, owner.getPassportID()); else ps.setNull(i++, Types.VARCHAR);
                if (owner.getHairColor() != null) ps.setString(i++, owner.getHairColor().name()); else ps.setNull(i++, Types.VARCHAR);
            } else {
                ps.setNull(i++, Types.VARCHAR);
                ps.setNull(i++, Types.TIMESTAMP);
                ps.setNull(i++, Types.FLOAT);
                ps.setNull(i++, Types.VARCHAR);
                ps.setNull(i++, Types.VARCHAR);
            }
            ps.setInt(i, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // =====================================================================
    // PRODUCTS — DELETE
    // =====================================================================

    public boolean deleteProduct(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM products WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public int deleteAllProducts() throws SQLException {
        try (Statement st = connection.createStatement()) {
            return st.executeUpdate("DELETE FROM products");
        }
    }

    /** Удаляет все продукты пользователя, возвращает кол-во удалённых. */
    public int deleteProductsByOwner(String ownerLogin) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM products WHERE owner_login = ?")) {
            ps.setString(1, ownerLogin);
            return ps.executeUpdate();
        }
    }

    // =====================================================================
    // SUBSCRIPTIONS
    // =====================================================================

    public int addSubscription(String userLogin, String field, String operator, float threshold) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO subscriptions(user_login, field, operator, threshold) VALUES(?,?,?,?) RETURNING id")) {
            ps.setString(1, userLogin);
            ps.setString(2, field);
            ps.setString(3, operator);
            ps.setFloat(4, threshold);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id");
        }
    }

    public boolean deleteSubscription(int id, String userLogin) throws SQLException {
        // Сначала проверяем что подписка принадлежит этому пользователю
        try (PreparedStatement check = connection.prepareStatement(
                "SELECT user_login FROM subscriptions WHERE id = ?")) {
            check.setInt(1, id);
            ResultSet rs = check.executeQuery();
            if (!rs.next()) return false; // нет такой подписки
            String owner = rs.getString("user_login");
            if (!owner.trim().equalsIgnoreCase(userLogin.trim())) return false; // не владелец
        }
        // Удаляем
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM subscriptions WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Subscription> getSubscriptionsByUser(String userLogin) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM subscriptions WHERE user_login = ? ORDER BY id")) {
            ps.setString(1, userLogin);
            return mapSubscriptions(ps.executeQuery());
        }
    }

    public List<Subscription> getAllSubscriptions() throws SQLException {
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM subscriptions")) {
            return mapSubscriptions(rs);
        }
    }

    private List<Subscription> mapSubscriptions(ResultSet rs) throws SQLException {
        List<Subscription> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Subscription(
                rs.getInt("id"),
                rs.getString("user_login"),
                rs.getString("field"),
                rs.getString("operator"),
                rs.getFloat("threshold")
            ));
        }
        return list;
    }

    public void close() {
        try { if (connection != null && !connection.isClosed()) connection.close(); }
        catch (SQLException ignored) {}
    }
}
