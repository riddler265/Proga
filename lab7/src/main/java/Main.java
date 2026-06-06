import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/studs",
                    "s504751",
                    "u0dHC5qLmSGuiQxU"
            );

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                System.out.println(id + " " + name + " " + price);
            }

            System.out.println("hello");
        } catch (SQLException e) {
            System.out.println("hello");
            System.exit(1);
        }
    }
}
